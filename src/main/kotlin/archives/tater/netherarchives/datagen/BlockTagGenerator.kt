package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.NetherArchivesTags.BASALT_GEYSER_REPLACEABLE
import archives.tater.netherarchives.NetherArchivesTags.BASALT_GEYSER_REPLACEABLE_SUBMERGED
import archives.tater.netherarchives.NetherArchivesTags.BLAZE_FIRE_TARGET
import archives.tater.netherarchives.NetherArchivesTags.MAGNETIC
import archives.tater.netherarchives.NetherArchivesTags.ROTTEN_FLESH_FERMENTER
import archives.tater.netherarchives.block.NetherArchivesBlocks
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
        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).add(
            NetherArchivesBlocks.MAGNETITE,
            NetherArchivesBlocks.SMOLDERING_MAGNETITE
        )
        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(
            NetherArchivesBlocks.ROTTEN_FLESH_BLOCK,
            NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK
        )
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(
            NetherArchivesBlocks.BASALT_GEYSER,
        )
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(
            NetherArchivesBlocks.SMOLDERING_MAGNETITE,
            NetherArchivesBlocks.BASALT_GEYSER,
        )
        getOrCreateTagBuilder(BlockTags.SOUL_FIRE_BASE_BLOCKS).add(
            NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK,
        )
        getOrCreateTagBuilder(MAGNETIC).add(
            Blocks.LODESTONE,
            Blocks.NETHERITE_BLOCK,
        )
        getOrCreateTagBuilder(BLAZE_FIRE_TARGET)
        getOrCreateTagBuilder(ROTTEN_FLESH_FERMENTER).add(
            Blocks.SOUL_FIRE,
            Blocks.SOUL_CAMPFIRE
        )
        getOrCreateTagBuilder(BASALT_GEYSER_REPLACEABLE).add(
            Blocks.BASALT
        )
        getOrCreateTagBuilder(BASALT_GEYSER_REPLACEABLE_SUBMERGED).apply {
            forceAddTag(BlockTags.BASE_STONE_NETHER)
            add(NetherArchivesBlocks.MAGNETITE)
        }
        getOrCreateTagBuilder(ConventionalBlockTags.GLASS_BLOCKS).add(
            NetherArchivesBlocks.SOUL_GLASS
        )
    }
}
