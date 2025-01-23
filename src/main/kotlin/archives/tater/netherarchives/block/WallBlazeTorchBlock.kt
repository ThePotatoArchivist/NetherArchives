package archives.tater.netherarchives.block

import net.minecraft.block.BlockState
import net.minecraft.block.WallTorchBlock
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.world.World

class WallBlazeTorchBlock(settings: Settings?) : WallTorchBlock(settings, ParticleTypes.FLAME),
    AbstractBlazeTorchBlock {
    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        val direction = state.get(FACING).opposite

        super<AbstractBlazeTorchBlock>.randomDisplayTick(
            state,
            world,
            pos,
            random,
            pos.x.toDouble() + 0.5 + 0.27 * direction.offsetX,
            pos.y.toDouble() + 0.8125 + 0.22,
            pos.z.toDouble() + 0.5 + 0.27 * direction.offsetZ
        )
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        super<AbstractBlazeTorchBlock>.onBlockAdded(state, world, pos)
    }
}
