@file:Suppress("NOTHING_TO_INLINE")

package archives.tater.netherarchives.util

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.component.type.AttributeModifiersComponent
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryEntryLookup
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraft.block.AbstractBlock.Settings as BlockSettings
import net.minecraft.item.Item.Settings as ItemSettings

internal inline fun BlockSettings(init: BlockSettings.() -> Unit = {}): BlockSettings =
    BlockSettings.create().apply(init)

internal inline fun ItemSettings(init: ItemSettings.() -> Unit = {}): ItemSettings =
    ItemSettings().apply(init)

internal inline infix fun ItemStack.isIn(tag: TagKey<Item>): Boolean = this.isIn(tag)
internal inline infix fun FluidState.isIn(tag: TagKey<Fluid>): Boolean = this.isIn(tag)
internal inline infix fun EntityType<*>.isIn(tag: TagKey<EntityType<*>>): Boolean = this.isIn(tag)
inline infix fun BlockState.isIn(tag: TagKey<Block>): Boolean = this.isIn(tag)

inline infix fun BlockState.isOf(block: Block): Boolean = this.isOf(block)

internal inline operator fun LivingEntity.get(hand: Hand): ItemStack = getStackInHand(hand)
internal inline operator fun World.get(pos: BlockPos): BlockState = getBlockState(pos)
internal inline operator fun World.set(pos: BlockPos, state: BlockState) {
    setBlockState(pos, state)
}

internal inline operator fun Vec3d.plus(other: Vec3d) = add(other)
internal inline operator fun Vec3d.unaryMinus(): Vec3d = multiply(-1.0)
internal inline operator fun Vec3d.minus(other: Vec3d): Vec3d = subtract(other)
internal inline operator fun Vec3d.component1() = x
internal inline operator fun Vec3d.component2() = y
internal inline operator fun Vec3d.component3() = z

fun AttributeModifiersComponent(init: AttributeModifiersComponent.Builder.() -> Unit): AttributeModifiersComponent =
    AttributeModifiersComponent.builder().apply(init).build()

operator fun <T> RegistryWrapper.WrapperLookup.get(registryRef: RegistryKey<Registry<T>>): RegistryWrapper.Impl<T> =
    getOrThrow(registryRef)
operator fun <T> RegistryEntryLookup<T>.get(key: RegistryKey<T>): RegistryEntry.Reference<T> = getOrThrow(key)