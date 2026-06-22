package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.ModBlockIds
import archives.tater.netherarchives.registry.ModBlockItemIds
import archives.tater.netherarchives.registry.ModTags
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
            ModBlockItemIds.MAGNETITE,
            ModBlockItemIds.SMOLDERING_MAGNETITE
        )
        builder(BlockTags.MINEABLE_WITH_HOE).add(
            ModBlockItemIds.ROTTEN_FLESH_BLOCK,
            ModBlockItemIds.FERMENTED_ROTTEN_FLESH_BLOCK
        )
        builder(BlockTags.MINEABLE_WITH_PICKAXE).add(
            ModBlockItemIds.BASALT_GEYSER,
            ModBlockItemIds.ADJUSTABLE_BASALT_GEYSER,
        )
        builder(BlockTags.NEEDS_STONE_TOOL).add(
            ModBlockItemIds.SMOLDERING_MAGNETITE,
        )
        builder(BlockTags.SOUL_FIRE_BASE_BLOCKS).add(
            ModBlockItemIds.FERMENTED_ROTTEN_FLESH_BLOCK,
        )
        builder(BlockTags.FIRE).add(
            ModBlockIds.BLAZE_FIRE,
        )
        builder(BlockTags.IMPERMEABLE).add(
            ModBlockItemIds.SPECTREGLASS,
            ModBlockItemIds.SHATTERED_SPECTREGLASS,
        )
        builder(ModTags.MAGNETIC).add(
            BlockItemIds.LODESTONE,
            BlockItemIds.NETHERITE_BLOCK,
        )
        builder(ModTags.ROTTEN_FLESH_FERMENTER).apply {
            add(BlockIds.SOUL_FIRE)
            add(BlockItemIds.SOUL_CAMPFIRE)
        }
        builder(ModTags.BASALT_GEYSER_REPLACEABLE).add(
            BlockItemIds.BASALT
        )
        builder(ModTags.BASALT_GEYSER_REPLACEABLE_SUBMERGED).apply {
            forceAddTag(BlockTags.BASE_STONE_NETHER)
            add(ModBlockItemIds.MAGNETITE)
        }
        builder(ConventionalBlockTags.GLASS_BLOCKS).add(
            ModBlockItemIds.SPECTREGLASS,
            ModBlockItemIds.SHATTERED_SPECTREGLASS
        )
        builder(ConventionalBlockTags.GLASS_PANES).add(
            ModBlockItemIds.SPECTREGLASS_PANE,
            ModBlockItemIds.SHATTERED_SPECTREGLASS_PANE,
        )
        builder(ModTags.REVEALS_INVISIBLES).add(
            ModBlockItemIds.SPECTREGLASS,
            ModBlockItemIds.SPECTREGLASS_PANE,
        )
        builder(ModTags.INVERTS_BEACON).add(
            ModBlockItemIds.SPECTREGLASS,
            ModBlockItemIds.SHATTERED_SPECTREGLASS,
            ModBlockItemIds.SPECTREGLASS_PANE,
            ModBlockItemIds.SHATTERED_SPECTREGLASS_PANE,
        )
    }
}
