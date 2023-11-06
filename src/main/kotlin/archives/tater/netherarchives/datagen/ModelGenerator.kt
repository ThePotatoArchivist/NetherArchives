package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.block.FermentingBlock
import archives.tater.netherarchives.block.NetherArchivesBlocks
import archives.tater.netherarchives.item.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.*
import net.minecraft.util.Identifier

class ModelGenerator(generator: FabricDataOutput) : FabricModelProvider(generator) {

    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(NetherArchivesBlocks.MAGNETITE)
        blockStateModelGenerator.registerSimpleCubeAll(NetherArchivesBlocks.SMOLDERING_MAGNETITE)
        blockStateModelGenerator.registerSimpleCubeAll(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH)


        val blockStateVariantMap = BlockStateVariantMap.create(FermentingBlock.AGE).register {
            val suffix = if(it == 0) "" else "_stage$it"
            val textureMap: TextureMap = TextureMap.all(TextureMap.getSubId(NetherArchivesBlocks.ROTTEN_FLESH, suffix))
            val identifier: Identifier =
                Models.CUBE_ALL.upload(NetherArchivesBlocks.ROTTEN_FLESH, suffix, textureMap, blockStateModelGenerator.modelCollector)
            BlockStateVariant.create().put(VariantSettings.MODEL, identifier)
        }
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(NetherArchivesBlocks.ROTTEN_FLESH).coordinate(blockStateVariantMap))
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
        itemModelGenerator.register(NetherArchivesItems.IRON_SLAG, Models.GENERATED)
    }
}
