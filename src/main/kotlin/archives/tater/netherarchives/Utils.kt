@file:Suppress("NOTHING_TO_INLINE")

package archives.tater.netherarchives

import com.google.common.collect.AbstractIterator
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.component.type.AttributeModifiersComponent
import net.minecraft.data.client.Model
import net.minecraft.data.client.TextureKey
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.random.Random
import net.minecraft.world.World
import java.util.*
import net.minecraft.block.AbstractBlock.Settings as BlockSettings
import net.minecraft.item.Item.Settings as ItemSettings

// iterateInSquare uses the same blockPos object and mutates it, so we need this to use proper collection operations
internal fun Iterable<BlockPos>.listCopy(): List<BlockPos> = map(BlockPos::mutableCopy)

internal fun <T> Iterable<T>.draw(random: Random, count: Int = 1): List<T> {
    val pool = this.toMutableList()
    return (0 until count.coerceAtMost(pool.size))
        .map { pool.removeAt(random.nextInt(pool.size)) }
}

internal inline fun BlockSettings(init: BlockSettings.() -> Unit = {}): BlockSettings =
    BlockSettings.create().apply(init)

internal inline fun ItemSettings(init: ItemSettings.() -> Unit = {}): ItemSettings =
    ItemSettings().apply(init)

internal inline infix fun ItemStack.isIn(tag: TagKey<Item>): Boolean = this.isIn(tag)
internal inline infix fun FluidState.isIn(tag: TagKey<Fluid>): Boolean = this.isIn(tag)
internal inline infix fun EntityType<*>.isIn(tag: TagKey<EntityType<*>>): Boolean = this.isIn(tag)

inline infix fun BlockState.isOf(block: Block): Boolean = this.isOf(block)

internal inline operator fun Vec3d.plus(other: Vec3d) = add(other)

internal inline fun Model(vararg requiredTextureKeys: TextureKey, parent: Identifier? = null, variant: String? = null): Model =
    Model(Optional.ofNullable(parent), Optional.ofNullable(variant), *requiredTextureKeys)

internal inline operator fun LivingEntity.get(hand: Hand): ItemStack = getStackInHand(hand)
internal inline operator fun World.get(pos: BlockPos): BlockState = getBlockState(pos)
internal inline operator fun World.set(pos: BlockPos, state: BlockState) {
    setBlockState(pos, state)
}

internal inline operator fun Vec3d.unaryMinus(): Vec3d = multiply(-1.0)
internal inline operator fun Vec3d.minus(other: Vec3d): Vec3d = subtract(other)
internal inline operator fun Vec3d.component1() = x
internal inline operator fun Vec3d.component2() = y
internal inline operator fun Vec3d.component3() = z

fun iterateLinearBlockPos(origin: BlockPos, direction: Direction, distance: Int) = Iterable {
    object : AbstractIterator<BlockPos>() {
        private val pos = BlockPos.Mutable(origin.x, origin.y, origin.z)
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

fun AttributeModifiersComponent(init: AttributeModifiersComponent.Builder.() -> Unit): AttributeModifiersComponent =
    AttributeModifiersComponent.builder().apply(init).build()