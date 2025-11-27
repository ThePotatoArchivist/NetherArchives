package archives.tater.netherarchives.util

import com.google.common.collect.AbstractIterator
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.RandomSource

// iterateInSquare uses the same blockPos object and mutates it, so we need this to use proper collection operations
internal fun Iterable<BlockPos>.listCopy(): List<BlockPos> = map(BlockPos::mutable)

internal fun <T> Iterable<T>.draw(random: RandomSource, count: Int = 1): List<T> {
    val pool = this.toMutableList()
    return (0 until count.coerceAtMost(pool.size))
        .map { pool.removeAt(random.nextInt(pool.size)) }
}

fun iterateLinearBlockPos(origin: BlockPos, direction: Direction, distance: Int) = Iterable {
    object : AbstractIterator<BlockPos>() {
        private val pos = BlockPos.MutableBlockPos(origin.x, origin.y, origin.z)
        private var index = 0

        override fun computeNext(): BlockPos? {
            pos.move(direction)
            index++
            if (index > distance)
                return endOfData()
            return pos
        }
    }
}

internal fun invertArgb(argb: Int): Int {
    return ((argb and 0xff000000u.toInt())
        or (0x00ffffff - (argb and 0x00ffffff)))
}
