package archives.tater.netherarchives

import archives.tater.netherarchives.block.NetherArchivesBlocks
import archives.tater.netherarchives.datagen.BlockTagGenerator
import archives.tater.netherarchives.entity.NetherArchivesEntities
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.entity.FlyingItemEntityRenderer
import net.minecraft.client.world.ClientWorld
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.math.Box
import net.minecraft.world.BlockStateRaycastContext

object NetherArchivesClient : ClientModInitializer {
    private fun renderSoulTick(world: ClientWorld) {
        val player = world.players[0]
        world.getOtherEntities(player, Box.of(player.eyePos, 16.0, 16.0, 16.0)).forEach { entity ->
            val result = world.raycast(BlockStateRaycastContext(player.eyePos, entity.pos) {
                it.isIn(BlockTagGenerator.SOUL_INVISIBLE_REVEAL)
            })
            NetherArchives.logger.info("{}", result);
            if (entity.isInvisible && result != null) {
                world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, entity.eyePos.x, entity.eyePos.y, entity.eyePos.z, 0.0, 0.0, 0.0)
            }
        }
    }

    override fun onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        BlockRenderLayerMap.INSTANCE.putBlock(NetherArchivesBlocks.BLAZE_FIRE, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(NetherArchivesBlocks.BLAZE_DUST, RenderLayer.getCutout())
        BlockRenderLayerMap.INSTANCE.putBlock(NetherArchivesBlocks.SOUL_GLASS, RenderLayer.getTranslucent())

        EntityRendererRegistry.register(NetherArchivesEntities.BLAZE_LANTERN, ::FlyingItemEntityRenderer)

        ClientTickEvents.START_WORLD_TICK.register(::renderSoulTick)
    }
}
