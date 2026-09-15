package archives.tater.netherarchives.datagen.builder

import net.minecraft.advancements.predicates.ItemPredicate
import net.minecraft.core.Holder
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.entries.UniformContainerBase
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition
import net.minecraft.world.level.storage.loot.predicates.MatchTool
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders.exactly

fun lootTable(init: LootTable.Builder.() -> Unit): LootTable.Builder {
    return LootTable.lootTable().apply(init)
}

fun LootTable.Builder.pool(rolls: Holder<ContextIntProvider>, init: LootPool.Builder.() -> Unit) {
    pool(LootPool.lootPool().setRolls(rolls).apply(init).build())
}

fun LootTable.Builder.pool(rolls: Int = 1, init: LootPool.Builder.() -> Unit) {
    pool(exactly(rolls), init)
}

fun LootPool.Builder.item(drop: ItemLike, init: UniformContainerBase.Builder<*>.() -> Unit) {
    add(LootItem.lootTableItem(drop).apply(init).build())
}

fun LootPool.Builder.empty(init: UniformContainerBase.Builder<*>.() -> Unit) {
    add(EmptyLootItem.emptyItem().apply(init).build())
}

fun LootPool.Builder.alternatives(init: AlternativesEntry.Builder.() -> Unit) {
    add(AlternativesEntry.alternatives().apply(init).build())
}

fun LootPool.Builder.conditions(init: Conditions.() -> Unit) {
    Conditions(this).init()
}

fun UniformContainerBase.Builder<*>.count(count: Holder<ContextIntProvider>) {
    apply(SetItemCountFunction.setCount(count))
}

fun UniformContainerBase.Builder<*>.oreDrops(enchantment: Holder<Enchantment>) {
    apply(ApplyBonusCount.addOreBonusCount(enchantment))
}

fun UniformContainerBase.Builder<*>.conditions(init: Conditions.() -> Unit) {
    Conditions(this).init()
}

fun AlternativesEntry.Builder.item(drop: ItemLike, init: UniformContainerBase.Builder<*>.() -> Unit = {}) {
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

operator fun LootItemCondition.Builder.not(): LootItemCondition.Builder {
    return this.invert()
}
