@file:Suppress("NOTHING_TO_INLINE")

package archives.tater.netherarchives.util

import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.core.HolderGetter
import net.minecraft.core.TypedInstance
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.TagKey
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.ItemAttributeModifiers
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3
import net.minecraft.world.item.Item.Properties as ItemProperties
import net.minecraft.world.level.block.state.BlockBehaviour.Properties as BlockProperties

internal inline fun BlockProperties(init: BlockProperties.() -> Unit = {}): BlockProperties =
    BlockProperties.of().apply(init)

internal inline fun ItemProperties(init: ItemProperties.() -> Unit = {}): ItemProperties =
    ItemProperties().apply(init)

infix fun <T: Any> TypedInstance<T>.isOf(type: T) = this.`is`(type)
infix fun <T: Any> TypedInstance<T>.isIn(tag: TagKey<T>) = this.`is`(tag)

internal inline operator fun LivingEntity.get(hand: InteractionHand): ItemStack = getItemInHand(hand)
internal inline operator fun Level.get(pos: BlockPos): BlockState = getBlockState(pos)
internal inline operator fun Level.set(pos: BlockPos, state: BlockState) {
    setBlockAndUpdate(pos, state)
}

internal inline operator fun Vec3.plus(other: Vec3) = add(other)
internal inline operator fun Vec3.unaryMinus(): Vec3 = scale(-1.0)
internal inline operator fun Vec3.minus(other: Vec3): Vec3 = subtract(other)
internal inline operator fun Vec3.component1() = x
internal inline operator fun Vec3.component2() = y
internal inline operator fun Vec3.component3() = z

fun ItemAttributeModifiers(init: ItemAttributeModifiers.Builder.() -> Unit): ItemAttributeModifiers =
    ItemAttributeModifiers.builder().apply(init).build()

operator fun <T: Any> HolderGetter<T>.get(key: ResourceKey<T>): Holder.Reference<T> = getOrThrow(key)

inline var Entity.pos: Vec3
    get() = position()
    set(value) {
        setPos(value)
    }
