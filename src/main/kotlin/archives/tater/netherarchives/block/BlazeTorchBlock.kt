package archives.tater.netherarchives.block

import net.minecraft.block.BlockState
import net.minecraft.block.TorchBlock
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.world.World

class BlazeTorchBlock(settings: Settings) : TorchBlock(ParticleTypes.FLAME, settings), AbstractBlazeTorchBlock {
    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
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

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        super<AbstractBlazeTorchBlock>.onBlockAdded(state, world, pos)
    }
}
