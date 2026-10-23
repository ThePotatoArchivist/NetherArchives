package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey

object NetherArchivesFeatures {
    private fun create(path: String) = ResourceKey.create(Registries.FEATURE, NetherArchives.id(path))

    @JvmField val BASALT_GEYSER = create("basalt_geyser")
    @JvmField val MAGNETITE_BLOBS = create("magnetite_blobs")
    @JvmField val MAGNETITE_DELTA = create("magnetite_delta")
}
