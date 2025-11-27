package archives.tater.netherarchives.item

import archives.tater.netherarchives.registry.NetherArchivesDamageTypes.paddleBurn
import archives.tater.netherarchives.registry.NetherArchivesTags
import archives.tater.netherarchives.util.get
import archives.tater.netherarchives.util.isIn
import net.minecraft.world.entity.LivingEntity.getSlotForHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.tags.FluidTags
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.util.Mth
import net.minecraft.world.phys.Vec3
import net.minecraft.world.level.Level

class OarItem(settings: Properties) : Item(settings) {
    override fun use(world: Level, user: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        if (!SkisItem.isSkiing(user)) return super.use(world, user, hand)

        val fluidState = world.getFluidState(user.blockPosition())

        val itemStack = user[hand]

        val yawRads = user.yRot * Mth.DEG_TO_RAD
        if (user.isControlledByLocalInstance) {
            user.setDeltaMovement(
                user.deltaMovement
                .yRot(yawRads)
                .run { if (z > 0) Vec3(x, y, z + VELOCITY) else Vec3(x, y, VELOCITY) }
                .yRot(-yawRads))
        }

        if (fluidState isIn NetherArchivesTags.BURNS_WHEN_PADDLE)
            user.hurt(world.damageSources().paddleBurn, 1f)
        // TODO custom sounds
        if (fluidState isIn FluidTags.LAVA)
            user.playSound(SoundEvents.BUCKET_EMPTY_LAVA, 1f, 1f)
        else
            user.playSound(SoundEvents.BOAT_PADDLE_WATER, 3f, 1f)
        user.cooldowns.addCooldown(itemStack.item, 10)
        itemStack.hurtAndBreak(1, user, getSlotForHand(hand))
        user.causeFoodExhaustion(0.2f)
        return InteractionResultHolder.success(itemStack)
    }

    companion object {
        const val VELOCITY = 0.3
    }
}
