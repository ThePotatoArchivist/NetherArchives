@file:Suppress("NOTHING_TO_INLINE")

package archives.tater.netherarchives

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.data.client.Model
import net.minecraft.data.client.TextureKey
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.random.Random
import java.util.*

// iterateInSquare uses the same blockPos object and mutates it, so we need this to use proper collection operations
internal fun Iterable<BlockPos>.listCopy(): List<BlockPos> = map(BlockPos::mutableCopy)

internal fun <T> Iterable<T>.draw(random: Random, count: Int = 1): List<T> {
    val pool = this.toMutableList()
    return (0 until count.coerceAtMost(pool.size))
        .map { pool.removeAt(random.nextInt(pool.size)) }
}

internal fun FabricBlockSettings(init: FabricBlockSettings.() -> Unit): FabricBlockSettings =
    FabricBlockSettings.create().apply(init)

internal fun FabricItemSettings(init: FabricItemSettings.() -> Unit): FabricItemSettings =
    FabricItemSettings().apply(init)

internal infix fun ItemStack.isIn(tag: TagKey<Item>): Boolean = this.isIn(tag)
internal infix fun FluidState.isIn(tag: TagKey<Fluid>): Boolean = this.isIn(tag)

internal inline operator fun Vec3d.plus(other: Vec3d) = add(other)

internal inline fun Model(vararg requiredTextureKeys: TextureKey, parent: Identifier? = null, variant: String? = null): Model =
    Model(Optional.ofNullable(parent), Optional.ofNullable(variant), *requiredTextureKeys)
