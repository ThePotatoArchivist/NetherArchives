package archives.tater.netherarchives

import archives.tater.netherarchives.client.render.entity.feature.WitherEyesFeatureRenderer
import archives.tater.netherarchives.client.render.entity.feature.WitherSkeletonEyesFeatureRenderer
import archives.tater.netherarchives.client.render.entity.model.SkisEntityModel
import archives.tater.netherarchives.client.render.particle.BlazeSparkParticle
import archives.tater.netherarchives.client.util.registerArmorRenderer
import archives.tater.netherarchives.registry.*
import archives.tater.netherarchives.util.isIn
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import net.fabricmc.fabric.api.client.rendering.v1.*
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gl.RenderPipelines
import net.minecraft.client.particle.FlameParticle
import net.minecraft.client.render.BlockRenderLayer
import net.minecraft.client.render.entity.FlyingItemEntityRenderer
import net.minecraft.client.render.entity.WitherEntityRenderer
import net.minecraft.client.render.entity.WitherSkeletonEntityRenderer
import net.minecraft.client.render.entity.model.EntityModelLayer
import net.minecraft.client.render.entity.state.BipedEntityRenderState
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.Box
import net.minecraft.util.math.MathHelper.clamp
import net.minecraft.util.math.Vec3d
import net.minecraft.world.BlockStateRaycastContext
import java.util.*

object NetherArchivesClient : ClientModInitializer {

    @JvmField
    internal val spectreglassRevealed = WeakHashMap<LivingEntity, Boolean>()

    @JvmField
    internal var usingSoulKnife = false // TODO rename

    private val SKIS_MODEL_LAYER = EntityModelLayer(NetherArchives.id("skis"), "main")

    private val BASALT_SKIS_LOCATION = NetherArchives.id("textures/models/basalt_skis.png")
    private lateinit var basaltSkisModel: SkisEntityModel<BipedEntityRenderState>

    @JvmStatic
    fun isRevealed(entity: LivingEntity) = usingSoulKnife || spectreglassRevealed.getOrDefault(entity, false)

    @JvmField
    val KNIFE_OVERLAY = NetherArchives.id("textures/misc/knife_overlay.png")

    override fun onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        with (NetherArchivesBlocks) {
            BlockRenderLayerMap.putBlocks(BlockRenderLayer.CUTOUT,
                BLAZE_FIRE,
                BLAZE_DUST,
                BLAZE_TORCH,
                WALL_BLAZE_TORCH
            )
            BlockRenderLayerMap.putBlocks(
                BlockRenderLayer.TRANSLUCENT,
                SPECTREGLASS,
                SHATTERED_SPECTREGLASS,
                SPECTREGLASS_PANE,
                SHATTERED_SPECTREGLASS_PANE,
            )
        }

        EntityRendererRegistry.register(NetherArchivesEntities.BLAZE_LANTERN, ::FlyingItemEntityRenderer)

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register { entityType, entityRenderer, registrationHelper, _ ->
            if (NetherArchives.config.skeletonEyes)
                registrationHelper.register(when (entityType) {
                    EntityType.WITHER_SKELETON -> WitherSkeletonEyesFeatureRenderer(entityRenderer as WitherSkeletonEntityRenderer)
                    EntityType.WITHER -> WitherEyesFeatureRenderer(entityRenderer as WitherEntityRenderer)
                    else -> return@register
                })
        }

        EntityModelLayerRegistry.registerModelLayer(SKIS_MODEL_LAYER, SkisEntityModel.Companion::getTexturedModelData)

        registerArmorRenderer(NetherArchivesItems.BASALT_SKIS) {
                matrices, vertexConsumers, stack, _, _, light, model ->
            if (!::basaltSkisModel.isInitialized) {
                basaltSkisModel = SkisEntityModel(MinecraftClient.getInstance().loadedEntityModels.getModelPart(SKIS_MODEL_LAYER))
            }

            model.copyTransforms(basaltSkisModel)
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, basaltSkisModel, BASALT_SKIS_LOCATION)
        }

        HudElementRegistry.addFirst(NetherArchives.id("soul_glass_knife")) { context, _ ->
            if (usingSoulKnife)
                context.drawTexture(
                    RenderPipelines.GUI_TEXTURED,
                    KNIFE_OVERLAY,
                    0,
                    0,
                    0.0f,
                    0.0f,
                    context.scaledWindowWidth,
                    context.scaledWindowHeight,
                    context.scaledWindowWidth,
                    context.scaledWindowHeight,
                )
        }

        ParticleFactoryRegistry.getInstance().register(NetherArchivesParticles.BLAZE_FLAME, FlameParticle::Factory)
        ParticleFactoryRegistry.getInstance().register(NetherArchivesParticles.BLAZE_SPARK, BlazeSparkParticle::Factory)
        ParticleFactoryRegistry.getInstance().register(NetherArchivesParticles.SMALL_BLAZE_SPARK, BlazeSparkParticle::SmallFactory)

        ClientTickEvents.START_WORLD_TICK.register { world ->
            val client = MinecraftClient.getInstance()
            val camera = client.gameRenderer.camera
            usingSoulKnife = !camera.isThirdPerson && client.player == client.cameraEntity && client.player?.activeItem?.isOf(NetherArchivesItems.SPECTREGLASS_KNIFE) == true

            if (usingSoulKnife) return@register // Can skip checks

            val cameraPos = camera.pos
            val distance = 64 * clamp(client.options.clampedViewDistance / 8.0, 1.0, 2.5) *
                    client.options.entityDistanceScaling.value
            for (entity in world.getEntitiesByClass(
                LivingEntity::class.java,
                Box.of(cameraPos, distance, distance, distance)
            ) {
                it.isInvisible || it.isInvisibleTo(client.player)
            }) {
                spectreglassRevealed[entity] = world.raycast(BlockStateRaycastContext(
                        cameraPos,
                        Vec3d(entity.x, entity.getBodyY(0.5), entity.z),
                    ) { it isIn NetherArchivesTags.REVEALS_INVISIBLES }).type != HitResult.Type.MISS
            }
        }
    }
}
