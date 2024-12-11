package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.block.NetherArchivesBlocks
import archives.tater.netherarchives.block.RottenFleshBlock
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
                MultipartBlockStateSupplier.create(NetherArchivesBlocks.BLAZE_FIRE)
                    .with(BlockStateModelGenerator.buildBlockStateVariants(
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
        blockStateModelGenerator.registerSimpleState(NetherArchivesBlocks.BLAZE_DUST)
        blockStateModelGenerator.registerSimpleCubeAll(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK)
        blockStateModelGenerator.registerTorch(NetherArchivesBlocks.BLAZE_TORCH, NetherArchivesBlocks.WALL_BLAZE_TORCH)

        val blockStateVariantMap = BlockStateVariantMap.create(RottenFleshBlock.AGE).register {
            val suffix = if (it == 0) "" else "_stage$it"
            val textureMap: TextureMap =
                TextureMap.all(TextureMap.getSubId(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK, suffix))
            val identifier: Identifier =
                Models.CUBE_ALL.upload(
                    NetherArchivesBlocks.ROTTEN_FLESH_BLOCK,
                    suffix,
                    textureMap,
                    blockStateModelGenerator.modelCollector
                )
            BlockStateVariant.create().put(VariantSettings.MODEL, identifier)
        }
        blockStateModelGenerator.blockStateCollector.accept(
            VariantsBlockStateSupplier.create(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK).coordinate(blockStateVariantMap)
        )

    }


    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
        itemModelGenerator.register(NetherArchivesItems.IRON_SLAG, Models.GENERATED)
        itemModelGenerator.register(NetherArchivesItems.BLAZE_DUST, Models.GENERATED)
        itemModelGenerator.register(NetherArchivesItems.BASALT_SKIS, Models.GENERATED)
        itemModelGenerator.register(NetherArchivesItems.NETHERITE_SKIS, Models.GENERATED)
        itemModelGenerator.register(NetherArchivesItems.BASALT_OAR, Models.HANDHELD_ROD)

        Models.GENERATED.upload(ModelIds.getItemModelId(NetherArchivesItems.DUMMY_SOUL_FIRE), TextureMap().apply {
            put(TextureKey.LAYER0, ModelIds.getBlockSubModelId(Blocks.SOUL_FIRE, "_0"))
        }, itemModelGenerator.writer)

        Models.TEMPLATE_LANTERN.upload(
            ModelIds.getItemModelId(NetherArchivesItems.BLAZE_LANTERN),
            TextureMap().put(TextureKey.LANTERN, TextureMap.getId(NetherArchivesItems.BLAZE_LANTERN)),
            itemModelGenerator.writer
        )
    }
}
