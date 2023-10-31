package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.block.NetherArchivesBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.item.Items
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.MatchToolLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.loot.provider.number.LootNumberProvider
import net.minecraft.loot.provider.number.UniformLootNumberProvider
import net.minecraft.predicate.item.ItemPredicate

class BlockLootTableGenerator(output: FabricDataOutput) : FabricBlockLootTableProvider(output) {
    override fun generate() {
        addDrop(NetherArchivesBlocks.MAGNETITE, drops(NetherArchivesBlocks.MAGNETITE_ITEM))
    }
}
