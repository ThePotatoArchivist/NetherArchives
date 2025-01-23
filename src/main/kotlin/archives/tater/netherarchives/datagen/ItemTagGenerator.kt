package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.item.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.ItemTags
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import java.util.concurrent.CompletableFuture

class ItemTagGenerator(
    output: FabricDataOutput,
    completableFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricTagProvider.ItemTagProvider(output, completableFuture) {
    override fun configure(arg: RegistryWrapper.WrapperLookup?) {
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, Identifier("c", "hidden_from_recipe_viewers"))).add(
            NetherArchivesItems.DUMMY_SOUL_FIRE,
        )
        getOrCreateTagBuilder(ItemTags.SOUL_FIRE_BASE_BLOCKS).add(
            NetherArchivesItems.FERMENTED_ROTTEN_FLESH_BLOCK,
        )
    }
}
