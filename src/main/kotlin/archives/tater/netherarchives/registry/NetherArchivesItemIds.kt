package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.block.ColorCollection

object NetherArchivesItemIds {
    private fun create(id: Identifier) = ResourceKey.create(Registries.ITEM, id)
    private fun create(path: String) = create(NetherArchives.id(path))

    @JvmField val IRON_SLAG = create("iron_slag")
    @JvmField val BLAZE_LANTERN = create("blaze_lantern")
    @JvmField val BASALT_SKIS = create("basalt_skis")
    @JvmField val DYED_BASALT_SKIS = ColorCollection.NAMES.map { color -> create("${color}_basalt_skis") }
    @JvmField val BASALT_OAR = create("basalt_oar")
    @JvmField val BASALT_ROD = create("basalt_rod")
    @JvmField val SPECTREGLASS_SHARD = create("spectreglass_shard")
    @JvmField val SPECTREGLASS_KNIFE = create("spectreglass_knife")
    @JvmField val DUMMY_SOUL_FIRE = create("dummy_soul_fire")
}
