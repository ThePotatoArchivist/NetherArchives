package archives.tater.netherarchives

import archives.tater.netherarchives.client.render.entity.feature.WitherEyesFeatureRenderer
import archives.tater.netherarchives.client.render.entity.feature.WitherSkeletonEyesFeatureRenderer
import archives.tater.netherarchives.client.render.entity.model.SkisEntityModel
import archives.tater.netherarchives.client.render.particle.BlazeSparkParticle
import archives.tater.netherarchives.client.util.registerArmorRenderer
import archives.tater.netherarchives.registry.NetherArchivesEntities
import archives.tater.netherarchives.registry.NetherArchivesItems
import archives.tater.netherarchives.registry.NetherArchivesParticles
import archives.tater.netherarchives.registry.NetherArchivesTags
import archives.tater.netherarchives.util.isIn
import archives.tater.netherarchives.util.isOf
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry
import net.minecraft.client.Minecraft
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.particle.FlameParticle
import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.client.renderer.entity.EntityRenderers
import net.minecraft.client.renderer.entity.ThrownItemRenderer
import net.minecraft.client.renderer.entity.WitherBossRenderer
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer
import net.minecraft.client.renderer.entity.state.HumanoidRenderState
import net.minecraft.client.renderer.rendertype.RenderTypes
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.util.Mth.clamp
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.ClipBlockStateContext
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3
import java.util.*

object NetherArchivesClient : ClientModInitializer {

    @JvmField
    internal val spectreglassRevealed = WeakHashMap<LivingEntity, Boolean>()

    @JvmField
    internal val SOUL_GLASS_REVEALED = RenderStateDataKey.create<Boolean> { "Soul Glass Revealed" }

    @JvmField
    internal var usingSoulKnife = false // TODO rename

    private val SKIS_MODEL_LAYER = ModelLayerLocation(NetherArchives.id("skis"), "main")

    private val BASALT_SKIS_LOCATION = NetherArchives.id("textures/models/basalt_skis.png")
    private lateinit var basaltSkisModel: SkisEntityModel<HumanoidRenderState>

    @JvmStatic
    fun isRevealed(entity: LivingEntity) = usingSoulKnife || spectreglassRevealed.getOrDefault(entity, false)

    @JvmField
    val KNIFE_OVERLAY = NetherArchives.id("textures/misc/knife_overlay.png")

    override fun onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        EntityRenderers.register(NetherArchivesEntities.BLAZE_LANTERN, ::ThrownItemRenderer)

        LivingEntityRenderLayerRegistrationCallback.EVENT.register { entityType, entityRenderer, registrationHelper, _ ->
            if (NetherArchivesClientConfig.config.skeletonEyes)
                registrationHelper.register(when (entityType) {
                    EntityType.WITHER_SKELETON -> WitherSkeletonEyesFeatureRenderer(entityRenderer as WitherSkeletonRenderer)
                    EntityType.WITHER -> WitherEyesFeatureRenderer(entityRenderer as WitherBossRenderer)
                    else -> return@register
                })
        }

        ModelLayerRegistry.registerModelLayer(SKIS_MODEL_LAYER, SkisEntityModel.Companion::getTexturedModelData)

        registerArmorRenderer(NetherArchivesItems.BASALT_SKIS) { matrices, queue, _, bipedEntityRenderState, _, light, _ ->
            if (!::basaltSkisModel.isInitialized) {
                basaltSkisModel = SkisEntityModel(Minecraft.getInstance().entityModels.bakeLayer(SKIS_MODEL_LAYER))
            }

            basaltSkisModel.resetPose()
            basaltSkisModel.setupAnim(bipedEntityRenderState)
            queue.order(1).submitModel(
                basaltSkisModel,
                bipedEntityRenderState,
                matrices,
                RenderTypes.armorCutoutNoCull(BASALT_SKIS_LOCATION),
                light,
                OverlayTexture.NO_OVERLAY,
                bipedEntityRenderState.outlineColor,
                null
            )
        }

        HudElementRegistry.addFirst(NetherArchives.id("soul_glass_knife")) { context, _ ->
            if (usingSoulKnife)
                context.blit(
                    RenderPipelines.GUI_TEXTURED,
                    KNIFE_OVERLAY,
                    0,
                    0,
                    0.0f,
                    0.0f,
                    context.guiWidth(),
                    context.guiHeight(),
                    context.guiWidth(),
                    context.guiHeight(),
                )
        }

        with (ParticleProviderRegistry.getInstance()) {
            register(NetherArchivesParticles.BLAZE_FLAME, FlameParticle::Provider)
            register(NetherArchivesParticles.BLAZE_SPARK, BlazeSparkParticle::Factory)
            register(NetherArchivesParticles.SMALL_BLAZE_SPARK, BlazeSparkParticle::SmallFactory)
        }

        ClientTickEvents.START_LEVEL_TICK.register { world ->
            val client = Minecraft.getInstance()
            val camera = client.gameRenderer.mainCamera
            usingSoulKnife = !camera.isDetached && client.player == client.cameraEntity && (
                    client.player?.useItem?.isOf(NetherArchivesItems.SPECTREGLASS_KNIFE) == true
                    /*|| NetherArchives.EXPOSURE_INSTALLED && CameraClient.viewfinder()?.run { isLookingThrough && Attachment.FILTER.get(camera().itemStack).forReading isOf NetherArchivesItems.SPECTREGLASS_PANE } == true*/
            )

            if (usingSoulKnife) return@register // Can skip checks

            val cameraPos = camera.position()
            val distance = 64 * clamp(client.options.effectiveRenderDistance / 8.0, 1.0, 2.5) *
                    client.options.entityDistanceScaling().get()
            for (entity in world.getEntitiesOfClass(
                LivingEntity::class.java,
                AABB.ofSize(cameraPos, distance, distance, distance)
            ) {
                it.isInvisible || it.isInvisibleTo(client.player!!)
            }) {
                spectreglassRevealed[entity] = world.isBlockInLine(
                    ClipBlockStateContext(
                        cameraPos,
                        Vec3(entity.x, entity.getY(0.5), entity.z),
                    ) { it isIn NetherArchivesTags.REVEALS_INVISIBLES }).type != HitResult.Type.MISS
            }
        }
    }
}
