package archives.tater.netherarchives.item

import archives.tater.netherarchives.registry.NetherArchivesDamageTypes.paddleBurn
import archives.tater.netherarchives.registry.NetherArchivesTags
import archives.tater.netherarchives.util.get
import archives.tater.netherarchives.util.isIn
import net.minecraft.entity.LivingEntity.getSlotForHand
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.registry.tag.FluidTags
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class OarItem(settings: Settings) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): ActionResult {
        if (!SkisItem.isSkiing(user)) return super.use(world, user, hand)

        val fluidState = world.getFluidState(user.blockPos)

        val itemStack = user[hand]

        val yawRads = user.yaw * MathHelper.RADIANS_PER_DEGREE
        if (user.isLogicalSideForUpdatingMovement) {
            user.velocity = user.velocity
                .rotateY(yawRads)
                .run { if (z > 0) Vec3d(x, y, z + VELOCITY) else Vec3d(x, y, VELOCITY) }
                .rotateY(-yawRads)
        }

        if (world is ServerWorld && fluidState isIn NetherArchivesTags.BURNS_WHEN_PADDLE)
            user.damage(world, world.damageSources.paddleBurn, 1f)
        // TODO custom sounds
        if (fluidState isIn FluidTags.LAVA)
            user.playSound(SoundEvents.ITEM_BUCKET_EMPTY_LAVA, 1f, 1f)
        else
            user.playSound(SoundEvents.ENTITY_BOAT_PADDLE_WATER, 3f, 1f)
        user.itemCooldownManager.set(itemStack, 10)
        itemStack.damage(1, user, getSlotForHand(hand))
        user.addExhaustion(0.2f)
        return ActionResult.SUCCESS.withNewHandStack(itemStack)
    }

    companion object {
        const val VELOCITY = 0.3
    }
}
