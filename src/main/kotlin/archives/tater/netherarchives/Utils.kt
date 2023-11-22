package archives.tater.netherarchives

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random

// iterateInSquare uses the same blockPos object and mutates it, so we need this to use proper collection operations
fun Iterable<BlockPos>.listCopy(): List<BlockPos> = map(BlockPos::mutableCopy)

fun <T> Iterable<T>.draw(random: Random, count: Int = 1): List<T> {
    val pool = this.toMutableList()
    return (0 until count.coerceAtMost(pool.size))
        .map { pool.removeAt(random.nextInt(pool.size)) }
}
