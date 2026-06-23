package archives.tater.netherarchives.item

import archives.tater.netherarchives.registry.ModTags
import archives.tater.netherarchives.registry.NetherArchivesDamageTypes.paddleBurn
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
    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResult {
        if (!SkisItem.isSkiing(player)) return super.use(level, player, hand)

        val fluidState = level.getFluidState(player.blockPosition())

        val itemStack = player[hand]

        val yawRads = player.yRot * Mth.DEG_TO_RAD
        if (player.isLocalInstanceAuthoritative) {
            player.deltaMovement = player.deltaMovement
                .yRot(yawRads)
                .run { if (z > 0) Vec3(x, y, z + VELOCITY) else Vec3(x, y, VELOCITY) }
                .yRot(-yawRads)
        }

        if (level is ServerLevel && fluidState isIn ModTags.BURNS_WHEN_PADDLE)
            player.hurtServer(level, level.damageSources().paddleBurn, 1f)
        // TODO custom sounds
        if (fluidState isIn FluidTags.LAVA)
            player.playSound(SoundEvents.BUCKET_EMPTY_LAVA, 1f, 1f)
        else
            player.playSound(SoundEvents.BOAT_PADDLE_WATER, 3f, 1f)
        player.cooldowns.addCooldown(itemStack, 10)
        itemStack.hurtAndBreak(1, player, hand)
        player.causeFoodExhaustion(0.2f)
        if (player is ServerPlayer)
            NetherArchivesTriggers.SKIS_PADDLE.trigger(player, itemStack)
        return InteractionResult.SUCCESS.heldItemTransformedTo(itemStack)
    }

    companion object {
        const val VELOCITY = 0.3
    }
}
