package archives.tater.netherarchives

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random

// iterateInSquare uses the same blockPos object and mutates it, so we need this to use proper collection operations
fun Iterable<BlockPos>.listCopy(): List<BlockPos> = map(BlockPos::mutableCopy)

fun <T> Iterable<T>.draw(random: Random, count: Int = 1): List<T> {
    val pool = this.toMutableList()
    return (0 until count.coerceAtMost(pool.size))
        .map { pool.removeAt(random.nextInt(pool.size)) }
}

fun FabricBlockSettings(init: FabricBlockSettings.() -> Unit): FabricBlockSettings =
    FabricBlockSettings.create().apply(init)

fun FabricItemSettings(init: FabricItemSettings.() -> Unit): FabricItemSettings =
    FabricItemSettings().apply(init)
