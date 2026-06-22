package archives.tater.netherarchives.registry

import archives.tater.netherarchives.block.*
import archives.tater.netherarchives.util.copyLootAndTranslation
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.references.BlockItemId
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.StainedGlassPaneBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.PushReaction

object NetherArchivesBlocks {
    private fun register(id: ResourceKey<Block>, block: (BlockBehaviour.Properties) -> Block = ::Block, properties: BlockBehaviour.Properties = BlockBehaviour.Properties.of()) =
        Registry.register(BuiltInRegistries.BLOCK, id, block(properties.setId(id)))

    private fun register(id: BlockItemId, block: (BlockBehaviour.Properties) -> Block = ::Block, properties: BlockBehaviour.Properties = BlockBehaviour.Properties.of()) =
        register(id.block, block, properties)

    private inline fun register(id: ResourceKey<Block>, noinline block: (BlockBehaviour.Properties) -> Block = ::Block, properties: BlockBehaviour.Properties.() -> Unit) =
        register(id, block, BlockBehaviour.Properties.of().apply(properties))

    private inline fun register(id: BlockItemId, noinline block: (BlockBehaviour.Properties) -> Block = ::Block, properties: BlockBehaviour.Properties.() -> Unit) =
        register(id.block, block, properties)

    @JvmField
    val MAGNETITE = register(ModBlockItemIds.MAGNETITE, ::MagnetiteBlock) {
        strength(0.8f, 9.0f)
        sound(SoundType.BASALT)
    }

    @JvmField
    val SMOLDERING_MAGNETITE = register(ModBlockItemIds.SMOLDERING_MAGNETITE, ::SmolderingMagnetiteBlock) {
        strength(0.6f, 1.25f)
        sound(SoundType.BASALT)
        lightLevel { 3 }
        requiresCorrectToolForDrops()
    }

    @JvmField
    val BLAZE_FIRE = register(ModBlockIds.BLAZE_FIRE, ::BlazeFireBlock) {
        replaceable()
        noCollision()
        instabreak()
        lightLevel { 15 }
        sound(SoundType.WOOL)
        pushReaction(PushReaction.DESTROY)
        noOcclusion()
    }

    @JvmField
    val BLAZE_DUST = register(ModBlockItemIds.BLAZE_DUST, ::BlazePowderBlock) {
        replaceable()
        noCollision()
        sound(SoundType.SAND)
        pushReaction(PushReaction.DESTROY)
        noOcclusion()
    }

    @JvmField
    val FERMENTED_ROTTEN_FLESH_BLOCK = register(ModBlockItemIds.FERMENTED_ROTTEN_FLESH_BLOCK) {
        strength(1.5f, 1f)
        sound(SoundType.CORAL_BLOCK)
    }

    @JvmField
    val ROTTEN_FLESH_BLOCK = register(ModBlockItemIds.ROTTEN_FLESH_BLOCK, ::RottenFleshBlock) {
        strength(0.7f, 0.7f)
        sound(SoundType.CORAL_BLOCK)
    }

    @JvmField
    val BLAZE_TORCH = register(ModBlockItemIds.BLAZE_TORCH, ::BlazeTorchBlock) {
        noCollision()
        instabreak()
        lightLevel { 15 }
        sound(SoundType.BONE_BLOCK)
        pushReaction(PushReaction.DESTROY)
    }

    @JvmField
    val WALL_BLAZE_TORCH = register(ModBlockIds.WALL_BLAZE_TORCH, ::WallBlazeTorchBlock) {
        copyLootAndTranslation(BLAZE_TORCH)
        noCollision()
        instabreak()
        lightLevel { 15 }
        sound(SoundType.BONE_BLOCK)
        pushReaction(PushReaction.DESTROY)
    }

    @JvmField
    val BASALT_GEYSER = register(ModBlockItemIds.BASALT_GEYSER, ::BasaltGeyserBlock) {
        strength(1.2f, 4.2f)
        sound(SoundType.BASALT)
        requiresCorrectToolForDrops()
    }

    @JvmField
    val ADJUSTABLE_BASALT_GEYSER = register(ModBlockItemIds.ADJUSTABLE_BASALT_GEYSER, ::AdjustableBasaltGeyserBlock) {
        strength(1.2f, 4.2f)
        sound(SoundType.BASALT)
    }

    @JvmField
    val SHATTERED_SPECTREGLASS = register(ModBlockItemIds.SHATTERED_SPECTREGLASS, ::SoulGlassBlock, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS))

    @JvmField
    val SPECTREGLASS = register(ModBlockItemIds.SPECTREGLASS, { ShatterableSoulGlassBlock(SHATTERED_SPECTREGLASS, it) },
        BlockBehaviour.Properties.ofFullCopy(SHATTERED_SPECTREGLASS).apply {
            strength(0.3f, 1f)
        }
    )

    @JvmField
    val SHATTERED_SPECTREGLASS_PANE = register(ModBlockItemIds.SHATTERED_SPECTREGLASS_PANE, { StainedGlassPaneBlock(DyeColor.BLACK, it) }, BlockBehaviour.Properties.ofFullCopy(
        Blocks.GLASS_PANE))

    @JvmField
    val SPECTREGLASS_PANE = register(ModBlockItemIds.SPECTREGLASS_PANE, { ShatterableGlassPaneBlock(SHATTERED_SPECTREGLASS_PANE, it) },
        BlockBehaviour.Properties.ofFullCopy(SHATTERED_SPECTREGLASS_PANE).apply {
            strength(0.3f, 1f)
        }
    )

    fun init() {
        FlammableBlockRegistry.getDefaultInstance().add(ROTTEN_FLESH_BLOCK, 15, 30)
    }

}

internal typealias ModBlocks = NetherArchivesBlocks