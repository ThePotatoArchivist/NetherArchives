package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey

object NetherArchivesConfiguredFeatures {
    private fun create(path: String) = ResourceKey.create(Registries.CONFIGURED_FEATURE, NetherArchives.id(path))

    val BASALT_GEYSER = create("basalt_geyser")
    val MAGNETITE_BLOBS = create("magnetite_blobs")
    val MAGNETITE_DELTA = create("magnetite_delta")
}

internal typealias ModConfiguredFeatures = NetherArchivesConfiguredFeatures