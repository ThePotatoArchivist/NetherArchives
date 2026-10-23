package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.equipment.EquipmentAssets

object NetherArchivesEquipmentAssets {
    private fun create(path: String) = ResourceKey.create(EquipmentAssets.ROOT_ID, NetherArchives.id(path))

    val BASALT_SKIS = create("basalt_skis")
}

internal typealias ModEquipmentAssets = NetherArchivesEquipmentAssets