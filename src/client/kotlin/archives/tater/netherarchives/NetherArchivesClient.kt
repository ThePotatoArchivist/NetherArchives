package archives.tater.netherarchives

import archives.tater.netherarchives.client.render.entity.feature.WitherEyesFeatureRenderer
import archives.tater.netherarchives.client.render.entity.feature.WitherSkeletonEyesFeatureRenderer
import archives.tater.netherarchives.client.render.entity.model.SkisEntityModel
import archives.tater.netherarchives.client.render.particle.BlazeSparkParticle
import archives.tater.netherarchives.client.util.registerArmorRenderer
import archives.tater.netherarchives.registry.*
import archives.tater.netherarchives.util.isIn
import archives.tater.netherarchives.util.isOf
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback
import net.minecraft.client.Minecraft
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.particle.FlameParticle
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.ThrownItemRenderer
import net.minecraft.client.renderer.entity.WitherBossRenderer
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer
import net.minecraft.client.renderer.item.ItemProperties
import net.minecraft.util.Mth.clamp
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.level.ClipBlockStateContext
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3
import io.github.mortuusars.exposure.client.camera.CameraClient
import io.github.mortuusars.exposure.world.item.camera.Attachment
import java.util.*

object NetherArchivesClient : ClientModInitializer {

    @JvmField
    internal val spectreglassRevealed = WeakHashMap<LivingEntity, Boolean>()

    @JvmField
    internal var usingSoulKnife = false // TODO rename

    private val SKIS_MODEL_LAYER = ModelLayerLocation(NetherArchives.id("skis"), "main")

    private val BASALT_SKIS_LOCATION = NetherArchives.id("textures/models/basalt_skis.png")
    private lateinit var basaltSkisModel: SkisEntityModel<LivingEntity>

    @JvmField
    val BASALT_OAR_IN_HAND_ID = NetherArchives.id("item/basalt_oar_in_hand")

    @JvmField
    internal val inHandRenderModes = setOf(
        ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
        ItemDisplayContext.FIRST_PERSON_RIGHT_HAND,
        ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
        ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
    )

    @JvmStatic
    fun isRevealed(entity: LivingEntity) = usingSoulKnife || spectreglassRevealed.getOrDefault(entity, false)

    @JvmField
    val KNIFE_OVERLAY = NetherArchives.id("textures/misc/knife_overlay.png")

    override fun onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        with (NetherArchivesBlocks) {
            BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderType.cutout(),
                BLAZE_FIRE,
                BLAZE_DUST,
                BLAZE_TORCH,
                WALL_BLAZE_TORCH
            )
            BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderType.translucent(),
                SPECTREGLASS,
                SHATTERED_SPECTREGLASS,
                SPECTREGLASS_PANE,
                SHATTERED_SPECTREGLASS_PANE,
            )
        }

        EntityRendererRegistry.register(NetherArchivesEntities.BLAZE_LANTERN, ::ThrownItemRenderer)

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register { entityType, entityRenderer, registrationHelper, _ ->
            if (NetherArchivesClientConfig.config.skeletonEyes)
                registrationHelper.register(when (entityType) {
                    EntityType.WITHER_SKELETON -> WitherSkeletonEyesFeatureRenderer(entityRenderer as WitherSkeletonRenderer)
                    EntityType.WITHER -> WitherEyesFeatureRenderer(entityRenderer as WitherBossRenderer)
                    else -> return@register
                })
        }

        EntityModelLayerRegistry.registerModelLayer(SKIS_MODEL_LAYER, SkisEntityModel.Companion::getTexturedModelData)

        registerArmorRenderer(NetherArchivesItems.BASALT_SKIS) {
            matrices, vertexConsumers, stack, _, _, light, model ->
            if (!::basaltSkisModel.isInitialized) {
                basaltSkisModel = SkisEntityModel(Minecraft.getInstance().entityModels.bakeLayer(SKIS_MODEL_LAYER))
            }

            model.copyPropertiesTo(basaltSkisModel)
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, basaltSkisModel, BASALT_SKIS_LOCATION)
        }

        ModelLoadingPlugin.register { ctx ->
            ctx.addModels(BASALT_OAR_IN_HAND_ID)
        }

        ItemProperties.register(NetherArchivesItems.SPECTREGLASS_KNIFE, NetherArchives.id("viewing")) { stack, _, entity, _ ->
            if (entity is Player && entity.isUsingItem && entity.useItem == stack) 1f else 0f
        }

        ParticleFactoryRegistry.getInstance().register(NetherArchivesParticles.BLAZE_FLAME, FlameParticle::Provider)
        ParticleFactoryRegistry.getInstance().register(NetherArchivesParticles.BLAZE_SPARK, BlazeSparkParticle::Factory)
        ParticleFactoryRegistry.getInstance().register(NetherArchivesParticles.SMALL_BLAZE_SPARK, BlazeSparkParticle::SmallFactory)

        ClientTickEvents.START_WORLD_TICK.register { world ->
            val client = Minecraft.getInstance()
            val camera = client.gameRenderer.mainCamera
            usingSoulKnife = !camera.isDetached && client.player == client.cameraEntity && (
                    client.player?.useItem?.isOf(NetherArchivesItems.SPECTREGLASS_KNIFE) == true
                    || NetherArchives.EXPOSURE_INSTALLED && CameraClient.viewfinder()?.run { isLookingThrough && Attachment.FILTER.get(camera().itemStack).forReading isOf NetherArchivesItems.SPECTREGLASS_PANE } == true
            )

            if (usingSoulKnife) return@register // Can skip checks

            val cameraPos = camera.position
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
