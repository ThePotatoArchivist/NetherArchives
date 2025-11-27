package archives.tater.netherarchives.block

import archives.tater.netherarchives.registry.NetherArchivesParticles
import archives.tater.netherarchives.block.entity.BlazeTorchBlockEntity
import net.minecraft.world.level.block.Block.UPDATE_CLIENTS
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.core.BlockPos
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level


interface AbstractBlazeTorchBlock : EntityBlock {
    override fun newBlockEntity(pos: BlockPos, state: BlockState) = BlazeTorchBlockEntity(pos, state)

    fun onBlockAdded(
        state: BlockState,
        world: Level,
        pos: BlockPos,
    ) {
        if (world.isClientSide) return
        (world.getBlockEntity(pos) as BlazeTorchBlockEntity).locateTarget() ?: return
        world.sendBlockUpdated(pos, state, state, UPDATE_CLIENTS)
    }

    fun randomDisplayTick(
        state: BlockState,
        world: Level,
        pos: BlockPos,
        random: RandomSource,
        originX: Double,
        originY: Double,
        originZ: Double
    ) {
        val blockEntity = world.getBlockEntity(pos) as BlazeTorchBlockEntity

        world.addAlwaysVisibleParticle(
            NetherArchivesParticles.BLAZE_FLAME,
            originX,
            originY,
            originZ,
            0.1 * (blockEntity.xVelocityCoef ?: 0.0),
            0.02,
            0.1 * (blockEntity.zVelocityCoef ?: 0.0)
        )

        world.addParticle(
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
