package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesBlockItemIds
import archives.tater.netherarchives.registry.NetherArchivesItemIds
import archives.tater.netherarchives.registry.NetherArchivesItemTags
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
        builder(NetherArchivesItemTags.BASALT_EQUIPMENT_REPAIR).add(
            BlockItemIds.POLISHED_BASALT,
        )
        with(builder(NetherArchivesItemTags.SKIS)) {
            add(NetherArchivesItemIds.BASALT_SKIS)
            NetherArchivesItemIds.DYED_BASALT_SKIS.forEach { add(it) }
        }
        builder(NetherArchivesItemTags.ROTTEN_FLESH_FERMENTER_ITEM).apply {
            add(BlockItemIds.SOUL_CAMPFIRE)
            add(NetherArchivesItemIds.DUMMY_SOUL_FIRE)
        }
        builder(ConventionalItemTags.HIDDEN_FROM_RECIPE_VIEWERS).add(
            NetherArchivesItemIds.DUMMY_SOUL_FIRE,
        )
        builder(ItemTags.SOUL_FIRE_BASE_BLOCKS).add(
            NetherArchivesBlockItemIds.FERMENTED_ROTTEN_FLESH_BLOCK,
        )
        builder(ConventionalItemTags.GLASS_BLOCKS).add(
            NetherArchivesBlockItemIds.SPECTREGLASS,
            NetherArchivesBlockItemIds.SHATTERED_SPECTREGLASS,
        )
        builder(ConventionalItemTags.GLASS_PANES).add(
            NetherArchivesBlockItemIds.SPECTREGLASS_PANE,
            NetherArchivesBlockItemIds.SHATTERED_SPECTREGLASS_PANE,
        )
        builder(ItemTags.FOOT_ARMOR_ENCHANTABLE)
            .addTag(NetherArchivesItemTags.SKIS)
        builder(ConventionalItemTags.MELEE_WEAPON_TOOLS).add(
            NetherArchivesItemIds.SPECTREGLASS_KNIFE,
        )
        with(builder(ItemTags.DURABILITY_ENCHANTABLE)) {
            addTag(NetherArchivesItemTags.SKIS)
            add(NetherArchivesItemIds.BASALT_OAR, NetherArchivesItemIds.SPECTREGLASS_KNIFE)
        }
        builder(ItemTags.BREAKS_DECORATED_POTS).add(
            NetherArchivesItemIds.SPECTREGLASS_KNIFE,
        )
        builder(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(
            NetherArchivesItemIds.SPECTREGLASS_KNIFE,
        )
        builder(ConventionalItemTags.IRON_RAW_MATERIALS).add(
            NetherArchivesItemIds.IRON_SLAG,
        )
        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_HOT).add(
            NetherArchivesBlockItemIds.SMOLDERING_MAGNETITE
        )
        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_REGULAR).add(
            NetherArchivesBlockItemIds.MAGNETITE
        )
        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_FAST_FLAT).add(
            NetherArchivesBlockItemIds.ROTTEN_FLESH_BLOCK
        )
        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_HIGH_RESISTANCE).add(
            NetherArchivesBlockItemIds.FERMENTED_ROTTEN_FLESH_BLOCK
        )
        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY).add(
            NetherArchivesBlockItemIds.BASALT_GEYSER,
            NetherArchivesBlockItemIds.ADJUSTABLE_BASALT_GEYSER,
        )
//        if (NetherArchives.EXPOSURE_INSTALLED)
//            builder(Exposure.Tags.Items.FILTERS).add(
//                ModItemIds.SPECTREGLASS_PANE
//            )
    }
}
