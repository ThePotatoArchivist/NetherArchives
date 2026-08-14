package archives.tater.netherarchives.item

import archives.tater.netherarchives.component.AshAmount
import archives.tater.netherarchives.registry.ModBlocks
import archives.tater.netherarchives.registry.ModComponents
import archives.tater.netherarchives.registry.ModTags
import archives.tater.netherarchives.util.ceilDiv
import archives.tater.netherarchives.util.get
import archives.tater.netherarchives.util.isIn
import archives.tater.netherarchives.util.set
import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.SlotAccess
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ClickAction
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class NecroticUrnItem(properties: Properties) : Item(properties) {
    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResult {
        val stack = player[hand]
        val ashAmount = AshAmount.get(stack)
        if (ashAmount <= 0) return InteractionResult.FAIL

        if (level.isClientSide) return InteractionResult.CONSUME

        val center = player.blockPosition()
        val state = ModBlocks.NECROTIC_ASH.defaultBlockState()

        var count = 0
        for (pos in BlockPos.betweenClosed(center.x - H_RANGE, center.y - V_RANGE, center.z - H_RANGE, center.x + H_RANGE, center.y + H_RANGE, center.z + H_RANGE)) {
            if (center.distSqr(pos) > H_RANGE * H_RANGE) continue
            if (!level.isEmptyBlock(pos) || !state.canSurvive(level, pos)) continue

            level[pos] = state
            count++
        }

        if (count <= 0) return InteractionResult.FAIL

        level.playSound(null, player, SoundEvents.SOUL_SAND_PLACE, player.soundSource, 1f, 1f)

        if (!player.hasInfiniteMaterials())
            stack[ModComponents.ASH_AMOUNT] = AshAmount((ashAmount - (count ceilDiv ASH_PER_FLESH)).coerceAtLeast(0))

        return InteractionResult.SUCCESS_SERVER
    }

    override fun overrideOtherStackedOnMe(
        self: ItemStack,
        other: ItemStack,
        slot: Slot,
        clickAction: ClickAction,
        player: Player,
        carriedItem: SlotAccess
    ): Boolean = tryInsert(other, clickAction, self, player)

    override fun overrideStackedOnOther(
        self: ItemStack,
        slot: Slot,
        clickAction: ClickAction,
        player: Player
    ): Boolean = tryInsert(slot.item, clickAction, self, player)

    private fun tryInsert(
        other: ItemStack,
        clickAction: ClickAction,
        self: ItemStack,
        player: Player
    ): Boolean {
        if (!(other isIn ModTags.NECROTIC_URN_FUEL)) return false
        if (clickAction != ClickAction.PRIMARY) return false
        val amount = AshAmount.get(self)
        if (amount >= AshAmount.MAX) return false

        val transferred = (AshAmount.MAX - amount).coerceAtMost(other.count)

        other.shrink(transferred)
        self[ModComponents.ASH_AMOUNT] = AshAmount(amount + transferred)

        player.level().playLocalSound(player, SoundEvents.DECORATED_POT_INSERT, player.soundSource, 1f, 1f)

        return true
    }

    override fun getBarColor(stack: ItemStack): Int = 0xff68e2a3u.toInt()

    override fun getBarWidth(stack: ItemStack): Int = when (val amount = AshAmount.get(stack)) {
        0 -> 0
        AshAmount.MAX -> 13
        else -> 11 * (amount - 1) / 64 + 1
    }

    override fun isBarVisible(stack: ItemStack): Boolean = true

    companion object {
        const val H_RANGE = 8
        const val V_RANGE = 3
        const val ASH_PER_FLESH = 64
    }
}