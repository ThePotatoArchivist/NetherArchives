package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesItems.BASALT_OAR
import archives.tater.netherarchives.registry.NetherArchivesItems.BASALT_SKIS
import archives.tater.netherarchives.registry.NetherArchivesItems.DUMMY_SOUL_FIRE
import archives.tater.netherarchives.registry.NetherArchivesItems.FERMENTED_ROTTEN_FLESH_BLOCK
import archives.tater.netherarchives.registry.NetherArchivesItems.SHATTERED_SPECTREGLASS
import archives.tater.netherarchives.registry.NetherArchivesItems.SHATTERED_SPECTREGLASS_PANE
import archives.tater.netherarchives.registry.NetherArchivesItems.SPECTREGLASS
import archives.tater.netherarchives.registry.NetherArchivesItems.SPECTREGLASS_KNIFE
import archives.tater.netherarchives.registry.NetherArchivesItems.SPECTREGLASS_PANE
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
            DUMMY_SOUL_FIRE,
        )
        getOrCreateTagBuilder(ItemTags.SOUL_FIRE_BASE_BLOCKS).add(
            FERMENTED_ROTTEN_FLESH_BLOCK,
        )
        getOrCreateTagBuilder(ConventionalItemTags.GLASS_BLOCKS).add(
            SPECTREGLASS,
            SHATTERED_SPECTREGLASS,
        )
        getOrCreateTagBuilder(ConventionalItemTags.GLASS_PANES).add(
            SPECTREGLASS_PANE,
            SHATTERED_SPECTREGLASS_PANE,
        )
        getOrCreateTagBuilder(ItemTags.ARMOR_ENCHANTABLE).add(
            BASALT_SKIS,
        )
        getOrCreateTagBuilder(ConventionalItemTags.MELEE_WEAPON_TOOLS).add(
            SPECTREGLASS_KNIFE,
        )
        getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE).add(
            BASALT_SKIS,
            BASALT_OAR,
            SPECTREGLASS_KNIFE,
        )
        getOrCreateTagBuilder(ItemTags.BREAKS_DECORATED_POTS).add(
            SPECTREGLASS_KNIFE,
        )
        getOrCreateTagBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(
            SPECTREGLASS_KNIFE,
        )
    }
}
