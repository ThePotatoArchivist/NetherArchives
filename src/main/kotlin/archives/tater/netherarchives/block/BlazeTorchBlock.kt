package archives.tater.netherarchives.block

import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.TorchBlock
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.core.BlockPos
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level

class BlazeTorchBlock(settings: Properties) : TorchBlock(ParticleTypes.FLAME, settings), AbstractBlazeTorchBlock {
    override fun animateTick(state: BlockState, world: Level, pos: BlockPos, random: RandomSource) {
        super<AbstractBlazeTorchBlock>.randomDisplayTick(
            state,
            world,
            pos,
            random,
            pos.x.toDouble() + 0.5,
            pos.y.toDouble() + 0.8125,
            pos.z.toDouble() + 0.5
        )
    }

    override fun onPlace(state: BlockState, world: Level, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        super<AbstractBlazeTorchBlock>.onBlockAdded(state, world, pos)
    }
}
