package archives.tater.netherarchives

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec2f
import net.minecraft.util.math.random.Random
import kotlin.math.cos
import kotlin.math.sin

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

infix fun ItemStack.isIn(tag: TagKey<Item>): Boolean = this.isIn(tag)
infix fun FluidState.isIn(tag: TagKey<Fluid>): Boolean = this.isIn(tag)

/**
 * Counterclockwise
 */
fun Vec2f.rotate(radians: Float) = Vec2f(
    x * cos(radians) - y * sin(radians),
    x * sin(radians) + y * cos(radians),
)
