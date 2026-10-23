package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesBlockIds
import archives.tater.netherarchives.registry.NetherArchivesBlockItemIds
import archives.tater.netherarchives.registry.NetherArchivesBlockTags
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags
import net.minecraft.core.HolderLookup
import net.minecraft.references.BlockIds
import net.minecraft.references.BlockItemIds
import net.minecraft.tags.BlockTags
import java.util.concurrent.CompletableFuture

class BlockTagGenerator(output: FabricPackOutput, completableFuture: CompletableFuture<HolderLookup.Provider>) :
    FabricTagsProvider.BlockTagsProvider(output, completableFuture) {

    override fun addTags(arg: HolderLookup.Provider) {
        builder(BlockTags.MINEABLE_WITH_SHOVEL).add(
            NetherArchivesBlockItemIds.MAGNETITE,
            NetherArchivesBlockItemIds.SMOLDERING_MAGNETITE
        )
        builder(BlockTags.MINEABLE_WITH_HOE).add(
            NetherArchivesBlockItemIds.ROTTEN_FLESH_BLOCK,
            NetherArchivesBlockItemIds.FERMENTED_ROTTEN_FLESH_BLOCK
        )
        builder(BlockTags.MINEABLE_WITH_PICKAXE).add(
            NetherArchivesBlockItemIds.BASALT_GEYSER,
            NetherArchivesBlockItemIds.ADJUSTABLE_BASALT_GEYSER,
        )
        builder(BlockTags.NEEDS_STONE_TOOL).add(
            NetherArchivesBlockItemIds.SMOLDERING_MAGNETITE,
        )
        builder(BlockTags.SOUL_FIRE_BASE_BLOCKS).add(
            NetherArchivesBlockItemIds.FERMENTED_ROTTEN_FLESH_BLOCK,
        )
        builder(BlockTags.FIRE).add(
            NetherArchivesBlockIds.BLAZE_FIRE,
        )
        builder(BlockTags.IMPERMEABLE).add(
            NetherArchivesBlockItemIds.SPECTREGLASS,
            NetherArchivesBlockItemIds.SHATTERED_SPECTREGLASS,
        )
        builder(NetherArchivesBlockTags.MAGNETIC).add(
            BlockItemIds.LODESTONE,
            BlockItemIds.NETHERITE_BLOCK,
        )
        builder(NetherArchivesBlockTags.ROTTEN_FLESH_FERMENTER).apply {
            add(BlockIds.SOUL_FIRE)
            add(BlockItemIds.SOUL_CAMPFIRE)
        }
        builder(NetherArchivesBlockTags.BASALT_GEYSER_REPLACEABLE).add(
            BlockItemIds.BASALT
        )
        builder(NetherArchivesBlockTags.BASALT_GEYSER_REPLACEABLE_SUBMERGED).apply {
            forceAddTag(BlockTags.BASE_STONE_NETHER)
            add(NetherArchivesBlockItemIds.MAGNETITE)
        }
        builder(ConventionalBlockTags.GLASS_BLOCKS).add(
            NetherArchivesBlockItemIds.SPECTREGLASS,
            NetherArchivesBlockItemIds.SHATTERED_SPECTREGLASS
        )
        builder(ConventionalBlockTags.GLASS_PANES).add(
            NetherArchivesBlockItemIds.SPECTREGLASS_PANE,
            NetherArchivesBlockItemIds.SHATTERED_SPECTREGLASS_PANE,
        )
        builder(NetherArchivesBlockTags.REVEALS_INVISIBLES).add(
            NetherArchivesBlockItemIds.SPECTREGLASS,
            NetherArchivesBlockItemIds.SPECTREGLASS_PANE,
        )
        builder(NetherArchivesBlockTags.INVERTS_BEACON).add(
            NetherArchivesBlockItemIds.SPECTREGLASS,
            NetherArchivesBlockItemIds.SHATTERED_SPECTREGLASS,
            NetherArchivesBlockItemIds.SPECTREGLASS_PANE,
            NetherArchivesBlockItemIds.SHATTERED_SPECTREGLASS_PANE,
        )
        builder(BlockTags.CAUSES_PERIODIC_GEYSER_ERUPTIONS).add(
            NetherArchivesBlockItemIds.SMOLDERING_MAGNETITE
        )
        builder(BlockTags.CAUSES_CONTINUOUS_GEYSER_ERUPTIONS).add(
            NetherArchivesBlockItemIds.BASALT_GEYSER
        )
    }
}
