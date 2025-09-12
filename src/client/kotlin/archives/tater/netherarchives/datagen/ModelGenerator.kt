package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.block.AdjustableBasaltGeyserBlock
import archives.tater.netherarchives.block.RottenFleshBlock
import archives.tater.netherarchives.registry.NetherArchivesBlocks
import archives.tater.netherarchives.registry.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.data.client.*
import net.minecraft.data.client.VariantSettings.Rotation
import net.minecraft.util.Identifier

class ModelGenerator(generator: FabricDataOutput) : FabricModelProvider(generator) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(NetherArchivesBlocks.MAGNETITE)
        blockStateModelGenerator.registerSimpleCubeAll(NetherArchivesBlocks.SMOLDERING_MAGNETITE)
        registerBlazeFire(blockStateModelGenerator)
        blockStateModelGenerator.registerSimpleState(NetherArchivesBlocks.BLAZE_DUST)
        blockStateModelGenerator.registerSimpleCubeAll(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK)
        blockStateModelGenerator.registerTorch(NetherArchivesBlocks.BLAZE_TORCH, NetherArchivesBlocks.WALL_BLAZE_TORCH)
        blockStateModelGenerator.registerGlassPane(NetherArchivesBlocks.SPECTREGLASS, NetherArchivesBlocks.SPECTREGLASS_PANE)
        blockStateModelGenerator.registerGlassPane(NetherArchivesBlocks.SHATTERED_SPECTREGLASS, NetherArchivesBlocks.SHATTERED_SPECTREGLASS_PANE)

        blockStateModelGenerator.acceptVariants(NetherArchivesBlocks.BASALT_GEYSER,
            CUBE_BOTTOM_TOP_PARTICLE_MODEL.upload(
                NetherArchivesBlocks.BASALT_GEYSER,
                cubeBottomTopParticle(
                    top = NetherArchivesBlocks.BASALT_GEYSER,
                    bottom = Blocks.BASALT,
                    bottomSuffix = "_top",
                ),
                blockStateModelGenerator.modelCollector
            )
        ) {
            coordinate(blockStateModelGenerator.createUpDefaultFacingVariantMap())
        }

        blockStateModelGenerator.acceptVariants(NetherArchivesBlocks.ADJUSTABLE_BASALT_GEYSER) {
            coordinate(BlockStateVariantMap.create(AdjustableBasaltGeyserBlock.POWERED).register { powered ->
                val suffix = if (powered) "_on" else ""
                BlockStateVariant(
                    model = CUBE_BOTTOM_TOP_PARTICLE_MODEL.upload(
                        ModelIds.getBlockSubModelId(NetherArchivesBlocks.ADJUSTABLE_BASALT_GEYSER, suffix),
                        cubeBottomTopParticle(
                            top = NetherArchivesBlocks.ADJUSTABLE_BASALT_GEYSER,
                            bottom = NetherArchivesBlocks.ADJUSTABLE_BASALT_GEYSER,
                            suffix = suffix,
                            topSuffixed = false,
                        ),
                        blockStateModelGenerator.modelCollector
                    )
                )
            })
            coordinate(blockStateModelGenerator.createUpDefaultFacingVariantMap())
        }

        blockStateModelGenerator.acceptVariants(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK) {
            coordinate(BlockStateVariantMap.create(RottenFleshBlock.AGE).register {
                val suffix = if (it == 0) "" else "_stage$it"
                BlockStateVariant(
                    model = Models.CUBE_ALL.upload(
                        NetherArchivesBlocks.ROTTEN_FLESH_BLOCK,
                        suffix,
                        TextureMap.all(TextureMap.getSubId(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK, suffix)),
                        blockStateModelGenerator.modelCollector
                    )
                )
            })
        }
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
        itemModelGenerator.register(NetherArchivesItems.IRON_SLAG, Models.GENERATED)
        itemModelGenerator.register(NetherArchivesItems.BLAZE_DUST, Models.GENERATED)
        itemModelGenerator.register(NetherArchivesItems.BLAZE_LANTERN, Models.GENERATED)
        itemModelGenerator.register(NetherArchivesItems.BASALT_SKIS, Models.GENERATED)
        itemModelGenerator.register(NetherArchivesItems.SPECTREGLASS_SHARD, Models.GENERATED)
        itemModelGenerator.register(NetherArchivesItems.BASALT_OAR, Models.GENERATED)
        itemModelGenerator.register(NetherArchivesItems.BASALT_ROD, Models.HANDHELD_ROD)

        Models.GENERATED.upload(ModelIds.getItemModelId(NetherArchivesItems.DUMMY_SOUL_FIRE), TextureMap().apply {
            put(TextureKey.LAYER0, ModelIds.getBlockSubModelId(Blocks.SOUL_FIRE, "_0"))
        }, itemModelGenerator.writer)
    }

    companion object {
        val CUBE_BOTTOM_TOP_PARTICLE_MODEL = Model(
            TextureKey.TOP,
            TextureKey.SIDE,
            TextureKey.BOTTOM,
            TextureKey.PARTICLE,
            parent = Identifier.ofVanilla("block/cube_bottom_top")
        )

        fun cubeBottomTopParticle(top: Block, bottom: Block, side: Block = top, suffix: String = "", bottomSuffix: String = "_bottom", topSuffixed: Boolean = true) = TextureMap(
            TextureKey.TOP to ModelIds.getBlockSubModelId(top, if (topSuffixed) "_top$suffix" else "_top"),
            TextureKey.SIDE to ModelIds.getBlockSubModelId(side, "_side$suffix"),
            TextureKey.BOTTOM to ModelIds.getBlockSubModelId(bottom, "$bottomSuffix$suffix"),
            TextureKey.PARTICLE to ModelIds.getBlockSubModelId(top, "_top$suffix"),
        )

        inline fun BlockStateModelGenerator.acceptVariants(block: Block, vararg variants: BlockStateVariant, init: VariantsBlockStateSupplier.() -> Unit = {}) {
            blockStateCollector.accept(when (variants.size) {
                0 -> VariantsBlockStateSupplier.create(block)
                1 -> VariantsBlockStateSupplier.create(block, variants.first())
                else -> VariantsBlockStateSupplier.create(block, *variants)
            }.apply(init))
        }

        inline fun BlockStateModelGenerator.acceptVariants(block: Block, model: Identifier, init: VariantsBlockStateSupplier.() -> Unit = {}) {
            acceptVariants(block, BlockStateVariant(model = model), init = init)
        }

        inline fun buildBlockStateVariants(modelIds: List<Identifier>, crossinline processor: BlockStateVariant.() -> Unit = {}): MutableList<BlockStateVariant> =
            BlockStateModelGenerator.buildBlockStateVariants(modelIds) { it.apply(processor) }

        /**
         * Copied from [BlockStateModelGenerator.registerSoulFire]
         */
        fun registerBlazeFire(blockStateModelGenerator: BlockStateModelGenerator) {
            val floorModels: List<Identifier> = blockStateModelGenerator.getFireFloorModels(NetherArchivesBlocks.BLAZE_FIRE)
            val sideModels: List<Identifier> = blockStateModelGenerator.getFireSideModels(NetherArchivesBlocks.BLAZE_FIRE)
            blockStateModelGenerator.blockStateCollector.accept(
                MultipartBlockStateSupplier.create(NetherArchivesBlocks.BLAZE_FIRE).apply {
                    with(buildBlockStateVariants(floorModels))
                    with(buildBlockStateVariants(sideModels))
                    with(buildBlockStateVariants(sideModels) { put(VariantSettings.Y, Rotation.R90) })
                    with(buildBlockStateVariants(sideModels) { put(VariantSettings.Y, Rotation.R180) })
                    with(buildBlockStateVariants(sideModels) { put(VariantSettings.Y, Rotation.R270) })
                }
            )
        }
    }
}
