package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.block.AdjustableBasaltGeyserBlock
import archives.tater.netherarchives.block.RottenFleshBlock
import archives.tater.netherarchives.mixin.client.BlockStateModelGeneratorAccessor
import archives.tater.netherarchives.registry.NetherArchivesBlocks
import archives.tater.netherarchives.registry.NetherArchivesItems
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.client.data.*
import net.minecraft.client.data.BlockStateModelGenerator.createWeightedVariant
import net.minecraft.client.render.item.property.bool.UsingItemProperty
import net.minecraft.client.render.model.json.ModelVariantOperator

class ModelGenerator(generator: FabricDataOutput) : FabricModelProvider(generator) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(NetherArchivesBlocks.MAGNETITE)
        blockStateModelGenerator.registerSingleton(NetherArchivesBlocks.SMOLDERING_MAGNETITE, CUBE_ALL_EMISSIVE_TEXTURED)
        blockStateModelGenerator.registerBlazeFire()
        blockStateModelGenerator.registerSimpleState(NetherArchivesBlocks.BLAZE_DUST)
        blockStateModelGenerator.registerSimpleCubeAll(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK)
        blockStateModelGenerator.registerExtendedTorch(NetherArchivesBlocks.BLAZE_TORCH, NetherArchivesBlocks.WALL_BLAZE_TORCH)
        blockStateModelGenerator.registerGlassAndPane(NetherArchivesBlocks.SPECTREGLASS, NetherArchivesBlocks.SPECTREGLASS_PANE)
        blockStateModelGenerator.registerGlassAndPane(NetherArchivesBlocks.SHATTERED_SPECTREGLASS, NetherArchivesBlocks.SHATTERED_SPECTREGLASS_PANE)

        blockStateModelGenerator.blockStateCollector.accept(
            VariantsBlockModelDefinitionCreator.of(
                NetherArchivesBlocks.BASALT_GEYSER,
                createWeightedVariant(ModelIds.getBlockModelId(NetherArchivesBlocks.BASALT_GEYSER))
            ).coordinate(UP_DEFAULT_ROTATION_OPERATIONS)
        )

        blockStateModelGenerator.blockStateCollector.accept(
            VariantsBlockModelDefinitionCreator.of(NetherArchivesBlocks.ADJUSTABLE_BASALT_GEYSER).with(
                BlockStateVariantMap.models(AdjustableBasaltGeyserBlock.POWERED).generate { powered ->
                    createWeightedVariant(ModelIds.getBlockSubModelId(NetherArchivesBlocks.ADJUSTABLE_BASALT_GEYSER, if (powered) "_on" else ""))
                }
            ).coordinate(UP_DEFAULT_ROTATION_OPERATIONS)
        )

        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK).with(
            BlockStateVariantMap.models(RottenFleshBlock.AGE).generate {
                val suffix = if (it == 0) "" else "_stage$it"
                createWeightedVariant(
                    Models.CUBE_ALL.upload(
                        NetherArchivesBlocks.ROTTEN_FLESH_BLOCK,
                        suffix,
                        TextureMap.all(TextureMap.getSubId(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK, suffix)),
                        blockStateModelGenerator.modelCollector
                    )
                )
            }
        ))
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
        itemModelGenerator.register(NetherArchivesItems.IRON_SLAG, Models.GENERATED)
        itemModelGenerator.register(NetherArchivesItems.BLAZE_DUST, Models.GENERATED)
        itemModelGenerator.register(NetherArchivesItems.BLAZE_LANTERN, Models.GENERATED)
        itemModelGenerator.register(NetherArchivesItems.BASALT_SKIS, Models.GENERATED)
        itemModelGenerator.register(NetherArchivesItems.SPECTREGLASS_SHARD, Models.GENERATED)
        itemModelGenerator.output.accept(NetherArchivesItems.SPECTREGLASS_KNIFE, ItemModels.condition(UsingItemProperty(),
            ItemModels.basic(ModelIds.getItemSubModelId(NetherArchivesItems.SPECTREGLASS_KNIFE, "_viewing")),
            ItemModels.basic(ModelIds.getItemModelId(NetherArchivesItems.SPECTREGLASS_KNIFE))
        ))
        itemModelGenerator.registerWithInHandModel(NetherArchivesItems.BASALT_OAR)
        itemModelGenerator.register(NetherArchivesItems.BASALT_ROD, Models.HANDHELD_ROD)

        itemModelGenerator.output.accept(NetherArchivesItems.DUMMY_SOUL_FIRE, ItemModels.basic(
            Models.GENERATED.upload(ModelIds.getItemModelId(NetherArchivesItems.DUMMY_SOUL_FIRE), TextureMap(
                TextureKey.LAYER0 to ModelIds.getBlockSubModelId(Blocks.SOUL_FIRE, "_0")
            ), itemModelGenerator.modelCollector)
        ))

    }

    companion object {

        val NORTH_DEFAULT_ROTATION_OPERATIONS: BlockStateVariantMap<ModelVariantOperator> = BlockStateModelGeneratorAccessor.getNORTH_DEFAULT_ROTATION_OPERATIONS()
        val UP_DEFAULT_ROTATION_OPERATIONS: BlockStateVariantMap<ModelVariantOperator> = BlockStateModelGeneratorAccessor.getUP_DEFAULT_ROTATION_OPERATIONS()
        val EAST_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS: BlockStateVariantMap<ModelVariantOperator> = BlockStateModelGeneratorAccessor.getEAST_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS()
        val SOUTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS: BlockStateVariantMap<ModelVariantOperator> = BlockStateModelGeneratorAccessor.getSOUTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS()
        val NORTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS: BlockStateVariantMap<ModelVariantOperator> = BlockStateModelGeneratorAccessor.getNORTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS()

        val ALL_EMISSIVE: TextureKey = TextureKey.of("all_emissive")
        val CUBE_ALL_EMISSIVE = Model(TextureKey.ALL, ALL_EMISSIVE, parent = NetherArchives.id("block/cube_all_emissive"))
        val CUBE_ALL_EMISSIVE_TEXTURED = texturedModelFactory(CUBE_ALL_EMISSIVE) { TextureMap(
            TextureKey.ALL to TextureMap.getId(it),
            ALL_EMISSIVE to TextureMap.getSubId(it, "_emissive")
        ) }

        /**
         * Copied from [BlockStateModelGenerator.registerSoulFire]
         */
        fun BlockStateModelGenerator.registerBlazeFire() {
            val floorModels = getFireFloorModels(NetherArchivesBlocks.BLAZE_FIRE)
            val sideModels = getFireSideModels(NetherArchivesBlocks.BLAZE_FIRE)
            blockStateCollector.accept(
                MultipartBlockModelDefinitionCreator.create(NetherArchivesBlocks.BLAZE_FIRE).apply {
                    with(floorModels)
                    with(sideModels)
                    with(sideModels.apply(BlockStateModelGenerator.ROTATE_Y_90))
                    with(sideModels.apply(BlockStateModelGenerator.ROTATE_Y_180))
                    with(sideModels.apply(BlockStateModelGenerator.ROTATE_Y_270))
                }
            )
        }

        val TEMPLATE_EXTENDED_TORCH_MODEL = Model(TextureKey.TORCH, parent = NetherArchives.id("block/template_extended_torch"))
        val TEMPLATE_EXTENDED_TORCH_WALL_MODEL = Model(TextureKey.TORCH, parent = NetherArchives.id("block/template_extended_torch_wall"))

        fun BlockStateModelGenerator.registerExtendedTorch(torch: Block, wallTorch: Block) {
            val textureMap = TextureMap.torch(torch)
            blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(torch,
                createWeightedVariant(TEMPLATE_EXTENDED_TORCH_MODEL.upload(torch, textureMap, modelCollector))
            ))
            blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(wallTorch,
                createWeightedVariant(TEMPLATE_EXTENDED_TORCH_WALL_MODEL.upload(
                    wallTorch,
                    textureMap,
                    modelCollector
                ))
            ).coordinate(EAST_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS))
            registerItemModel(torch)
        }
    }
}
