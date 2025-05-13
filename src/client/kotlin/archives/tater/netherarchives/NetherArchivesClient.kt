package archives.tater.netherarchives

import archives.tater.netherarchives.block.NetherArchivesBlocks
import archives.tater.netherarchives.block.entity.NetherArchivesBlockEntities
import archives.tater.netherarchives.client.registerArmorRenderer
import archives.tater.netherarchives.client.render.blockentity.SoulGlassBlockEntityRenderer
import archives.tater.netherarchives.client.render.entity.feature.WitherEyesFeatureRenderer
import archives.tater.netherarchives.client.render.entity.feature.WitherSkeletonEyesFeatureRenderer
import archives.tater.netherarchives.client.render.entity.model.SkisEntityModel
import archives.tater.netherarchives.client.render.particle.BlazeSparkParticle
import archives.tater.netherarchives.entity.NetherArchivesEntities
import archives.tater.netherarchives.item.NetherArchivesItems
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import net.fabricmc.fabric.api.client.rendering.v1.*
import net.minecraft.client.MinecraftClient
import net.minecraft.client.particle.FlameParticle
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories
import net.minecraft.client.render.entity.FlyingItemEntityRenderer
import net.minecraft.client.render.entity.WitherEntityRenderer
import net.minecraft.client.render.entity.WitherSkeletonEntityRenderer
import net.minecraft.client.render.entity.model.EntityModelLayer
import net.minecraft.client.render.model.json.ModelTransformationMode
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity

object NetherArchivesClient : ClientModInitializer {

    private val SKIS_MODEL_LAYER = EntityModelLayer(NetherArchives.id("skis"), "main")

    private val BASALT_SKIS_LOCATION = NetherArchives.id("textures/models/basalt_skis.png")
    private lateinit var basaltSkisModel: SkisEntityModel<LivingEntity>

    private val BASALT_OAR_IN_HAND_ID = NetherArchives.id("item/basalt_oar_in_hand")
    private val BASALT_OAR_IN_HAND_MODEL by lazy {
        MinecraftClient.getInstance().bakedModelManager.getModel(BASALT_OAR_IN_HAND_ID)
    }
    private val BASALT_OAR_INVENTORY_ID = NetherArchives.id("item/basalt_oar_inventory")
    private val BASALT_OAR_INVENTORY_MODEL by lazy {
        MinecraftClient.getInstance().bakedModelManager.getModel(BASALT_OAR_INVENTORY_ID)
    }

    private val inHandRenderModes = setOf(
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
            BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
                SOUL_GLASS,
                SHATTERED_SOUL_GLASS,
            )
        }

        EntityRendererRegistry.register(NetherArchivesEntities.BLAZE_LANTERN, ::FlyingItemEntityRenderer)

        BlockEntityRendererFactories.register(NetherArchivesBlockEntities.SOUL_GLASS_BLOCK_ENTITY, ::SoulGlassBlockEntityRenderer)

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
            ctx.addModels(BASALT_OAR_INVENTORY_ID)
        }

        BuiltinItemRendererRegistry.INSTANCE.register(NetherArchivesItems.BASALT_OAR) {
            stack, mode, matrices, vertexConsumers, light, overlay ->
            matrices.push()
            matrices.translate(0.5, 0.5, 0.5)
            MinecraftClient.getInstance().itemRenderer.renderItem(
                stack,
                mode,
                mode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND || mode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND,
                matrices,
                vertexConsumers,
                light,
                overlay,
                MinecraftClient.getInstance().bakedModelManager.getModel(
                    if (mode in inHandRenderModes) BASALT_OAR_IN_HAND_ID else BASALT_OAR_INVENTORY_ID)
            )
            matrices.pop()
        }

        ParticleFactoryRegistry.getInstance().register(NetherArchivesParticles.BLAZE_FLAME, FlameParticle::Factory)
        ParticleFactoryRegistry.getInstance().register(NetherArchivesParticles.BLAZE_SPARK, BlazeSparkParticle::Factory)
        ParticleFactoryRegistry.getInstance().register(NetherArchivesParticles.SMALL_BLAZE_SPARK, BlazeSparkParticle::SmallFactory)
    }
}
