package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesItems
import archives.tater.netherarchives.registry.NetherArchivesTags
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
        valueLookupBuilder(NetherArchivesTags.BASALT_EQUIPMENT_REPAIR).add(
            Items.POLISHED_BASALT,
        )
        with(valueLookupBuilder(NetherArchivesTags.SKIS)) {
            add(NetherArchivesItems.BASALT_SKIS)
            NetherArchivesItems.DYED_BASALT_SKIS.forEach { (_, item) -> add(item) }
        }
        valueLookupBuilder(NetherArchivesTags.ROTTEN_FLESH_FERMENTER_ITEM).add(
            Items.SOUL_CAMPFIRE,
            NetherArchivesItems.DUMMY_SOUL_FIRE
        )
        valueLookupBuilder(ConventionalItemTags.HIDDEN_FROM_RECIPE_VIEWERS).add(
            NetherArchivesItems.DUMMY_SOUL_FIRE,
        )
        valueLookupBuilder(ItemTags.SOUL_FIRE_BASE_BLOCKS).add(
            NetherArchivesItems.FERMENTED_ROTTEN_FLESH_BLOCK,
        )
        valueLookupBuilder(ConventionalItemTags.GLASS_BLOCKS).add(
            NetherArchivesItems.SPECTREGLASS,
            NetherArchivesItems.SHATTERED_SPECTREGLASS,
        )
        valueLookupBuilder(ConventionalItemTags.GLASS_PANES).add(
            NetherArchivesItems.SPECTREGLASS_PANE,
            NetherArchivesItems.SHATTERED_SPECTREGLASS_PANE,
        )
        valueLookupBuilder(ItemTags.FOOT_ARMOR_ENCHANTABLE)
            .addTag(NetherArchivesTags.SKIS)
        valueLookupBuilder(ConventionalItemTags.MELEE_WEAPON_TOOLS).add(
            NetherArchivesItems.SPECTREGLASS_KNIFE,
        )
        with(valueLookupBuilder(ItemTags.DURABILITY_ENCHANTABLE)) {
            addTag(NetherArchivesTags.SKIS)
            add(NetherArchivesItems.BASALT_OAR, NetherArchivesItems.SPECTREGLASS_KNIFE)
        }
        valueLookupBuilder(ItemTags.BREAKS_DECORATED_POTS).add(
            NetherArchivesItems.SPECTREGLASS_KNIFE,
        )
        valueLookupBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(
            NetherArchivesItems.SPECTREGLASS_KNIFE,
        )
        valueLookupBuilder(ConventionalItemTags.IRON_RAW_MATERIALS).add(
            NetherArchivesItems.IRON_SLAG,
        )
//        if (NetherArchives.EXPOSURE_INSTALLED)
//            valueLookupBuilder(Exposure.Tags.Items.FILTERS).add(
//                ModItems.SPECTREGLASS_PANE
//            )
    }
}
