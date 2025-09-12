package archives.tater.netherarchives.item

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.util.AttributeModifiersComponent
import archives.tater.netherarchives.util.get
import net.minecraft.block.BlockState
import net.minecraft.component.type.AttributeModifierSlot
import net.minecraft.component.type.AttributeModifiersComponent
import net.minecraft.component.type.ToolComponent
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class SoulGlassKnifeItem(settings: Settings) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        user.setCurrentHand(hand)
        return TypedActionResult.consume(user[hand])
    }

    override fun getMaxUseTime(stack: ItemStack?, user: LivingEntity?): Int = MAX_USE_TIME

    override fun getUseAction(stack: ItemStack?): UseAction = UseAction.SPYGLASS

    override fun finishUsing(stack: ItemStack, world: World, user: LivingEntity): ItemStack {
        if (world.isClient) return stack
        (user as? ServerPlayerEntity)?.setKnifeCooldown(MAX_USE_TIME)
        return stack
    }

    override fun onStoppedUsing(stack: ItemStack, world: World, user: LivingEntity, remainingUseTicks: Int) {
        if (world.isClient) return
        (user as? ServerPlayerEntity)?.setKnifeCooldown(MAX_USE_TIME - remainingUseTicks)
    }

    private fun PlayerEntity.setKnifeCooldown(usedTicks: Int) {
        itemCooldownManager[this@SoulGlassKnifeItem] = usedTicks.coerceAtLeast(20) * 400 / MAX_USE_TIME // Max 20 seconds
    }

    override fun canMine(state: BlockState?, world: World?, pos: BlockPos?, miner: PlayerEntity): Boolean =
        !miner.isCreative

    override fun postHit(stack: ItemStack?, target: LivingEntity?, attacker: LivingEntity?): Boolean = true

    override fun postDamageEntity(stack: ItemStack, target: LivingEntity, attacker: LivingEntity) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND)
    }

    companion object {
        private const val MAX_USE_TIME = 100 // 5 seconds

        private val BASE_ENTITY_INTERACTION_MODIFIER_ID = NetherArchives.id("base_entity_interaction_range")

        val attributeModifiers: AttributeModifiersComponent
            get() = AttributeModifiersComponent {
                add(
                    EntityAttributes.GENERIC_ATTACK_DAMAGE,
                    EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 4.0, Operation.ADD_VALUE),
                    AttributeModifierSlot.MAINHAND,
                )
                add(
                    EntityAttributes.GENERIC_ATTACK_SPEED,
                    EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -2.0, Operation.ADD_VALUE),
                    AttributeModifierSlot.MAINHAND,
                )
                add(
                    EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                    EntityAttributeModifier(BASE_ENTITY_INTERACTION_MODIFIER_ID, -0.5, Operation.ADD_VALUE),
                    AttributeModifierSlot.MAINHAND,
                )
            }

        val toolComponent: ToolComponent
            get() = ToolComponent(listOf(), 1f, 2)
    }
}