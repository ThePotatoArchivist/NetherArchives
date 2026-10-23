package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesBlockTags
import archives.tater.netherarchives.registry.NetherArchivesBlocks
import archives.tater.netherarchives.registry.NetherArchivesFeatures
import archives.tater.netherarchives.registry.NetherArchivesPlacements
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.Registry
import net.minecraft.core.Vec3i
import net.minecraft.core.registries.MultiRegistryBootstrap
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.FluidTags
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.VerticalAnchor.aboveBottom
import net.minecraft.world.level.levelgen.VerticalAnchor.belowTop
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.*
import net.minecraft.world.level.levelgen.blockpredicates.MatchingFluidsPredicate
import net.minecraft.world.level.levelgen.feature.DeltaFeature
import net.minecraft.world.level.levelgen.feature.ReplaceBlobsFeature
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import net.minecraft.world.level.levelgen.placement.*
import net.minecraft.world.level.levelgen.placement.BiomeFilter.biome
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement.uniform
import net.minecraft.world.level.levelgen.placement.InSquarePlacement.spread
import java.util.concurrent.CompletableFuture

object FeatureGenerator : MultiRegistryBootstrap {
    @Suppress("DEPRECATION")
    override fun run(registries: MultiRegistryBootstrap.BootstrapGetter) {
        val features = registries[Registries.FEATURE]
        val placedFeatures = registries[Registries.PLACED_FEATURE]
        val fluids = features.lookup(Registries.FLUID)

        val basaltGeyser = features.register(
            NetherArchivesFeatures.BASALT_GEYSER, SimpleBlockFeature(
                BlockStateProvider.of(
                    NetherArchivesBlocks.BASALT_GEYSER
                )
            )
        )

        placedFeatures.register(
            NetherArchivesPlacements.BASALT_GEYSER, PlacedFeature(
                basaltGeyser,
                [
                    CountOnEveryLayerPlacement.of(2),
                    OffsetPlacement.of(ConstantInt.of(0), ConstantInt.of(-1)),
                    BlockPredicateFilter.forPredicate(
                        allOf(
                            matchesBlocks(Vec3i(0, 1, 0), [Blocks.AIR]),
                            matchesTag(NetherArchivesBlockTags.BASALT_GEYSER_REPLACEABLE)
                        )
                    ),
                    biome(),
                ]
            )
        )

        placedFeatures.register(
            NetherArchivesPlacements.BASALT_GEYSER_SUBMERGED, PlacedFeature(
                basaltGeyser,
                [
                    CountOnEveryLayerPlacement.of(4),
                    OffsetPlacement.of(ConstantInt.of(0), ConstantInt.of(-1)),
                    BlockPredicateFilter.forPredicate(
                        allOf(
                            matchesTag(NetherArchivesBlockTags.BASALT_GEYSER_REPLACEABLE_SUBMERGED),
                            MatchingFluidsPredicate(Vec3i(0, 1, 0), fluids.getOrThrow(FluidTags.LAVA)),
                            anyOf(
                                matchesBlocks(Vec3i(0, 2, 0), [Blocks.AIR]),
                                allOf(
                                    MatchingFluidsPredicate(Vec3i(0, 2, 0), fluids.getOrThrow(FluidTags.LAVA)),
                                    matchesBlocks(Vec3i(0, 3, 0), [Blocks.AIR]),
                                )
                            )
                        )
                    ),
                    biome(),
                ]
            )
        )

        placedFeatures.register(
            NetherArchivesPlacements.MAGNETITE_BLOBS, PlacedFeature(
                features.register(
                    NetherArchivesFeatures.MAGNETITE_BLOBS, ReplaceBlobsFeature(
                        Blocks.NETHERRACK.defaultBlockState(),
                        NetherArchivesBlocks.MAGNETITE.defaultBlockState(),
                        UniformInt.of(3, 7)
                    )
                ),
                [
                    CountPlacement.of(10),
                    spread(),
                    uniform(aboveBottom(0), belowTop(0)),
                    biome(),
                ]
            )
        )

        placedFeatures.register(
            NetherArchivesPlacements.MAGNETITE_DELTA, PlacedFeature(
                features.register(
                    NetherArchivesFeatures.MAGNETITE_DELTA, DeltaFeature(
                        Blocks.LAVA.defaultBlockState(),
                        NetherArchivesBlocks.MAGNETITE.defaultBlockState(),
                        UniformInt.of(2, 6),
                        UniformInt.of(1, 3),
                    )
                ),
                [
                    CountOnEveryLayerPlacement.of(20),
                    biome(),
                ]
            )
        )
    }

    override fun requestedRegistries(): Set<ResourceKey<out Registry<*>>> = [
        Registries.FEATURE,
        Registries.PLACED_FEATURE,
    ]

    class Provider(output: FabricPackOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) :
        FabricDynamicRegistryProvider(output, registriesFuture) {

        override fun configure(registries: HolderLookup.Provider, entries: Entries) {
            entries.addAll(registries.lookupOrThrow(Registries.FEATURE))
            entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE))
        }

        override fun getName(): String = "Features"
    }
}