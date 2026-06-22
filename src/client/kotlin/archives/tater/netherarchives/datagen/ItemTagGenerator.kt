package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.ModItems
import archives.tater.netherarchives.registry.ModTags
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags
import net.minecraft.core.HolderLookup
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Items
import java.util.concurrent.CompletableFuture

class ItemTagGenerator(
    output: FabricPackOutput,
    registriesFuture: CompletableFuture<HolderLookup.Provider>
) : FabricTagsProvider.ItemTagsProvider(output, registriesFuture) {
    override fun addTags(arg: HolderLookup.Provider) {
        valueLookupBuilder(ModTags.BASALT_EQUIPMENT_REPAIR).add(
            Items.POLISHED_BASALT,
        )
        valueLookupBuilder(ModTags.SKIS).add(
            ModItems.BASALT_SKIS,
        )
        valueLookupBuilder(ModTags.ROTTEN_FLESH_FERMENTER_ITEM).add(
            Items.SOUL_CAMPFIRE,
            ModItems.DUMMY_SOUL_FIRE
        )
        valueLookupBuilder(ConventionalItemTags.HIDDEN_FROM_RECIPE_VIEWERS).add(
            ModItems.DUMMY_SOUL_FIRE,
        )
        valueLookupBuilder(ItemTags.SOUL_FIRE_BASE_BLOCKS).add(
            ModItems.FERMENTED_ROTTEN_FLESH_BLOCK,
        )
        valueLookupBuilder(ConventionalItemTags.GLASS_BLOCKS).add(
            ModItems.SPECTREGLASS,
            ModItems.SHATTERED_SPECTREGLASS,
        )
        valueLookupBuilder(ConventionalItemTags.GLASS_PANES).add(
            ModItems.SPECTREGLASS_PANE,
            ModItems.SHATTERED_SPECTREGLASS_PANE,
        )
        valueLookupBuilder(ItemTags.ARMOR_ENCHANTABLE).add(
            ModItems.BASALT_SKIS,
        )
        valueLookupBuilder(ConventionalItemTags.MELEE_WEAPON_TOOLS).add(
            ModItems.SPECTREGLASS_KNIFE,
        )
        valueLookupBuilder(ItemTags.DURABILITY_ENCHANTABLE).add(
            ModItems.BASALT_SKIS,
            ModItems.BASALT_OAR,
            ModItems.SPECTREGLASS_KNIFE,
        )
        valueLookupBuilder(ItemTags.BREAKS_DECORATED_POTS).add(
            ModItems.SPECTREGLASS_KNIFE,
        )
        valueLookupBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(
            ModItems.SPECTREGLASS_KNIFE,
        )
        valueLookupBuilder(ConventionalItemTags.IRON_RAW_MATERIALS).add(
            ModItems.IRON_SLAG,
        )
//        if (NetherArchives.EXPOSURE_INSTALLED)
//            valueLookupBuilder(Exposure.Tags.Items.FILTERS).add(
//                ModItems.SPECTREGLASS_PANE
//            )
    }
}
