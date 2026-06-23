package archives.tater.netherarchives.block

import archives.tater.netherarchives.block.entity.BlazeTorchBlockEntity
import archives.tater.netherarchives.registry.NetherArchivesParticles
import net.minecraft.core.BlockPos
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block.UPDATE_CLIENTS
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.state.BlockState


interface AbstractBlazeTorchBlock : EntityBlock {
    override fun newBlockEntity(pos: BlockPos, state: BlockState) = BlazeTorchBlockEntity(pos, state)

    fun onPlace(
        state: BlockState,
        level: Level,
        pos: BlockPos,
    ) {
        if (level.isClientSide) return
        (level.getBlockEntity(pos) as BlazeTorchBlockEntity).locateTarget() ?: return
        level.sendBlockUpdated(pos, state, state, UPDATE_CLIENTS)
    }

    fun animateTick(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        random: RandomSource,
        originX: Double,
        originY: Double,
        originZ: Double
    ) {
        val blockEntity = level.getBlockEntity(pos) as BlazeTorchBlockEntity

        level.addAlwaysVisibleParticle(
            NetherArchivesParticles.BLAZE_FLAME,
            originX,
            originY,
            originZ,
            0.1 * (blockEntity.xVelocityCoef ?: 0.0),
            0.02,
            0.1 * (blockEntity.zVelocityCoef ?: 0.0)
        )

        level.addParticle(
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
