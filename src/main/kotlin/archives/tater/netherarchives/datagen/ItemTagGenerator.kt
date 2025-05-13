package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.item.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.ItemTags
import java.util.concurrent.CompletableFuture

class ItemTagGenerator(
    output: FabricDataOutput,
    registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricTagProvider.ItemTagProvider(output, registriesFuture) {
    override fun configure(arg: RegistryWrapper.WrapperLookup?) {
        getOrCreateTagBuilder(ConventionalItemTags.HIDDEN_FROM_RECIPE_VIEWERS).add(
            NetherArchivesItems.DUMMY_SOUL_FIRE,
        )
        getOrCreateTagBuilder(ItemTags.SOUL_FIRE_BASE_BLOCKS).add(
            NetherArchivesItems.FERMENTED_ROTTEN_FLESH_BLOCK,
        )
        getOrCreateTagBuilder(ConventionalItemTags.GLASS_BLOCKS).add(
            NetherArchivesItems.SOUL_GLASS,
            NetherArchivesItems.SHATTERED_SOUL_GLASS,
        )
    }
}
