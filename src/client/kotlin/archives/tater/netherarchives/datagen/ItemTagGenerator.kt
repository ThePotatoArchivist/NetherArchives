package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.ModBlockItemIds
import archives.tater.netherarchives.registry.ModItemIds
import archives.tater.netherarchives.registry.ModTags
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags
import net.minecraft.core.HolderLookup
import net.minecraft.references.BlockItemIds
import net.minecraft.tags.ItemTags
import java.util.concurrent.CompletableFuture

class ItemTagGenerator(
    output: FabricPackOutput,
    registriesFuture: CompletableFuture<HolderLookup.Provider>
) : FabricTagsProvider.ItemTagsProvider(output, registriesFuture) {
    override fun addTags(arg: HolderLookup.Provider) {
        builder(ModTags.BASALT_EQUIPMENT_REPAIR).add(
            BlockItemIds.POLISHED_BASALT,
        )
        builder(ModTags.SKIS).add(
            ModItemIds.BASALT_SKIS,
        )
        builder(ModTags.ROTTEN_FLESH_FERMENTER_ITEM).apply {
            add(BlockItemIds.SOUL_CAMPFIRE)
            add(ModItemIds.DUMMY_SOUL_FIRE)
        }
        builder(ConventionalItemTags.HIDDEN_FROM_RECIPE_VIEWERS).add(
            ModItemIds.DUMMY_SOUL_FIRE,
        )
        builder(ItemTags.SOUL_FIRE_BASE_BLOCKS).add(
            ModBlockItemIds.FERMENTED_ROTTEN_FLESH_BLOCK,
        )
        builder(ConventionalItemTags.GLASS_BLOCKS).add(
            ModBlockItemIds.SPECTREGLASS,
            ModBlockItemIds.SHATTERED_SPECTREGLASS,
        )
        builder(ConventionalItemTags.GLASS_PANES).add(
            ModBlockItemIds.SPECTREGLASS_PANE,
            ModBlockItemIds.SHATTERED_SPECTREGLASS_PANE,
        )
        builder(ItemTags.ARMOR_ENCHANTABLE).add(
            ModItemIds.BASALT_SKIS,
        )
        builder(ConventionalItemTags.MELEE_WEAPON_TOOLS).add(
            ModItemIds.SPECTREGLASS_KNIFE,
        )
        builder(ItemTags.DURABILITY_ENCHANTABLE).add(
            ModItemIds.BASALT_SKIS,
            ModItemIds.BASALT_OAR,
            ModItemIds.SPECTREGLASS_KNIFE,
        )
        builder(ItemTags.BREAKS_DECORATED_POTS).add(
            ModItemIds.SPECTREGLASS_KNIFE,
        )
        builder(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(
            ModItemIds.SPECTREGLASS_KNIFE,
        )
        builder(ConventionalItemTags.IRON_RAW_MATERIALS).add(
            ModItemIds.IRON_SLAG,
        )
        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_HOT).add(
            ModBlockItemIds.SMOLDERING_MAGNETITE
        )
        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_REGULAR).add(
            ModBlockItemIds.MAGNETITE
        )
        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_FAST_FLAT).add(
            ModBlockItemIds.ROTTEN_FLESH_BLOCK
        )
        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_HIGH_RESISTANCE).add(
            ModBlockItemIds.FERMENTED_ROTTEN_FLESH_BLOCK
        )
        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY).add(
            ModBlockItemIds.BASALT_GEYSER,
            ModBlockItemIds.ADJUSTABLE_BASALT_GEYSER,
        )
//        if (NetherArchives.EXPOSURE_INSTALLED)
//            builder(Exposure.Tags.Items.FILTERS).add(
//                ModItemIds.SPECTREGLASS_PANE
//            )
    }
}
