package archives.tater.netherarchives.item

import archives.tater.netherarchives.registry.NetherArchivesDamageTypes.paddleBurn
import archives.tater.netherarchives.registry.NetherArchivesTags
import archives.tater.netherarchives.registry.NetherArchivesTriggers
import archives.tater.netherarchives.util.get
import archives.tater.netherarchives.util.isIn
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.tags.FluidTags
import net.minecraft.util.Mth
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

class OarItem(settings: Properties) : Item(settings) {
    override fun use(world: Level, user: Player, hand: InteractionHand): InteractionResult {
        if (!SkisItem.isSkiing(user)) return super.use(world, user, hand)

        val fluidState = world.getFluidState(user.blockPosition())

        val itemStack = user[hand]

        val yawRads = user.yRot * Mth.DEG_TO_RAD
        if (user.isLocalInstanceAuthoritative) {
            user.deltaMovement = user.deltaMovement
                .yRot(yawRads)
                .run { if (z > 0) Vec3(x, y, z + VELOCITY) else Vec3(x, y, VELOCITY) }
                .yRot(-yawRads)
        }

        if (world is ServerLevel && fluidState isIn NetherArchivesTags.BURNS_WHEN_PADDLE)
            user.hurtServer(world, world.damageSources().paddleBurn, 1f)
        // TODO custom sounds
        if (fluidState isIn FluidTags.LAVA)
            user.playSound(SoundEvents.BUCKET_EMPTY_LAVA, 1f, 1f)
        else
            user.playSound(SoundEvents.BOAT_PADDLE_WATER, 3f, 1f)
        user.cooldowns.addCooldown(itemStack, 10)
        itemStack.hurtAndBreak(1, user, hand)
        user.causeFoodExhaustion(0.2f)
        if (user is ServerPlayer)
            NetherArchivesTriggers.SKIS_PADDLE.trigger(user, itemStack)
        return InteractionResult.SUCCESS.heldItemTransformedTo(itemStack)
    }

    companion object {
        const val VELOCITY = 0.3
    }
}
