package archives.tater.netherarchives.datagen.builder

import net.minecraft.advancements.critereon.ItemPredicate
import net.minecraft.core.Holder
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition
import net.minecraft.world.level.storage.loot.predicates.MatchTool
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator

fun lootTable(init: LootTable.Builder.() -> Unit): LootTable.Builder {
    return LootTable.lootTable().apply(init)
}

fun LootTable.Builder.pool(rolls: NumberProvider, init: LootPool.Builder.() -> Unit) {
    pool(LootPool.lootPool().setRolls(rolls).apply(init).build())
}

fun LootTable.Builder.pool(rolls: Int = 1, init: LootPool.Builder.() -> Unit) {
    pool(constant(rolls), init)
}

fun LootPool.Builder.item(drop: ItemLike, init: LootPoolSingletonContainer.Builder<*>.() -> Unit) {
    with(LootItem.lootTableItem(drop).apply(init).build())
}

fun LootPool.Builder.empty(init: LootPoolSingletonContainer.Builder<*>.() -> Unit) {
    with(EmptyLootItem.emptyItem().apply(init).build())
}

fun LootPool.Builder.alternatives(init: AlternativesEntry.Builder.() -> Unit) {
    with(AlternativesEntry.alternatives().apply(init).build())
}

fun LootPool.Builder.conditions(init: Conditions.() -> Unit) {
    Conditions(this).init()
}

fun LootPoolSingletonContainer.Builder<*>.count(count: NumberProvider) {
    apply(SetItemCountFunction.setCount(count))
}

fun LootPoolSingletonContainer.Builder<*>.oreDrops(enchantment: Holder<Enchantment>) {
    apply(ApplyBonusCount.addOreBonusCount(enchantment))
}

fun LootPoolSingletonContainer.Builder<*>.conditions(init: Conditions.() -> Unit) {
    Conditions(this).init()
}

fun AlternativesEntry.Builder.item(drop: ItemLike, init: LootPoolSingletonContainer.Builder<*>.() -> Unit = {}) {
    otherwise(LootItem.lootTableItem(drop).apply(init))
}

class Conditions(private val parentBuilder: ConditionUserBuilder<*>) {
    fun survivesExplosion() {
        parentBuilder.`when`(ExplosionCondition.survivesExplosion())
    }

    fun tool(init: ItemPredicate.Builder.() -> Unit) {
        parentBuilder.`when`(MatchTool.toolMatches(ItemPredicate.Builder.item().apply(init)))
    }
}

fun constant(count: Float): ConstantValue = ConstantValue.exactly(count)
fun constant(count: Int) = constant(count.toFloat())

fun uniform(min: Float, max: Float): UniformGenerator = UniformGenerator.between(min, max)
fun uniform(min: Int, max: Int) = uniform(min.toFloat(), max.toFloat())

operator fun LootItemCondition.Builder.not(): LootItemCondition.Builder {
    return this.invert()
}
