package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.ModBlocks
import archives.tater.netherarchives.registry.ModTags
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags
import net.minecraft.core.HolderLookup
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Blocks
import java.util.concurrent.CompletableFuture

class BlockTagGenerator(output: FabricPackOutput, completableFuture: CompletableFuture<HolderLookup.Provider>) :
    FabricTagsProvider.BlockTagsProvider(output, completableFuture) {

    override fun addTags(arg: HolderLookup.Provider) {
        valueLookupBuilder(BlockTags.MINEABLE_WITH_SHOVEL).add(
            ModBlocks.MAGNETITE,
            ModBlocks.SMOLDERING_MAGNETITE
        )
        valueLookupBuilder(BlockTags.MINEABLE_WITH_HOE).add(
            ModBlocks.ROTTEN_FLESH_BLOCK,
            ModBlocks.FERMENTED_ROTTEN_FLESH_BLOCK
        )
        valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(
            ModBlocks.BASALT_GEYSER,
            ModBlocks.ADJUSTABLE_BASALT_GEYSER,
        )
        valueLookupBuilder(BlockTags.NEEDS_STONE_TOOL).add(
            ModBlocks.SMOLDERING_MAGNETITE,
        )
        valueLookupBuilder(BlockTags.SOUL_FIRE_BASE_BLOCKS).add(
            ModBlocks.FERMENTED_ROTTEN_FLESH_BLOCK,
        )
        valueLookupBuilder(BlockTags.FIRE).add(
            ModBlocks.BLAZE_FIRE,
        )
        valueLookupBuilder(BlockTags.IMPERMEABLE).add(
            ModBlocks.SPECTREGLASS,
            ModBlocks.SHATTERED_SPECTREGLASS,
        )
        valueLookupBuilder(ModTags.MAGNETIC).add(
            Blocks.LODESTONE,
            Blocks.NETHERITE_BLOCK,
        )
        valueLookupBuilder(ModTags.ROTTEN_FLESH_FERMENTER).add(
            Blocks.SOUL_FIRE,
            Blocks.SOUL_CAMPFIRE
        )
        valueLookupBuilder(ModTags.BASALT_GEYSER_REPLACEABLE).add(
            Blocks.BASALT
        )
        valueLookupBuilder(ModTags.BASALT_GEYSER_REPLACEABLE_SUBMERGED).apply {
            forceAddTag(BlockTags.BASE_STONE_NETHER)
            add(ModBlocks.MAGNETITE)
        }
        valueLookupBuilder(ConventionalBlockTags.GLASS_BLOCKS).add(
            ModBlocks.SPECTREGLASS,
            ModBlocks.SHATTERED_SPECTREGLASS
        )
        valueLookupBuilder(ConventionalBlockTags.GLASS_PANES).add(
            ModBlocks.SPECTREGLASS_PANE,
            ModBlocks.SHATTERED_SPECTREGLASS_PANE,
        )
        valueLookupBuilder(ModTags.REVEALS_INVISIBLES).add(
            ModBlocks.SPECTREGLASS,
            ModBlocks.SPECTREGLASS_PANE,
        )
        valueLookupBuilder(ModTags.INVERTS_BEACON).add(
            ModBlocks.SPECTREGLASS,
            ModBlocks.SHATTERED_SPECTREGLASS,
            ModBlocks.SPECTREGLASS_PANE,
            ModBlocks.SHATTERED_SPECTREGLASS_PANE,
        )
    }
}
