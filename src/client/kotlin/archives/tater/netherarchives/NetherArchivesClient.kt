package archives.tater.netherarchives

import archives.tater.netherarchives.block.NetherArchivesBlocks
import archives.tater.netherarchives.client.render.entity.feature.WitherEyesFeatureRenderer
import archives.tater.netherarchives.client.render.entity.feature.WitherSkeletonEyesFeatureRenderer
import archives.tater.netherarchives.entity.NetherArchivesEntities
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.entity.FlyingItemEntityRenderer
import net.minecraft.client.render.entity.WitherEntityRenderer
import net.minecraft.client.render.entity.WitherSkeletonEntityRenderer
import net.minecraft.entity.EntityType

object NetherArchivesClient : ClientModInitializer {
    private val CUTOUT_BLOCKS = with(NetherArchivesBlocks) {
        setOf(BLAZE_FIRE, BLAZE_DUST, BLAZE_TORCH, WALL_BLAZE_TORCH)
    }

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
    }
}
