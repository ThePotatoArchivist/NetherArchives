package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesBlocks
import archives.tater.netherarchives.registry.NetherArchivesBlocks.ADJUSTABLE_BASALT_GEYSER
import archives.tater.netherarchives.registry.NetherArchivesBlocks.BASALT_GEYSER
import archives.tater.netherarchives.registry.NetherArchivesBlocks.BLAZE_FIRE
import archives.tater.netherarchives.registry.NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK
import archives.tater.netherarchives.registry.NetherArchivesBlocks.MAGNETITE
import archives.tater.netherarchives.registry.NetherArchivesBlocks.SHATTERED_SPECTREGLASS
import archives.tater.netherarchives.registry.NetherArchivesBlocks.SHATTERED_SPECTREGLASS_PANE
import archives.tater.netherarchives.registry.NetherArchivesBlocks.SMOLDERING_MAGNETITE
import archives.tater.netherarchives.registry.NetherArchivesBlocks.SPECTREGLASS
import archives.tater.netherarchives.registry.NetherArchivesBlocks.SPECTREGLASS_PANE
import archives.tater.netherarchives.registry.NetherArchivesTags.BASALT_GEYSER_REPLACEABLE
import archives.tater.netherarchives.registry.NetherArchivesTags.BASALT_GEYSER_REPLACEABLE_SUBMERGED
import archives.tater.netherarchives.registry.NetherArchivesTags.BLAZE_FIRE_TARGET
import archives.tater.netherarchives.registry.NetherArchivesTags.INVERTS_BEACON
import archives.tater.netherarchives.registry.NetherArchivesTags.MAGNETIC
import archives.tater.netherarchives.registry.NetherArchivesTags.REVEALS_INVISIBLES
import archives.tater.netherarchives.registry.NetherArchivesTags.ROTTEN_FLESH_FERMENTER
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags
import net.minecraft.block.Blocks
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.BlockTags
import java.util.concurrent.CompletableFuture

class BlockTagGenerator(output: FabricDataOutput, completableFuture: CompletableFuture<RegistryWrapper.WrapperLookup>) :
    FabricTagProvider.BlockTagProvider(output, completableFuture) {

    override fun configure(arg: RegistryWrapper.WrapperLookup) {
        valueLookupBuilder(BlockTags.SHOVEL_MINEABLE).add(
            MAGNETITE,
            SMOLDERING_MAGNETITE
        )
        valueLookupBuilder(BlockTags.HOE_MINEABLE).add(
            NetherArchivesBlocks.ROTTEN_FLESH_BLOCK,
            FERMENTED_ROTTEN_FLESH_BLOCK
        )
        valueLookupBuilder(BlockTags.PICKAXE_MINEABLE).add(
            BASALT_GEYSER,
            ADJUSTABLE_BASALT_GEYSER,
        )
        valueLookupBuilder(BlockTags.NEEDS_STONE_TOOL).add(
            SMOLDERING_MAGNETITE,
        )
        valueLookupBuilder(BlockTags.SOUL_FIRE_BASE_BLOCKS).add(
            FERMENTED_ROTTEN_FLESH_BLOCK,
        )
        valueLookupBuilder(BlockTags.FIRE).add(
            BLAZE_FIRE,
        )
        valueLookupBuilder(BlockTags.IMPERMEABLE).add(
            SPECTREGLASS,
            SHATTERED_SPECTREGLASS,
        )
        valueLookupBuilder(MAGNETIC).add(
            Blocks.LODESTONE,
            Blocks.NETHERITE_BLOCK,
        )
        valueLookupBuilder(BLAZE_FIRE_TARGET)
        valueLookupBuilder(ROTTEN_FLESH_FERMENTER).add(
            Blocks.SOUL_FIRE,
            Blocks.SOUL_CAMPFIRE
        )
        valueLookupBuilder(BASALT_GEYSER_REPLACEABLE).add(
            Blocks.BASALT
        )
        valueLookupBuilder(BASALT_GEYSER_REPLACEABLE_SUBMERGED).apply {
            forceAddTag(BlockTags.BASE_STONE_NETHER)
            add(MAGNETITE)
        }
        valueLookupBuilder(ConventionalBlockTags.GLASS_BLOCKS).add(
            SPECTREGLASS,
            SHATTERED_SPECTREGLASS
        )
        valueLookupBuilder(ConventionalBlockTags.GLASS_PANES).add(
            SPECTREGLASS_PANE,
            SHATTERED_SPECTREGLASS_PANE,
        )
        valueLookupBuilder(REVEALS_INVISIBLES).add(
            SPECTREGLASS,
            SPECTREGLASS_PANE,
        )
        valueLookupBuilder(INVERTS_BEACON).add(
            SPECTREGLASS,
            SHATTERED_SPECTREGLASS,
            SPECTREGLASS_PANE,
            SHATTERED_SPECTREGLASS_PANE,
        )
    }
}
