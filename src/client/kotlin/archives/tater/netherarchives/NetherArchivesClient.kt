package archives.tater.netherarchives

import archives.tater.netherarchives.client.registerArmorRenderer
import archives.tater.netherarchives.client.render.entity.feature.WitherEyesFeatureRenderer
import archives.tater.netherarchives.client.render.entity.feature.WitherSkeletonEyesFeatureRenderer
import archives.tater.netherarchives.client.render.entity.model.SkisEntityModel
import archives.tater.netherarchives.client.render.particle.BlazeSparkParticle
import archives.tater.netherarchives.registry.NetherArchivesBlocks
import archives.tater.netherarchives.registry.NetherArchivesEntities
import archives.tater.netherarchives.registry.NetherArchivesItems
import archives.tater.netherarchives.registry.NetherArchivesParticles
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.particle.FlameParticle
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.entity.FlyingItemEntityRenderer
import net.minecraft.client.render.entity.WitherEntityRenderer
import net.minecraft.client.render.entity.WitherSkeletonEntityRenderer
import net.minecraft.client.render.entity.model.EntityModelLayer
import net.minecraft.client.render.model.json.ModelTransformationMode
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
    internal val soulGlassRevealed = WeakHashMap<LivingEntity, Boolean>()

    private val SKIS_MODEL_LAYER = EntityModelLayer(NetherArchives.id("skis"), "main")

    private val BASALT_SKIS_LOCATION = NetherArchives.id("textures/models/basalt_skis.png")
    private lateinit var basaltSkisModel: SkisEntityModel<LivingEntity>

    @JvmField
    val BASALT_OAR_IN_HAND_ID = NetherArchives.id("item/basalt_oar_in_hand")

    @JvmField
    internal val inHandRenderModes = setOf(
        ModelTransformationMode.FIRST_PERSON_LEFT_HAND,
        ModelTransformationMode.FIRST_PERSON_RIGHT_HAND,
        ModelTransformationMode.THIRD_PERSON_LEFT_HAND,
        ModelTransformationMode.THIRD_PERSON_RIGHT_HAND,
    )

    override fun onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        with (NetherArchivesBlocks) {
            BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                BLAZE_FIRE, BLAZE_DUST, BLAZE_TORCH, WALL_BLAZE_TORCH
            )
            BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderLayer.getTranslucent(),
                SOUL_GLASS,
                SHATTERED_SOUL_GLASS,
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
            matrices, vertexConsumers, stack, entity, slot, light, model ->
            if (!::basaltSkisModel.isInitialized) {
                basaltSkisModel = SkisEntityModel(MinecraftClient.getInstance().entityModelLoader.getModelPart(SKIS_MODEL_LAYER))
            }

            model.copyBipedStateTo(basaltSkisModel)
            ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, basaltSkisModel, BASALT_SKIS_LOCATION)
        }

        ModelLoadingPlugin.register { ctx ->
            ctx.addModels(BASALT_OAR_IN_HAND_ID)
        }

        ParticleFactoryRegistry.getInstance().register(NetherArchivesParticles.BLAZE_FLAME, FlameParticle::Factory)
        ParticleFactoryRegistry.getInstance().register(NetherArchivesParticles.BLAZE_SPARK, BlazeSparkParticle::Factory)
        ParticleFactoryRegistry.getInstance().register(NetherArchivesParticles.SMALL_BLAZE_SPARK, BlazeSparkParticle::SmallFactory)

        ClientTickEvents.START_WORLD_TICK.register { world ->
            val client = MinecraftClient.getInstance()
            val cameraPos = client.gameRenderer.camera.pos
            val distance = 64 * clamp(client.options.clampedViewDistance / 8.0, 1.0, 2.5) *
                    client.options.entityDistanceScaling.value
            for (entity in world.getEntitiesByClass(
                LivingEntity::class.java,
                Box.of(cameraPos, distance, distance, distance)
            ) {
                it.isInvisible || it.isInvisibleTo(client.player)
            }) {
                soulGlassRevealed[entity] = world.raycast(BlockStateRaycastContext(
                        cameraPos,
                        Vec3d(entity.x, entity.getBodyY(0.5), entity.z),
                    ) { it isOf NetherArchivesBlocks.SOUL_GLASS }).type != HitResult.Type.MISS
            }
        }
    }
}
