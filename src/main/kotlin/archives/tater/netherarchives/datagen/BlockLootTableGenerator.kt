package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.block.NetherArchivesBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.item.Items

class BlockLootTableGenerator(output: FabricDataOutput) : FabricBlockLootTableProvider(output) {
    override fun generate() {
        addDrop(NetherArchivesBlocks.MAGNETITE, drops(NetherArchivesBlocks.MAGNETITE_ITEM))
        addDrop(NetherArchivesBlocks.SMOLDERING_MAGNETITE, drops(Items.RAW_IRON))
    }
}
