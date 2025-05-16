package archives.tater.netherarchives.block

import archives.tater.netherarchives.get
import net.minecraft.block.BlockState
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.world.World

class PolishedBasaltGeyserBlock(settings: Settings) : BasaltGeyserBlock(settings) {

    override fun getPushDistance(world: World, pos: BlockPos, state: BlockState): Int =
        15 - world.getReceivedRedstonePower(pos)

    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        val facing = world[pos][FACING]
        val distance = getPushDistance(world, pos, state)
        if (distance <= 0) return
        repeat(2) {
            world.addFaceParticle(
                ParticleTypes.SMOKE,
                facing,
                pos,
                (distance / 4.0) * (0.15 + 0.15 * random.nextDouble()),
                posSpread = 0.3,
            )
        }
    }

    override fun addImportantParticles(world: World, pos: BlockPos, facing: Direction) {
    }
}
