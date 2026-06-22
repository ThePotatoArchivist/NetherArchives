package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey

object NetherArchivesItemIds {
    private fun create(id: Identifier) = ResourceKey.create(Registries.ITEM, id)
    private fun create(path: String) = create(NetherArchives.id(path))

    val IRON_SLAG = create("iron_slag")
    val BLAZE_LANTERN = create("blaze_lantern")
    val BASALT_SKIS = create("basalt_skis")
    val BASALT_OAR = create("basalt_oar")
    val BASALT_ROD = create("basalt_rod")
    val SPECTREGLASS_SHARD = create("spectreglass_shard")
    val SPECTREGLASS_KNIFE = create("spectreglass_knife")
    val DUMMY_SOUL_FIRE = create("dummy_soul_fire")
}

internal typealias ModItemIds = NetherArchivesItemIds