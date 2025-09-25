package archives.tater.netherarchives.block

import archives.tater.netherarchives.block.entity.BlazeTorchBlockEntity
import archives.tater.netherarchives.registry.NetherArchivesParticles
import net.minecraft.block.Block.NOTIFY_LISTENERS
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.world.World


interface AbstractBlazeTorchBlock : BlockEntityProvider {
    override fun createBlockEntity(pos: BlockPos, state: BlockState) = BlazeTorchBlockEntity(pos, state)

    fun onBlockAdded(
        state: BlockState,
        world: World,
        pos: BlockPos,
    ) {
        if (world.isClient) return
        (world.getBlockEntity(pos) as BlazeTorchBlockEntity).locateTarget() ?: return
        world.updateListeners(pos, state, state, NOTIFY_LISTENERS)
    }

    fun randomDisplayTick(
        state: BlockState,
        world: World,
        pos: BlockPos,
        random: Random,
        originX: Double,
        originY: Double,
        originZ: Double
    ) {
        val blockEntity = world.getBlockEntity(pos) as BlazeTorchBlockEntity

        world.addImportantParticleClient(
            NetherArchivesParticles.BLAZE_FLAME,
            originX,
            originY,
            originZ,
            0.1 * (blockEntity.xVelocityCoef ?: 0.0),
            0.02,
            0.1 * (blockEntity.zVelocityCoef ?: 0.0)
        )

        world.addParticleClient(
            NetherArchivesParticles.SMALL_BLAZE_SPARK,
            originX + 0.4 * random.nextDouble() - 0.2,
            originY + 0.4 * random.nextDouble() - 0.2,
            originZ + 0.4 * random.nextDouble() - 0.2,
            0.0,
            0.0,
            0.0,
        )

    }
}
