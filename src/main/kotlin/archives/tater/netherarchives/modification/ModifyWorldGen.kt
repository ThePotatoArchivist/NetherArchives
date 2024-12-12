package archives.tater.netherarchives.modification

import archives.tater.netherarchives.NetherArchives
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import net.minecraft.world.biome.BiomeKeys
import net.minecraft.world.gen.GenerationStep


object ModifyWorldGen {
    private val MAGNETITE_DELTA =
        RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier(NetherArchives.MOD_ID, "magnetite_delta"))
    private val MAGNETITE_BLOBS =
        RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier(NetherArchives.MOD_ID, "magnetite_blobs"))
    private val BASALT_GEYSER =
        RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier(NetherArchives.MOD_ID, "basalt_geyser"))
    private val BASALT_GEYSER_SUBMERGED =
        RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier(NetherArchives.MOD_ID, "basalt_geyser_submerged"))

    operator fun invoke() {
        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.BASALT_DELTAS),
            GenerationStep.Feature.SURFACE_STRUCTURES,
            MAGNETITE_DELTA
        )
        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.BASALT_DELTAS),
            GenerationStep.Feature.SURFACE_STRUCTURES,
            MAGNETITE_BLOBS
        )
        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.BASALT_DELTAS),
            GenerationStep.Feature.TOP_LAYER_MODIFICATION,
            BASALT_GEYSER
        )
        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.BASALT_DELTAS),
            GenerationStep.Feature.TOP_LAYER_MODIFICATION,
            BASALT_GEYSER_SUBMERGED
        )
    }
}
