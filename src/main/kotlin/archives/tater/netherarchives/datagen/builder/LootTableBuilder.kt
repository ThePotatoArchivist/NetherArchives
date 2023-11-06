package archives.tater.netherarchives.datagen.builder

import net.minecraft.enchantment.Enchantments
import net.minecraft.item.ItemConvertible
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.LootCondition
import net.minecraft.loot.condition.MatchToolLootCondition
import net.minecraft.loot.condition.SurvivesExplosionLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.entry.LeafEntry
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.loot.provider.number.LootNumberProvider
import net.minecraft.loot.provider.number.UniformLootNumberProvider
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.item.EnchantmentPredicate
import net.minecraft.predicate.item.ItemPredicate

fun lootTableBuilder(init: LootTableBuilder.() -> Unit): LootTable.Builder {
    return LootTableBuilder().also(init).output
}

class LootTableBuilder {
    val output = LootTable.Builder()

    fun pool(rolls: LootNumberProvider, init: LootPoolBuilder.() -> Unit) {
        output.pool(LootPoolBuilder(rolls).also(init).output)
    }

    fun pool(rolls: Int, init: LootPoolBuilder.() -> Unit) {
        pool(constant(rolls), init)
    }

    fun pool(rolls: LootNumberProvider, bonusRolls: LootNumberProvider, init: LootPoolBuilder.() -> Unit) {
        output.pool(LootPoolBuilder(rolls, bonusRolls).also(init).output)
    }
}

class LootPoolBuilder(rolls: LootNumberProvider, bonusRolls: LootNumberProvider = constant(0)) {
    val output: LootPool.Builder = LootPool.builder().rolls(rolls).bonusRolls(bonusRolls)

    fun entry(drop: ItemConvertible, init: ItemEntryBuilder.() -> Unit = {}) {
        output.with(ItemEntryBuilder(drop).also(init).output)
    }

    fun condition(condition: Conditions.() -> LootCondition.Builder) {
        output.conditionally(Conditions.condition())
    }
}

class ItemEntryBuilder(drop: ItemConvertible) {
    val output: LeafEntry.Builder<*> = ItemEntry.builder(drop)

    fun count(number: LootNumberProvider) {
        output.apply(SetCountLootFunction.builder(number))
    }

    fun condition(condition: Conditions.() -> LootCondition.Builder) {
        output.conditionally(Conditions.condition())
    }
}

object Conditions {
    val survivesExplosion: LootCondition.Builder = SurvivesExplosionLootCondition.builder()

    fun tool(init: ItemPredicateBuilder.() -> Unit): LootCondition.Builder {
        return MatchToolLootCondition.builder(ItemPredicateBuilder().also(init).output)
    }
}

class ItemPredicateBuilder {
    val output: ItemPredicate.Builder = ItemPredicate.Builder.create()

    val silkTouch: Unit
        get() {
            output.enchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.ANY))
        }
}

fun constant(count: Float): ConstantLootNumberProvider = ConstantLootNumberProvider.create(count)
fun constant(count: Int) = constant(count.toFloat())

fun uniform(min: Float, max: Float): UniformLootNumberProvider = UniformLootNumberProvider.create(min, max)
fun uniform(min: Int, max: Int) = uniform(min.toFloat(), max.toFloat())

operator fun LootCondition.Builder.not(): LootCondition.Builder {
    return this.invert()
}
