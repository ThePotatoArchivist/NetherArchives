package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesBlocks
import archives.tater.netherarchives.registry.NetherArchivesConfiguredFeatures
import archives.tater.netherarchives.registry.NetherArchivesPlacedFeatures
import archives.tater.netherarchives.registry.NetherArchivesTags
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.Vec3i
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.tags.FluidTags
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.VerticalAnchor.aboveBottom
import net.minecraft.world.level.levelgen.VerticalAnchor.belowTop
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.*
import net.minecraft.world.level.levelgen.blockpredicates.MatchingFluidsPredicate
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceSphereConfiguration
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider.simple
import net.minecraft.world.level.levelgen.placement.*
import net.minecraft.world.level.levelgen.placement.BiomeFilter.biome
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement.uniform
import net.minecraft.world.level.levelgen.placement.InSquarePlacement.spread
import java.util.concurrent.CompletableFuture

class FeatureGenerator(output: FabricPackOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) :
    FabricDynamicRegistryProvider(output, registriesFuture) {

    @Suppress("DEPRECATION")
    override fun configure(
        registries: HolderLookup.Provider,
        entries: Entries
    ) {
        entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE))

        entries.add(
            NetherArchivesPlacedFeatures.MAGNETITE_BLOBS, PlacedFeature(
                registries.getOrThrow(NetherArchivesConfiguredFeatures.MAGNETITE_BLOBS),
                [
                    CountPlacement.of(10),
                    spread(),
                    uniform(aboveBottom(0), belowTop(0)),
                    biome(),
                ]
            )
        )

        entries.add(
            NetherArchivesPlacedFeatures.MAGNETITE_DELTA, PlacedFeature(
                registries.getOrThrow(NetherArchivesConfiguredFeatures.MAGNETITE_DELTA),
                [
                    CountOnEveryLayerPlacement.of(20),
                    biome(),
                ]
            )
        )

        entries.add(
            NetherArchivesPlacedFeatures.BASALT_GEYSER, PlacedFeature(
                registries.getOrThrow(NetherArchivesConfiguredFeatures.BASALT_GEYSER),
                [
                    CountOnEveryLayerPlacement.of(2),
                    RandomOffsetPlacement.of(ConstantInt.of(0), ConstantInt.of(-1)),
                    BlockPredicateFilter.forPredicate(
                        allOf(
                            matchesBlocks(Vec3i(0, 1, 0), Blocks.AIR),
                            matchesTag(NetherArchivesTags.BASALT_GEYSER_REPLACEABLE)
                        )
                    ),
                    biome(),
                ]
            )
        )

        entries.add(
            NetherArchivesPlacedFeatures.BASALT_GEYSER_SUBMERGED, PlacedFeature(
                registries.getOrThrow(NetherArchivesConfiguredFeatures.BASALT_GEYSER),
                [
                    CountOnEveryLayerPlacement.of(4),
                    RandomOffsetPlacement.of(ConstantInt.of(0), ConstantInt.of(-1)),
                    BlockPredicateFilter.forPredicate(
                        allOf(
                            matchesTag(NetherArchivesTags.BASALT_GEYSER_REPLACEABLE_SUBMERGED),
                            MatchingFluidsPredicate(Vec3i(0, 1, 0), registries.getOrThrow(FluidTags.LAVA)),
                            anyOf(
                                matchesBlocks(Vec3i(0, 2, 0), Blocks.AIR),
                                allOf(
                                    MatchingFluidsPredicate(Vec3i(0, 2, 0), registries.getOrThrow(FluidTags.LAVA)),
                                    matchesBlocks(Vec3i(0, 3, 0), Blocks.AIR),
                                )
                            )
                        )
                    ),
                    biome(),
                ]
            )
        )
    }

    override fun getName(): String = "Features"

    companion object : RegistrySetBuilder.RegistryBootstrap<ConfiguredFeature<*, *>> {
        override fun run(registry: BootstrapContext<ConfiguredFeature<*, *>>) {

            registry.register(
                NetherArchivesConfiguredFeatures.BASALT_GEYSER, ConfiguredFeature(
                    Feature.SIMPLE_BLOCK,
                    SimpleBlockConfiguration(simple(NetherArchivesBlocks.BASALT_GEYSER))
                )
            )

            registry.register(
                NetherArchivesConfiguredFeatures.MAGNETITE_BLOBS, ConfiguredFeature(
                    Feature.REPLACE_BLOBS,
                    ReplaceSphereConfiguration(
                        Blocks.NETHERRACK.defaultBlockState(),
                        NetherArchivesBlocks.MAGNETITE.defaultBlockState(),
                        UniformInt.of(3, 7)
                    )
                )
            )

            registry.register(
                NetherArchivesConfiguredFeatures.MAGNETITE_DELTA, ConfiguredFeature(
                    Feature.DELTA_FEATURE,
                    DeltaFeatureConfiguration(
                        Blocks.LAVA.defaultBlockState(),
                        NetherArchivesBlocks.MAGNETITE.defaultBlockState(),
                        UniformInt.of(2, 6),
                        UniformInt.of(1, 3),
                    )
                )
            )
        }

    }
}