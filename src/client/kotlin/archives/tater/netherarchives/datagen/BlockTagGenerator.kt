package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesBlocks
import archives.tater.netherarchives.registry.NetherArchivesTags
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
            NetherArchivesBlocks.MAGNETITE,
            NetherArchivesBlocks.SMOLDERING_MAGNETITE
        )
        valueLookupBuilder(BlockTags.MINEABLE_WITH_HOE).add(
            NetherArchivesBlocks.ROTTEN_FLESH_BLOCK,
            NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK
        )
        valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(
            NetherArchivesBlocks.BASALT_GEYSER,
            NetherArchivesBlocks.ADJUSTABLE_BASALT_GEYSER,
        )
        valueLookupBuilder(BlockTags.NEEDS_STONE_TOOL).add(
            NetherArchivesBlocks.SMOLDERING_MAGNETITE,
        )
        valueLookupBuilder(BlockTags.SOUL_FIRE_BASE_BLOCKS).add(
            NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK,
        )
        valueLookupBuilder(BlockTags.FIRE).add(
            NetherArchivesBlocks.BLAZE_FIRE,
        )
        valueLookupBuilder(BlockTags.IMPERMEABLE).add(
            NetherArchivesBlocks.SPECTREGLASS,
            NetherArchivesBlocks.SHATTERED_SPECTREGLASS,
        )
        valueLookupBuilder(NetherArchivesTags.MAGNETIC).add(
            Blocks.LODESTONE,
            Blocks.NETHERITE_BLOCK,
        )
        valueLookupBuilder(NetherArchivesTags.ROTTEN_FLESH_FERMENTER).add(
            Blocks.SOUL_FIRE,
            Blocks.SOUL_CAMPFIRE
        )
        valueLookupBuilder(NetherArchivesTags.BASALT_GEYSER_REPLACEABLE).add(
            Blocks.BASALT
        )
        valueLookupBuilder(NetherArchivesTags.BASALT_GEYSER_REPLACEABLE_SUBMERGED).apply {
            forceAddTag(BlockTags.BASE_STONE_NETHER)
            add(NetherArchivesBlocks.MAGNETITE)
        }
        valueLookupBuilder(ConventionalBlockTags.GLASS_BLOCKS).add(
            NetherArchivesBlocks.SPECTREGLASS,
            NetherArchivesBlocks.SHATTERED_SPECTREGLASS
        )
        valueLookupBuilder(ConventionalBlockTags.GLASS_PANES).add(
            NetherArchivesBlocks.SPECTREGLASS_PANE,
            NetherArchivesBlocks.SHATTERED_SPECTREGLASS_PANE,
        )
        valueLookupBuilder(NetherArchivesTags.REVEALS_INVISIBLES).add(
            NetherArchivesBlocks.SPECTREGLASS,
            NetherArchivesBlocks.SPECTREGLASS_PANE,
        )
        valueLookupBuilder(NetherArchivesTags.INVERTS_BEACON).add(
            NetherArchivesBlocks.SPECTREGLASS,
            NetherArchivesBlocks.SHATTERED_SPECTREGLASS,
            NetherArchivesBlocks.SPECTREGLASS_PANE,
            NetherArchivesBlocks.SHATTERED_SPECTREGLASS_PANE,
        )
    }
}
