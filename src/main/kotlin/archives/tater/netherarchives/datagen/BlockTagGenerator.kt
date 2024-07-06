package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.NetherArchivesTags.BLAZE_FIRE_TARGET
import archives.tater.netherarchives.NetherArchivesTags.MAGNETIC
import archives.tater.netherarchives.NetherArchivesTags.ROTTEN_FLESH_FERMENTER
import archives.tater.netherarchives.block.NetherArchivesBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
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
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(
            NetherArchivesBlocks.SMOLDERING_MAGNETITE
        )
        getOrCreateTagBuilder(MAGNETIC).add(
            Blocks.LODESTONE
        )
        getOrCreateTagBuilder(BLAZE_FIRE_TARGET)
        getOrCreateTagBuilder(ROTTEN_FLESH_FERMENTER).add(
            Blocks.SOUL_FIRE,
            Blocks.SOUL_CAMPFIRE
        )
    }
}
