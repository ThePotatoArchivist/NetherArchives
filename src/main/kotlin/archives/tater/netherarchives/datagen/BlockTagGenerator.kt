package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.block.NetherArchivesBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import java.util.concurrent.CompletableFuture

class BlockTagGenerator(output: FabricDataOutput, completableFuture: CompletableFuture<RegistryWrapper.WrapperLookup>) :
    FabricTagProvider.BlockTagProvider(output, completableFuture) {
    companion object {
        private val SHOVEL_MINEABLE: TagKey<Block> =
            TagKey.of(RegistryKeys.BLOCK, Identifier("minecraft", "mineable/shovel"))

        private val HOE_MINEABLE: TagKey<Block> =
            TagKey.of(RegistryKeys.BLOCK, Identifier("minecraft", "mineable/hoe"))

        private val NEEDS_STONE_TOOL: TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, Identifier("minecraft", "needs_stone_tool"))

        val MAGNETIC: TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, Identifier(NetherArchives.NAMESPACE, "magnetic"))

        val BLAZE_FIRE_TARGET: TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, Identifier(NetherArchives.NAMESPACE, "blaze_fire_target"))

        val ROTTEN_FLESH_FERMENTER: TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, Identifier(NetherArchives.NAMESPACE, "rotten_flesh_fermenter"))
    }

    override fun configure(arg: RegistryWrapper.WrapperLookup) {
        getOrCreateTagBuilder(SHOVEL_MINEABLE).add(
            NetherArchivesBlocks.MAGNETITE,
            NetherArchivesBlocks.SMOLDERING_MAGNETITE
        )
        getOrCreateTagBuilder(HOE_MINEABLE).add(
            NetherArchivesBlocks.ROTTEN_FLESH_BLOCK,
            NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK
        )
        getOrCreateTagBuilder(NEEDS_STONE_TOOL).add(
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
