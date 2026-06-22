package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.block.AdjustableBasaltGeyserBlock
import archives.tater.netherarchives.block.RottenFleshBlock
import archives.tater.netherarchives.mixin.client.BlockStateModelGeneratorAccessor
import archives.tater.netherarchives.registry.ModBlocks
import archives.tater.netherarchives.registry.ModItems
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.minecraft.client.data.models.BlockModelGenerators
import net.minecraft.client.data.models.BlockModelGenerators.plainVariant
import net.minecraft.client.data.models.ItemModelGenerators
import net.minecraft.client.data.models.blockstates.MultiPartGenerator
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator
import net.minecraft.client.data.models.blockstates.PropertyDispatch
import net.minecraft.client.data.models.model.*
import net.minecraft.client.renderer.block.dispatch.VariantMutator
import net.minecraft.client.renderer.item.properties.conditional.IsUsingItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks

class ModelGenerator(generator: FabricPackOutput) : FabricModelProvider(generator) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockModelGenerators) {
        blockStateModelGenerator.createTrivialCube(ModBlocks.MAGNETITE)
        blockStateModelGenerator.createTrivialBlock(ModBlocks.SMOLDERING_MAGNETITE, CUBE_ALL_EMISSIVE_TEXTURED)
        blockStateModelGenerator.registerBlazeFire()
        blockStateModelGenerator.createNonTemplateModelBlock(ModBlocks.BLAZE_DUST)
        blockStateModelGenerator.createTrivialCube(ModBlocks.FERMENTED_ROTTEN_FLESH_BLOCK)
        blockStateModelGenerator.registerExtendedTorch(ModBlocks.BLAZE_TORCH, ModBlocks.WALL_BLAZE_TORCH)
        blockStateModelGenerator.createGlassBlocks(ModBlocks.SPECTREGLASS, ModBlocks.SPECTREGLASS_PANE)
        blockStateModelGenerator.createGlassBlocks(ModBlocks.SHATTERED_SPECTREGLASS, ModBlocks.SHATTERED_SPECTREGLASS_PANE)

        blockStateModelGenerator.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(
                ModBlocks.BASALT_GEYSER,
                plainVariant(ModelLocationUtils.getModelLocation(ModBlocks.BASALT_GEYSER))
            ).with(ROTATIONS_COLUMN_WITH_FACING)
        )

        blockStateModelGenerator.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(ModBlocks.ADJUSTABLE_BASALT_GEYSER).with(
                PropertyDispatch.initial(AdjustableBasaltGeyserBlock.POWERED).generate { powered ->
                    plainVariant(ModelLocationUtils.getModelLocation(ModBlocks.ADJUSTABLE_BASALT_GEYSER, if (powered) "_on" else ""))
                }
            ).with(ROTATIONS_COLUMN_WITH_FACING)
        )

        blockStateModelGenerator.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(ModBlocks.ROTTEN_FLESH_BLOCK).with(
            PropertyDispatch.initial(RottenFleshBlock.AGE).generate {
                val suffix = if (it == 0) "" else "_stage$it"
                plainVariant(
                    ModelTemplates.CUBE_ALL.createWithSuffix(
                        ModBlocks.ROTTEN_FLESH_BLOCK,
                        suffix,
                        TextureMapping.cube(TextureMapping.getBlockTexture(ModBlocks.ROTTEN_FLESH_BLOCK, suffix)),
                        blockStateModelGenerator.modelOutput
                    )
                )
            }
        ))
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerators) {
        itemModelGenerator.generateFlatItem(ModItems.IRON_SLAG, ModelTemplates.FLAT_ITEM)
        itemModelGenerator.generateFlatItem(ModItems.BLAZE_DUST, ModelTemplates.FLAT_ITEM)
        itemModelGenerator.generateFlatItem(ModItems.BLAZE_LANTERN, ModelTemplates.FLAT_ITEM)
        itemModelGenerator.generateFlatItem(ModItems.BASALT_SKIS, ModelTemplates.FLAT_ITEM)
        itemModelGenerator.generateFlatItem(ModItems.SPECTREGLASS_SHARD, ModelTemplates.FLAT_ITEM)
        itemModelGenerator.itemModelOutput.accept(ModItems.SPECTREGLASS_KNIFE, ItemModelUtils.conditional(
            IsUsingItem(),
            ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(ModItems.SPECTREGLASS_KNIFE, "_viewing")),
            ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(ModItems.SPECTREGLASS_KNIFE))
        ))
        itemModelGenerator.generateSpyglass(ModItems.BASALT_OAR)
        itemModelGenerator.generateFlatItem(ModItems.BASALT_ROD, ModelTemplates.FLAT_HANDHELD_ROD_ITEM)

        itemModelGenerator.itemModelOutput.accept(ModItems.DUMMY_SOUL_FIRE, ItemModelUtils.plainModel(
            ModelTemplates.FLAT_ITEM.create(
                ModelLocationUtils.getModelLocation(ModItems.DUMMY_SOUL_FIRE), TextureMapping(
                TextureSlot.LAYER0 to TextureMapping.getBlockTexture(Blocks.SOUL_FIRE, "_0")
            ), itemModelGenerator.modelOutput
            )
        ))

    }

    companion object {

        val ROTATION_FACING: PropertyDispatch<VariantMutator> = BlockStateModelGeneratorAccessor.getROTATION_FACING()
        val ROTATIONS_COLUMN_WITH_FACING: PropertyDispatch<VariantMutator> = BlockStateModelGeneratorAccessor.getROTATIONS_COLUMN_WITH_FACING()
        val ROTATION_TORCH: PropertyDispatch<VariantMutator> = BlockStateModelGeneratorAccessor.getROTATION_TORCH()
        val ROTATION_HORIZONTAL_FACING_ALT: PropertyDispatch<VariantMutator> = BlockStateModelGeneratorAccessor.getROTATION_HORIZONTAL_FACING_ALT()
        val ROTATION_HORIZONTAL_FACING: PropertyDispatch<VariantMutator> = BlockStateModelGeneratorAccessor.getROTATION_HORIZONTAL_FACING()

        val ALL_EMISSIVE: TextureSlot = TextureSlot.create("all_emissive")
        val CUBE_ALL_EMISSIVE = ModelTemplate(TextureSlot.ALL, ALL_EMISSIVE, parent = NetherArchives.id("block/cube_all_emissive"))
        val CUBE_ALL_EMISSIVE_TEXTURED = texturedModelFactory(CUBE_ALL_EMISSIVE) { TextureMapping(
            TextureSlot.ALL to TextureMapping.getBlockTexture(it),
            ALL_EMISSIVE to TextureMapping.getBlockTexture(it, "_emissive")
        ) }

        /**
         * Copied from [BlockModelGenerators.createSoulFire]
         */
        fun BlockModelGenerators.registerBlazeFire() {
            val floorModels = createFloorFireModels(ModBlocks.BLAZE_FIRE)
            val sideModels = createSideFireModels(ModBlocks.BLAZE_FIRE)
            blockStateOutput.accept(
                MultiPartGenerator.multiPart(ModBlocks.BLAZE_FIRE).apply {
                    with(floorModels)
                    with(sideModels)
                    with(sideModels.with(BlockModelGenerators.Y_ROT_90))
                    with(sideModels.with(BlockModelGenerators.Y_ROT_180))
                    with(sideModels.with(BlockModelGenerators.Y_ROT_270))
                }
            )
        }

        val TEMPLATE_EXTENDED_TORCH_MODEL = ModelTemplate(TextureSlot.TORCH, parent = NetherArchives.id("block/template_extended_torch"))
        val TEMPLATE_EXTENDED_TORCH_WALL_MODEL = ModelTemplate(TextureSlot.TORCH, parent = NetherArchives.id("block/template_extended_torch_wall"))

        fun BlockModelGenerators.registerExtendedTorch(torch: Block, wallTorch: Block) {
            val textureMap = TextureMapping.torch(torch)
            blockStateOutput.accept(
                BlockModelGenerators.createSimpleBlock(torch,
                plainVariant(TEMPLATE_EXTENDED_TORCH_MODEL.create(torch, textureMap, modelOutput))
            ))
            blockStateOutput.accept(
                MultiVariantGenerator.dispatch(wallTorch,
                plainVariant(TEMPLATE_EXTENDED_TORCH_WALL_MODEL.create(
                    wallTorch,
                    textureMap,
                    modelOutput
                ))
            ).with(ROTATION_TORCH))
            registerSimpleFlatItemModel(torch)
        }
    }
}
