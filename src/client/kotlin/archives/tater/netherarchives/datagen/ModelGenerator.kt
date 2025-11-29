package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.block.AdjustableBasaltGeyserBlock
import archives.tater.netherarchives.block.RottenFleshBlock
import archives.tater.netherarchives.mixin.client.BlockStateModelGeneratorAccessor
import archives.tater.netherarchives.registry.NetherArchivesBlocks
import archives.tater.netherarchives.registry.NetherArchivesItems
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.client.data.models.BlockModelGenerators
import net.minecraft.client.data.models.BlockModelGenerators.plainVariant
import net.minecraft.client.data.models.ItemModelGenerators
import net.minecraft.client.data.models.blockstates.MultiPartGenerator
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator
import net.minecraft.client.data.models.blockstates.PropertyDispatch
import net.minecraft.client.data.models.model.*
import net.minecraft.client.renderer.block.model.VariantMutator
import net.minecraft.client.renderer.item.properties.conditional.IsUsingItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks

class ModelGenerator(generator: FabricDataOutput) : FabricModelProvider(generator) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockModelGenerators) {
        blockStateModelGenerator.createTrivialCube(NetherArchivesBlocks.MAGNETITE)
        blockStateModelGenerator.createTrivialBlock(NetherArchivesBlocks.SMOLDERING_MAGNETITE, CUBE_ALL_EMISSIVE_TEXTURED)
        blockStateModelGenerator.registerBlazeFire()
        blockStateModelGenerator.createNonTemplateModelBlock(NetherArchivesBlocks.BLAZE_DUST)
        blockStateModelGenerator.createTrivialCube(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK)
        blockStateModelGenerator.registerExtendedTorch(NetherArchivesBlocks.BLAZE_TORCH, NetherArchivesBlocks.WALL_BLAZE_TORCH)
        blockStateModelGenerator.createGlassBlocks(NetherArchivesBlocks.SPECTREGLASS, NetherArchivesBlocks.SPECTREGLASS_PANE)
        blockStateModelGenerator.createGlassBlocks(NetherArchivesBlocks.SHATTERED_SPECTREGLASS, NetherArchivesBlocks.SHATTERED_SPECTREGLASS_PANE)

        blockStateModelGenerator.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(
                NetherArchivesBlocks.BASALT_GEYSER,
                plainVariant(ModelLocationUtils.getModelLocation(NetherArchivesBlocks.BASALT_GEYSER))
            ).with(ROTATIONS_COLUMN_WITH_FACING)
        )

        blockStateModelGenerator.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(NetherArchivesBlocks.ADJUSTABLE_BASALT_GEYSER).with(
                PropertyDispatch.initial(AdjustableBasaltGeyserBlock.POWERED).generate { powered ->
                    plainVariant(ModelLocationUtils.getModelLocation(NetherArchivesBlocks.ADJUSTABLE_BASALT_GEYSER, if (powered) "_on" else ""))
                }
            ).with(ROTATIONS_COLUMN_WITH_FACING)
        )

        blockStateModelGenerator.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK).with(
            PropertyDispatch.initial(RottenFleshBlock.AGE).generate {
                val suffix = if (it == 0) "" else "_stage$it"
                plainVariant(
                    ModelTemplates.CUBE_ALL.createWithSuffix(
                        NetherArchivesBlocks.ROTTEN_FLESH_BLOCK,
                        suffix,
                        TextureMapping.cube(TextureMapping.getBlockTexture(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK, suffix)),
                        blockStateModelGenerator.modelOutput
                    )
                )
            }
        ))
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerators) {
        itemModelGenerator.generateFlatItem(NetherArchivesItems.IRON_SLAG, ModelTemplates.FLAT_ITEM)
        itemModelGenerator.generateFlatItem(NetherArchivesItems.BLAZE_DUST, ModelTemplates.FLAT_ITEM)
        itemModelGenerator.generateFlatItem(NetherArchivesItems.BLAZE_LANTERN, ModelTemplates.FLAT_ITEM)
        itemModelGenerator.generateFlatItem(NetherArchivesItems.BASALT_SKIS, ModelTemplates.FLAT_ITEM)
        itemModelGenerator.generateFlatItem(NetherArchivesItems.SPECTREGLASS_SHARD, ModelTemplates.FLAT_ITEM)
        itemModelGenerator.itemModelOutput.accept(NetherArchivesItems.SPECTREGLASS_KNIFE, ItemModelUtils.conditional(
            IsUsingItem(),
            ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(NetherArchivesItems.SPECTREGLASS_KNIFE, "_viewing")),
            ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(NetherArchivesItems.SPECTREGLASS_KNIFE))
        ))
        itemModelGenerator.generateSpyglass(NetherArchivesItems.BASALT_OAR)
        itemModelGenerator.generateFlatItem(NetherArchivesItems.BASALT_ROD, ModelTemplates.FLAT_HANDHELD_ROD_ITEM)

        itemModelGenerator.itemModelOutput.accept(NetherArchivesItems.DUMMY_SOUL_FIRE, ItemModelUtils.plainModel(
            ModelTemplates.FLAT_ITEM.create(
                ModelLocationUtils.getModelLocation(NetherArchivesItems.DUMMY_SOUL_FIRE), TextureMapping(
                TextureSlot.LAYER0 to ModelLocationUtils.getModelLocation(Blocks.SOUL_FIRE, "_0")
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
            val floorModels = createFloorFireModels(NetherArchivesBlocks.BLAZE_FIRE)
            val sideModels = createSideFireModels(NetherArchivesBlocks.BLAZE_FIRE)
            blockStateOutput.accept(
                MultiPartGenerator.multiPart(NetherArchivesBlocks.BLAZE_FIRE).apply {
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
