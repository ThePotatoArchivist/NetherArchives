package archives.tater.netherarchives

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random

// iterateInSquare uses the same blockPos object and mutates it, so we need this to use proper collection operations
fun Iterable<BlockPos>.listCopy(): List<BlockPos> = map(BlockPos::mutableCopy)

fun <T> Iterable<T>.shuffled(minecraftRandom: Random): List<T> {
    return shuffled(object: java.util.Random() {
        override fun nextInt(): Int = minecraftRandom.nextInt()
    })
}
