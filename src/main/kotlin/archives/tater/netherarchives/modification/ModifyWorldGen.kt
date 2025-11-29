package archives.tater.netherarchives.modification

import archives.tater.netherarchives.NetherArchives
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.resources.ResourceKey
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.biome.Biomes
import net.minecraft.world.level.levelgen.GenerationStep


private val MAGNETITE_DELTA =
    ResourceKey.create(Registries.PLACED_FEATURE, NetherArchives.id("magnetite_delta"))
private val MAGNETITE_BLOBS =
    ResourceKey.create(Registries.PLACED_FEATURE, NetherArchives.id("magnetite_blobs"))
private val BASALT_GEYSER =
    ResourceKey.create(Registries.PLACED_FEATURE, NetherArchives.id("basalt_geyser"))
private val BASALT_GEYSER_SUBMERGED =
    ResourceKey.create(Registries.PLACED_FEATURE, NetherArchives.id("basalt_geyser_submerged"))

internal fun modifyWorldGen() {
    BiomeModifications.addFeature(
        BiomeSelectors.includeByKey(Biomes.BASALT_DELTAS),
        GenerationStep.Decoration.SURFACE_STRUCTURES,
        MAGNETITE_DELTA
    )
    BiomeModifications.addFeature(
        BiomeSelectors.includeByKey(Biomes.BASALT_DELTAS),
        GenerationStep.Decoration.SURFACE_STRUCTURES,
        MAGNETITE_BLOBS
    )
    BiomeModifications.addFeature(
        BiomeSelectors.includeByKey(Biomes.BASALT_DELTAS),
        GenerationStep.Decoration.TOP_LAYER_MODIFICATION,
        BASALT_GEYSER
    )
    BiomeModifications.addFeature(
        BiomeSelectors.includeByKey(Biomes.BASALT_DELTAS),
        GenerationStep.Decoration.TOP_LAYER_MODIFICATION,
        BASALT_GEYSER_SUBMERGED
    )
}
