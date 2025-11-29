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
import archives.tater.netherarchives.registry.NetherArchivesTags
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags
import net.minecraft.core.HolderLookup
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Items
import java.util.concurrent.CompletableFuture

class ItemTagGenerator(
    output: FabricDataOutput,
    registriesFuture: CompletableFuture<HolderLookup.Provider>
) : FabricTagProvider.ItemTagProvider(output, registriesFuture) {
    override fun addTags(arg: HolderLookup.Provider) {
        valueLookupBuilder(NetherArchivesTags.BASALT_EQUIPMENT_REPAIR).add(
            Items.POLISHED_BASALT,
        )
        valueLookupBuilder(NetherArchivesTags.SKIS).add(
            BASALT_SKIS,
        )
        valueLookupBuilder(ConventionalItemTags.HIDDEN_FROM_RECIPE_VIEWERS).add(
            DUMMY_SOUL_FIRE,
        )
        valueLookupBuilder(ItemTags.SOUL_FIRE_BASE_BLOCKS).add(
            FERMENTED_ROTTEN_FLESH_BLOCK,
        )
        valueLookupBuilder(ConventionalItemTags.GLASS_BLOCKS).add(
            SPECTREGLASS,
            SHATTERED_SPECTREGLASS,
        )
        valueLookupBuilder(ConventionalItemTags.GLASS_PANES).add(
            SPECTREGLASS_PANE,
            SHATTERED_SPECTREGLASS_PANE,
        )
        valueLookupBuilder(ItemTags.ARMOR_ENCHANTABLE).add(
            BASALT_SKIS,
        )
        valueLookupBuilder(ConventionalItemTags.MELEE_WEAPON_TOOLS).add(
            SPECTREGLASS_KNIFE,
        )
        valueLookupBuilder(ItemTags.DURABILITY_ENCHANTABLE).add(
            BASALT_SKIS,
            BASALT_OAR,
            SPECTREGLASS_KNIFE,
        )
        valueLookupBuilder(ItemTags.BREAKS_DECORATED_POTS).add(
            SPECTREGLASS_KNIFE,
        )
        valueLookupBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(
            SPECTREGLASS_KNIFE,
        )
    }
}
