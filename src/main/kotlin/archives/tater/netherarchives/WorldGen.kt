package archives.tater.netherarchives

import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.util.Identifier
import net.minecraft.world.biome.BiomeKeys
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.feature.PlacedFeature
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier


object WorldGen {
    private val MAGNETITE_DELTA = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier(NetherArchives.NAMESPACE, "magnetite_delta"))

    fun addFeatures() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BASALT_DELTAS), GenerationStep.Feature.SURFACE_STRUCTURES, MAGNETITE_DELTA)
    }
}