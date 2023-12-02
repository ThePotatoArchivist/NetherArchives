package archives.tater.netherarchives

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.entity.BannerPattern
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.command.argument.serialize.ArgumentSerializer
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.brain.MemoryModuleType
import net.minecraft.entity.ai.brain.sensor.SensorType
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.decoration.painting.PaintingVariant
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.passive.CatVariant
import net.minecraft.entity.passive.FrogVariant
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.loot.condition.LootConditionType
import net.minecraft.loot.entry.LootPoolEntryType
import net.minecraft.loot.function.LootFunctionType
import net.minecraft.loot.provider.nbt.LootNbtProviderType
import net.minecraft.loot.provider.number.LootNumberProviderType
import net.minecraft.loot.provider.score.LootScoreProviderType
import net.minecraft.particle.ParticleType
import net.minecraft.potion.Potion
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.registry.Registries
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.sound.SoundEvent
import net.minecraft.stat.StatType
import net.minecraft.structure.StructurePieceType
import net.minecraft.structure.pool.StructurePoolElementType
import net.minecraft.structure.processor.StructureProcessorType
import net.minecraft.structure.rule.PosRuleTestType
import net.minecraft.structure.rule.RuleTestType
import net.minecraft.structure.rule.blockentity.RuleBlockEntityModifierType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.floatprovider.FloatProviderType
import net.minecraft.util.math.intprovider.IntProviderType
import net.minecraft.util.math.random.Random
import net.minecraft.village.VillagerProfession
import net.minecraft.village.VillagerType
import net.minecraft.world.chunk.ChunkStatus
import net.minecraft.world.event.GameEvent
import net.minecraft.world.event.PositionSourceType
import net.minecraft.world.gen.blockpredicate.BlockPredicateType
import net.minecraft.world.gen.chunk.placement.StructurePlacementType
import net.minecraft.world.gen.feature.size.FeatureSizeType
import net.minecraft.world.gen.foliage.FoliagePlacerType
import net.minecraft.world.gen.heightprovider.HeightProviderType
import net.minecraft.world.gen.placementmodifier.PlacementModifierType
import net.minecraft.world.gen.root.RootPlacerType
import net.minecraft.world.gen.stateprovider.BlockStateProviderType
import net.minecraft.world.gen.structure.StructureType
import net.minecraft.world.gen.treedecorator.TreeDecoratorType
import net.minecraft.world.gen.trunk.TrunkPlacerType
import net.minecraft.world.poi.PointOfInterestType

// iterateInSquare uses the same blockPos object and mutates it, so we need this to use proper collection operations
fun Iterable<BlockPos>.listCopy(): List<BlockPos> = map(BlockPos::mutableCopy)

fun <T> Iterable<T>.draw(random: Random, count: Int = 1): List<T> {
    val pool = this.toMutableList()
    return (0 until count.coerceAtMost(pool.size))
        .map { pool.removeAt(random.nextInt(pool.size)) }
}

fun FabricBlockSettings(init: FabricBlockSettings.() -> Unit): FabricBlockSettings =
    FabricBlockSettings.create().apply(init)
