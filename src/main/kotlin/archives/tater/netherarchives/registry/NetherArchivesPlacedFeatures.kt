package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.levelgen.placement.PlacedFeature

object NetherArchivesPlacedFeatures {
    private fun create(path: String): ResourceKey<PlacedFeature> =
        ResourceKey.create(Registries.PLACED_FEATURE, NetherArchives.id(path))

    val MAGNETITE_DELTA = create("magnetite_delta")
    val MAGNETITE_BLOBS = create("magnetite_blobs")
    val BASALT_GEYSER = create("basalt_geyser")
    val BASALT_GEYSER_SUBMERGED = create("basalt_geyser_submerged")
}

internal typealias ModPlacedFeatures = NetherArchivesPlacedFeatures