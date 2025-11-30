package archives.tater.netherarchives.block

import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.WallTorchBlock
import net.minecraft.world.level.block.state.BlockState

class WallBlazeTorchBlock(settings: Properties) : WallTorchBlock(ParticleTypes.FLAME, settings),
    AbstractBlazeTorchBlock {
    override fun animateTick(state: BlockState, world: Level, pos: BlockPos, random: RandomSource) {
        val direction = state.getValue(FACING).opposite

        super<AbstractBlazeTorchBlock>.animateTick(
            state,
            world,
            pos,
            random,
            pos.x.toDouble() + 0.5 + 0.27 * direction.stepX,
            pos.y.toDouble() + 0.8125 + 0.22,
            pos.z.toDouble() + 0.5 + 0.27 * direction.stepZ
        )
    }

    override fun onPlace(state: BlockState, world: Level, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        super<AbstractBlazeTorchBlock>.onPlace(state, world, pos)
    }
}
