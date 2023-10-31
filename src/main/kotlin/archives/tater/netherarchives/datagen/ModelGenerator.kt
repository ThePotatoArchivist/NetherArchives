package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.block.NetherArchivesBlocks
import archives.tater.netherarchives.item.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.Models

class ModelGenerator(generator: FabricDataOutput) : FabricModelProvider(generator) {

    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(NetherArchivesBlocks.MAGNETITE)
        blockStateModelGenerator.registerSimpleCubeAll(NetherArchivesBlocks.SMOLDERING_MAGNETITE)
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
//        itemModelGenerator.register(NetherArchives.MAGNETITE_ITEM, Models.GENERATED)
        itemModelGenerator.register(NetherArchivesItems.IRON_SLAG, Models.GENERATED)
    }
}
