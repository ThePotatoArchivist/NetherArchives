package archives.tater.netherarchives.datagen.builder

import net.minecraft.enchantment.Enchantment
import net.minecraft.item.ItemConvertible
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.LootCondition
import net.minecraft.loot.condition.LootConditionConsumingBuilder
import net.minecraft.loot.condition.MatchToolLootCondition
import net.minecraft.loot.condition.SurvivesExplosionLootCondition
import net.minecraft.loot.entry.AlternativeEntry
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.entry.LeafEntry
import net.minecraft.loot.function.ApplyBonusLootFunction
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.loot.provider.number.LootNumberProvider
import net.minecraft.loot.provider.number.UniformLootNumberProvider
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.registry.entry.RegistryEntry

fun lootTable(init: LootTable.Builder.() -> Unit): LootTable.Builder {
    return LootTable.builder().apply(init)
}

fun LootTable.Builder.pool(rolls: LootNumberProvider, init: LootPool.Builder.() -> Unit) {
    pool(LootPool.builder().rolls(rolls).apply(init).build())
}

fun LootTable.Builder.pool(rolls: Int = 1, init: LootPool.Builder.() -> Unit) {
    pool(constant(rolls), init)
}

fun LootPool.Builder.item(drop: ItemConvertible, init: LeafEntry.Builder<*>.() -> Unit) {
    with(ItemEntry.builder(drop).apply(init).build())
}

fun LootPool.Builder.alternatives(init: AlternativeEntry.Builder.() -> Unit) {
    with(AlternativeEntry.builder().apply(init).build())
}

fun LootPool.Builder.conditions(init: Conditions.() -> Unit) {
    Conditions(this).init()
}

fun LeafEntry.Builder<*>.count(count: LootNumberProvider) {
    apply(SetCountLootFunction.builder(count))
}

fun LeafEntry.Builder<*>.oreDrops(enchantment: RegistryEntry<Enchantment>) {
    apply(ApplyBonusLootFunction.oreDrops(enchantment))
}

fun LeafEntry.Builder<*>.conditions(init: Conditions.() -> Unit) {
    Conditions(this).init()
}

fun AlternativeEntry.Builder.item(drop: ItemConvertible, init: LeafEntry.Builder<*>.() -> Unit = {}) {
    alternatively(ItemEntry.builder(drop).apply(init))
}

class Conditions(private val parentBuilder: LootConditionConsumingBuilder<*>) {
    fun survivesExplosion() {
        parentBuilder.conditionally(SurvivesExplosionLootCondition.builder())
    }

    fun tool(init: ItemPredicate.Builder.() -> Unit) {
        parentBuilder.conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().apply(init)))
    }
}

fun constant(count: Float): ConstantLootNumberProvider = ConstantLootNumberProvider.create(count)
fun constant(count: Int) = constant(count.toFloat())

fun uniform(min: Float, max: Float): UniformLootNumberProvider = UniformLootNumberProvider.create(min, max)
fun uniform(min: Int, max: Int) = uniform(min.toFloat(), max.toFloat())

operator fun LootCondition.Builder.not(): LootCondition.Builder {
    return this.invert()
}
