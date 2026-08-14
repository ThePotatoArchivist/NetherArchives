package archives.tater.netherarchives.component

import archives.tater.netherarchives.registry.ModComponents
import com.mojang.serialization.Codec
import net.minecraft.ChatFormatting
import net.minecraft.core.component.DataComponentGetter
import net.minecraft.network.chat.Component
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.component.TooltipProvider
import java.util.function.Consumer

@JvmRecord
data class AshAmount(val amount: Int) : TooltipProvider {
    init {
        if (amount !in 0..MAX)
            throw AssertionError("Ash amount must be between 0 and $MAX")
    }

    override fun addToTooltip(
        context: Item.TooltipContext,
        consumer: Consumer<Component>,
        flag: TooltipFlag,
        components: DataComponentGetter
    ) {
        consumer.accept(Component.translatable("item.netherarchives.necrotic_urn.ash", amount, MAX)
            .withStyle(ChatFormatting.GRAY)
        )
    }
    
    companion object {
        const val MAX = 64

        val CODEC: Codec<AshAmount> = Codec.intRange(0, MAX).xmap(::AshAmount, AshAmount::amount)
        val STREAM_CODEC = ByteBufCodecs.INT.map(::AshAmount, AshAmount::amount)

        fun get(stack: ItemStack) = stack[ModComponents.ASH_AMOUNT]?.amount ?: 0
    }
}