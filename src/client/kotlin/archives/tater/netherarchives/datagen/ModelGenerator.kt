package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.block.AdjustableBasaltGeyserBlock
import archives.tater.netherarchives.block.RottenFleshBlock
import archives.tater.netherarchives.registry.NetherArchivesBlocks
import archives.tater.netherarchives.registry.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.blockstates.*
import net.minecraft.data.models.blockstates.VariantProperties.Rotation
import net.minecraft.data.models.model.ModelLocationUtils
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks

class ModelGenerator(generator: FabricDataOutput) : FabricModelProvider(generator) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockModelGenerators) {
        blockStateModelGenerator.createTrivialCube(NetherArchivesBlocks.MAGNETITE)
        blockStateModelGenerator.createTrivialCube(NetherArchivesBlocks.SMOLDERING_MAGNETITE)
        registerBlazeFire(blockStateModelGenerator)
        blockStateModelGenerator.createNonTemplateModelBlock(NetherArchivesBlocks.BLAZE_DUST)
        blockStateModelGenerator.createTrivialCube(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK)
        blockStateModelGenerator.createNormalTorch(NetherArchivesBlocks.BLAZE_TORCH, NetherArchivesBlocks.WALL_BLAZE_TORCH)
        blockStateModelGenerator.createGlassBlocks(NetherArchivesBlocks.SPECTREGLASS, NetherArchivesBlocks.SPECTREGLASS_PANE)
        blockStateModelGenerator.createGlassBlocks(NetherArchivesBlocks.SHATTERED_SPECTREGLASS, NetherArchivesBlocks.SHATTERED_SPECTREGLASS_PANE)

        blockStateModelGenerator.acceptVariants(NetherArchivesBlocks.BASALT_GEYSER,
            CUBE_BOTTOM_TOP_PARTICLE_MODEL.create(
                NetherArchivesBlocks.BASALT_GEYSER,
                cubeBottomTopParticle(
                    top = NetherArchivesBlocks.BASALT_GEYSER,
                    bottom = Blocks.BASALT,
                    bottomSuffix = "_top",
                ),
                blockStateModelGenerator.modelOutput
            )
        ) {
            with(blockStateModelGenerator.createColumnWithFacing())
        }

        blockStateModelGenerator.acceptVariants(NetherArchivesBlocks.ADJUSTABLE_BASALT_GEYSER) {
            with(PropertyDispatch.property(AdjustableBasaltGeyserBlock.POWERED).generate { powered ->
                val suffix = if (powered) "_on" else ""
                BlockStateVariant(
                    model = CUBE_BOTTOM_TOP_PARTICLE_MODEL.create(
                        ModelLocationUtils.getModelLocation(NetherArchivesBlocks.ADJUSTABLE_BASALT_GEYSER, suffix),
                        cubeBottomTopParticle(
                            top = NetherArchivesBlocks.ADJUSTABLE_BASALT_GEYSER,
                            bottom = NetherArchivesBlocks.ADJUSTABLE_BASALT_GEYSER,
                            suffix = suffix,
                            topSuffixed = false,
                        ),
                        blockStateModelGenerator.modelOutput
                    )
                )
            })
            with(blockStateModelGenerator.createColumnWithFacing())
        }

        blockStateModelGenerator.acceptVariants(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK) {
            with(PropertyDispatch.property(RottenFleshBlock.AGE).generate {
                val suffix = if (it == 0) "" else "_stage$it"
                BlockStateVariant(
                    model = ModelTemplates.CUBE_ALL.createWithSuffix(
                        NetherArchivesBlocks.ROTTEN_FLESH_BLOCK,
                        suffix,
                        TextureMapping.cube(TextureMapping.getBlockTexture(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK, suffix)),
                        blockStateModelGenerator.modelOutput
                    )
                )
            })
        }
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerators) {
        itemModelGenerator.generateFlatItem(NetherArchivesItems.IRON_SLAG, ModelTemplates.FLAT_ITEM)
        itemModelGenerator.generateFlatItem(NetherArchivesItems.BLAZE_DUST, ModelTemplates.FLAT_ITEM)
        itemModelGenerator.generateFlatItem(NetherArchivesItems.BLAZE_LANTERN, ModelTemplates.FLAT_ITEM)
        itemModelGenerator.generateFlatItem(NetherArchivesItems.BASALT_SKIS, ModelTemplates.FLAT_ITEM)
        itemModelGenerator.generateFlatItem(NetherArchivesItems.SPECTREGLASS_SHARD, ModelTemplates.FLAT_ITEM)
        itemModelGenerator.generateFlatItem(NetherArchivesItems.BASALT_OAR, ModelTemplates.FLAT_ITEM)
        itemModelGenerator.generateFlatItem(NetherArchivesItems.BASALT_ROD, ModelTemplates.FLAT_HANDHELD_ROD_ITEM)

        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(NetherArchivesItems.DUMMY_SOUL_FIRE), TextureMap().apply {
            put(TextureSlot.LAYER0, ModelLocationUtils.getModelLocation(Blocks.SOUL_FIRE, "_0"))
        }, itemModelGenerator.output)
    }

    companion object {
        private val CUBE_BOTTOM_TOP_PARTICLE_MODEL = Model(
            TextureSlot.TOP,
            TextureSlot.SIDE,
            TextureSlot.BOTTOM,
            TextureSlot.PARTICLE,
            parent = ResourceLocation.withDefaultNamespace("block/cube_bottom_top")
        )

        private fun cubeBottomTopParticle(top: Block, bottom: Block, side: Block = top, suffix: String = "", bottomSuffix: String = "_bottom", topSuffixed: Boolean = true) = TextureMap(
            TextureSlot.TOP to ModelLocationUtils.getModelLocation(top, if (topSuffixed) "_top$suffix" else "_top"),
            TextureSlot.SIDE to ModelLocationUtils.getModelLocation(side, "_side$suffix"),
            TextureSlot.BOTTOM to ModelLocationUtils.getModelLocation(bottom, "$bottomSuffix$suffix"),
            TextureSlot.PARTICLE to ModelLocationUtils.getModelLocation(top, "_top$suffix"),
        )

        private inline fun BlockModelGenerators.acceptVariants(block: Block, vararg variants: Variant, init: MultiVariantGenerator.() -> Unit = {}) {
            blockStateOutput.accept(when (variants.size) {
                0 -> MultiVariantGenerator.multiVariant(block)
                1 -> MultiVariantGenerator.multiVariant(block, variants.first())
                else -> MultiVariantGenerator.multiVariant(block, *variants)
            }.apply(init))
        }

        private inline fun BlockModelGenerators.acceptVariants(block: Block, model: ResourceLocation, init: MultiVariantGenerator.() -> Unit = {}) {
            acceptVariants(block, BlockStateVariant(model = model), init = init)
        }

        private inline fun buildBlockStateVariants(modelIds: List<ResourceLocation>, crossinline processor: Variant.() -> Unit = {}): MutableList<Variant> =
            BlockModelGenerators.wrapModels(modelIds) { it.apply(processor) }

        /**
         * Copied from [BlockModelGenerators.createSoulFire]
         */
        private fun registerBlazeFire(blockStateModelGenerator: BlockModelGenerators) {
            val floorModels: List<ResourceLocation> = blockStateModelGenerator.createFloorFireModels(NetherArchivesBlocks.BLAZE_FIRE)
            val sideModels: List<ResourceLocation> = blockStateModelGenerator.createSideFireModels(NetherArchivesBlocks.BLAZE_FIRE)
            blockStateModelGenerator.blockStateOutput.accept(
                MultiPartGenerator.multiPart(NetherArchivesBlocks.BLAZE_FIRE).apply {
                    with(buildBlockStateVariants(floorModels))
                    with(buildBlockStateVariants(sideModels))
                    with(buildBlockStateVariants(sideModels) { with(VariantProperties.Y_ROT, Rotation.R90) })
                    with(buildBlockStateVariants(sideModels) { with(VariantProperties.Y_ROT, Rotation.R180) })
                    with(buildBlockStateVariants(sideModels) { with(VariantProperties.Y_ROT, Rotation.R270) })
                }
            )
        }
    }
}
