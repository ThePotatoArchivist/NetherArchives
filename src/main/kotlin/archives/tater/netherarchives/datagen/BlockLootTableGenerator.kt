package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.block.NetherArchivesBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider

class BlockLootTableGenerator(output: FabricDataOutput): FabricBlockLootTableProvider(output) {
    override fun generate() {
        addDrop(NetherArchivesBlocks.MAGNETITE, drops(NetherArchivesBlocks.MAGNETITE_ITEM))
    }
}