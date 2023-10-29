package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.block.NetherArchivesBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.block.Block
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import java.util.concurrent.CompletableFuture

class BlockTagGenerator(output: FabricDataOutput, completableFuture: CompletableFuture<RegistryWrapper.WrapperLookup>) : FabricTagProvider.BlockTagProvider(output, completableFuture) {
   companion object {
      private val SHOVEL_MINEABLE: TagKey<Block> =
         TagKey.of(RegistryKeys.BLOCK, Identifier("minecraft", "mineable/shovel"))
   }

   override fun configure(arg: RegistryWrapper.WrapperLookup) {
      getOrCreateTagBuilder(SHOVEL_MINEABLE)
         .add(NetherArchivesBlocks.MAGNETITE)
   }
}
