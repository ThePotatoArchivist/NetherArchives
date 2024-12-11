package archives.tater.netherarchives

import archives.tater.netherarchives.block.NetherArchivesBlocks
import archives.tater.netherarchives.client.registerArmorRenderer
import archives.tater.netherarchives.client.render.entity.feature.WitherEyesFeatureRenderer
import archives.tater.netherarchives.client.render.entity.feature.WitherSkeletonEyesFeatureRenderer
import archives.tater.netherarchives.client.render.entity.model.SkisEntityModel
import archives.tater.netherarchives.entity.NetherArchivesEntities
import archives.tater.netherarchives.item.NetherArchivesItems
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.entity.FlyingItemEntityRenderer
import net.minecraft.client.render.entity.WitherEntityRenderer
import net.minecraft.client.render.entity.WitherSkeletonEntityRenderer
import net.minecraft.client.render.entity.model.EntityModelLayer
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.util.Identifier

object NetherArchivesClient : ClientModInitializer {
    private val CUTOUT_BLOCKS = with(NetherArchivesBlocks) {
        setOf(BLAZE_FIRE, BLAZE_DUST, BLAZE_TORCH, WALL_BLAZE_TORCH)
    }

    val SKIS_MODEL_LAYER = EntityModelLayer(Identifier(NetherArchives.MOD_ID, "skis"), "main")

    private val BASALT_SKIS_LOCATION = Identifier(NetherArchives.MOD_ID, "textures/models/basalt_skis.png")
    private lateinit var basaltSkisModel: SkisEntityModel<LivingEntity>

    override fun onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        CUTOUT_BLOCKS.forEach {
            BlockRenderLayerMap.INSTANCE.putBlock(it, RenderLayer.getCutout())
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

    }
}
