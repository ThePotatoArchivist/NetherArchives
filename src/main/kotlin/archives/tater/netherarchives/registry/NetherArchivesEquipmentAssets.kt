package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.equipment.EquipmentAssets
import net.minecraft.world.level.block.ColorCollection

object NetherArchivesEquipmentAssets {
    private fun create(path: String) = ResourceKey.create(EquipmentAssets.ROOT_ID, NetherArchives.id(path))

    @JvmField val BASALT_SKIS = create("basalt_skis")
    @JvmField val DYED_BASALT_SKIS = ColorCollection.NAMES.map { color -> create("${color}_basalt_skis") }
}
