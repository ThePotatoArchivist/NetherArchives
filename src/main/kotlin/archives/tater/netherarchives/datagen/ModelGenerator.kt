package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.block.NetherArchivesBlocks
import archives.tater.netherarchives.item.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.block.Blocks
import net.minecraft.data.client.*
import net.minecraft.data.client.VariantSettings.Rotation
import net.minecraft.util.Identifier

class ModelGenerator(generator: FabricDataOutput) : FabricModelProvider(generator) {
    companion object {
        /**
         * Copied from [BlockStateModelGenerator.registerSoulFire]
         */
        fun registerBlazeFire(blockStateModelGenerator: BlockStateModelGenerator) {
            val list: List<Identifier> = blockStateModelGenerator.getFireFloorModels(NetherArchivesBlocks.BLAZE_FIRE)
            val list2: List<Identifier> = blockStateModelGenerator.getFireSideModels(NetherArchivesBlocks.BLAZE_FIRE)
            blockStateModelGenerator.blockStateCollector.accept(
                MultipartBlockStateSupplier.create(NetherArchivesBlocks.BLAZE_FIRE).with(BlockStateModelGenerator.buildBlockStateVariants(
                    list
                ) { blockStateVariant: BlockStateVariant? -> blockStateVariant })
                    .with(BlockStateModelGenerator.buildBlockStateVariants(
                        list2
                    ) { blockStateVariant: BlockStateVariant? -> blockStateVariant })
                    .with(BlockStateModelGenerator.buildBlockStateVariants(
                        list2
                    ) { blockStateVariant: BlockStateVariant ->
                        blockStateVariant.put(
                            VariantSettings.Y,
                            Rotation.R90
                        )
                    }).with(BlockStateModelGenerator.buildBlockStateVariants(
                        list2
                    ) { blockStateVariant: BlockStateVariant ->
                        blockStateVariant.put(
                            VariantSettings.Y,
                            Rotation.R180
                        )
                    }).with(BlockStateModelGenerator.buildBlockStateVariants(
                        list2
                    ) { blockStateVariant: BlockStateVariant ->
                        blockStateVariant.put(
                            VariantSettings.Y,
                            Rotation.R270
                        )
                    })
            )
        }
    }

    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(NetherArchivesBlocks.MAGNETITE)
        blockStateModelGenerator.registerSimpleCubeAll(NetherArchivesBlocks.SMOLDERING_MAGNETITE)
        registerBlazeFire(blockStateModelGenerator)
    }


    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
//        itemModelGenerator.register(NetherArchives.MAGNETITE_ITEM, Models.GENERATED)
        itemModelGenerator.register(NetherArchivesItems.IRON_SLAG, Models.GENERATED)
    }
}
