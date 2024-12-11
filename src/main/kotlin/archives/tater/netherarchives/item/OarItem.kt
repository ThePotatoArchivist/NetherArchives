package archives.tater.netherarchives.item

import archives.tater.netherarchives.NetherArchivesTags
import archives.tater.netherarchives.isIn
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class OarItem(settings: Settings) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (!SkisItem.isSkiing(user)) return super.use(world, user, hand)

        val fluidState = world.getFluidState(user.blockPos)

        val itemStack = user.getStackInHand(hand)

        val yawRads = user.yaw * MathHelper.RADIANS_PER_DEGREE
        if (user.isLogicalSideForUpdatingMovement) {
            user.velocity = user.velocity
                .rotateY(yawRads)
                .run { if (z > 0) Vec3d(x, y, z + VELOCITY) else Vec3d(x, y, VELOCITY) }
                .rotateY(-yawRads)
//            Vec2f(user.velocity.x.toFloat(), user.velocity.z.toFloat())
//                .rotate(-user.yaw)
//                .run { if (x > 0) Vec2f(x + VELOCITY.toFloat(), y) else Vec2f(VELOCITY.toFloat(), y) }
//                .rotate(user.yaw)
        }

        if (fluidState isIn NetherArchivesTags.BURNS_WHEN_PADDLE)
            user.damage(world.damageSources.hotFloor(), 1f)
        user.playSound(SoundEvents.ENTITY_BOAT_PADDLE_WATER, 1f, 1f)
        user.itemCooldownManager.set(itemStack.item, 10)
        itemStack.damage(1, user) { it.sendToolBreakStatus(hand) }
        user.addExhaustion(0.2f)
        return TypedActionResult.success(itemStack)
    }

    companion object {
        const val VELOCITY = 0.3
    }
}
