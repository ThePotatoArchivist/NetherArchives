package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesItems
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
            NetherArchivesItems.SPECTREGLASS,
            NetherArchivesItems.SHATTERED_SPECTREGLASS,
        )
        getOrCreateTagBuilder(ItemTags.ARMOR_ENCHANTABLE).add(
            NetherArchivesItems.BASALT_SKIS,
        )
        getOrCreateTagBuilder(ConventionalItemTags.MELEE_WEAPON_TOOLS).add(
            NetherArchivesItems.SPECTREGLASS_KNIFE,
        )
        getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE).add(
            NetherArchivesItems.BASALT_SKIS,
            NetherArchivesItems.BASALT_OAR,
            NetherArchivesItems.SPECTREGLASS_KNIFE,
        )
        getOrCreateTagBuilder(ItemTags.BREAKS_DECORATED_POTS).add(
            NetherArchivesItems.SPECTREGLASS_KNIFE,
        )
        getOrCreateTagBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(
            NetherArchivesItems.SPECTREGLASS_KNIFE,
        )
    }
}
