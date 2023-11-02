package archives.tater.netherarchives

import archives.tater.netherarchives.block.NetherArchivesBlocks
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.render.RenderLayer

object NetherArchivesClient : ClientModInitializer {
    override fun onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        BlockRenderLayerMap.INSTANCE.putBlock(NetherArchivesBlocks.BLAZE_FIRE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(NetherArchivesBlocks.BLAZE_POWDER_BLOCK, RenderLayer.getCutout())
    }
}
