package archives.tater.netherarchives.block

import net.minecraft.block.BlockState
import net.minecraft.block.WallTorchBlock
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.world.World

class WallBlazeTorchBlock(settings: Settings?) : WallTorchBlock(ParticleTypes.FLAME, settings),
    AbstractBlazeTorchBlock {
    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        val direction = state.get(FACING).opposite
        val x = pos.x.toDouble() + 0.5 + 0.27 * direction.offsetX
        val y = pos.y.toDouble() + 0.7 + 0.22
        val z = pos.z.toDouble() + 0.5 + 0.27 * direction.offsetZ

        super<AbstractBlazeTorchBlock>.randomDisplayTick(state, world, pos, random, x, y, z)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        super<AbstractBlazeTorchBlock>.onBlockAdded(state, world, pos)
    }
}
