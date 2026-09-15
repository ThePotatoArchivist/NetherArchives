package archives.tater.netherarchives.modification

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.registry.ModPlacedFeatures
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.fabricmc.fabric.api.biome.v1.ModificationPhase
import net.minecraft.world.level.biome.Biomes
import net.minecraft.world.level.levelgen.GenerationStep

internal fun modifyWorldGen() {
    with (BiomeModifications.create(NetherArchives.id("basalt_deltas"))) {
        add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(Biomes.BASALT_DELTAS)) {
            with (it.generationSettings) {
                addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.MAGNETITE_DELTA)
                addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.MAGNETITE_BLOBS)
                addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.BASALT_GEYSER)
                addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, ModPlacedFeatures.BASALT_GEYSER_SUBMERGED)
            }
        }
    }
}
