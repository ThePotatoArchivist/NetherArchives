package archives.tater.netherarchives.item

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.util.AttributeModifiersComponent
import archives.tater.netherarchives.util.get
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.UseAnim
import net.minecraft.world.item.component.ItemAttributeModifiers
import net.minecraft.world.item.component.Tool
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class SoulGlassKnifeItem(settings: Properties) : Item(settings) {
    override fun use(world: Level, user: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        user.startUsingItem(hand)
        return InteractionResultHolder.consume(user[hand])
    }

    override fun getUseDuration(stack: ItemStack?, user: LivingEntity?): Int = MAX_USE_TIME

    override fun getUseAnimation(stack: ItemStack?): UseAnim = UseAnim.SPYGLASS

    override fun finishUsingItem(stack: ItemStack, world: Level, user: LivingEntity): ItemStack {
        if (world.isClientSide) return stack
        (user as? ServerPlayer)?.setKnifeCooldown(MAX_USE_TIME)
        return stack
    }

    override fun releaseUsing(stack: ItemStack, world: Level, user: LivingEntity, remainingUseTicks: Int) {
        if (world.isClientSide) return
        (user as? ServerPlayer)?.setKnifeCooldown(MAX_USE_TIME - remainingUseTicks)
    }

    private fun Player.setKnifeCooldown(usedTicks: Int) {
        cooldowns.addCooldown(this@SoulGlassKnifeItem, usedTicks.coerceAtLeast(20) * 400 / MAX_USE_TIME) // Max 20 seconds
    }

    override fun canAttackBlock(state: BlockState?, world: Level?, pos: BlockPos?, miner: Player): Boolean =
        !miner.isCreative

    override fun hurtEnemy(stack: ItemStack?, target: LivingEntity?, attacker: LivingEntity?): Boolean = true

    override fun postHurtEnemy(stack: ItemStack, target: LivingEntity, attacker: LivingEntity) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND)
    }

    companion object {
        private const val MAX_USE_TIME = 100 // 5 seconds

        private val BASE_ENTITY_INTERACTION_MODIFIER_ID = NetherArchives.id("base_entity_interaction_range")

        val attributeModifiers: ItemAttributeModifiers
            get() = AttributeModifiersComponent {
                add(
                    Attributes.ATTACK_DAMAGE,
                    AttributeModifier(BASE_ATTACK_DAMAGE_ID, 4.0, Operation.ADD_VALUE),
                    EquipmentSlotGroup.MAINHAND,
                )
                add(
                    Attributes.ATTACK_SPEED,
                    AttributeModifier(BASE_ATTACK_SPEED_ID, -2.0, Operation.ADD_VALUE),
                    EquipmentSlotGroup.MAINHAND,
                )
                add(
                    Attributes.ENTITY_INTERACTION_RANGE,
                    AttributeModifier(BASE_ENTITY_INTERACTION_MODIFIER_ID, -0.5, Operation.ADD_VALUE),
                    EquipmentSlotGroup.MAINHAND,
                )
            }

        val toolComponent: Tool
            get() = Tool(listOf(), 1f, 2)
    }
}
