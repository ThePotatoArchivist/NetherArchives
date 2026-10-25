package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey

object NetherArchivesItemTags {
    private fun create(path: String) = TagKey.create(Registries.ITEM, NetherArchives.id(path))

    @JvmField val BASALT_EQUIPMENT_REPAIR = create("basalt_equipment_repair")
    @JvmField val SKIS = create("skis")
    @JvmField val ROTTEN_FLESH_FERMENTER = create("rotten_flesh_fermenter")
}