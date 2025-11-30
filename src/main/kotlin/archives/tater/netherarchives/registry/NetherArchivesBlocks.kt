package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.block.*
import archives.tater.netherarchives.util.BlockSettings
import archives.tater.netherarchives.util.copyLootAndTranslation
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.StainedGlassPaneBlock
import net.minecraft.world.level.material.PushReaction
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.item.DyeColor
import net.minecraft.resources.Identifier
import net.minecraft.world.level.block.state.BlockBehaviour.Properties as BlockSettings

object NetherArchivesBlocks {
    private fun register(id: Identifier, block: (BlockSettings) -> Block = ::Block, settings: BlockSettings = BlockSettings()): Block {
        val key = ResourceKey.create(Registries.BLOCK, id)
        return Registry.register(BuiltInRegistries.BLOCK, key, block(settings.setId(key)))
    }

    private fun register(path: String, block: (BlockSettings) -> Block = ::Block, settings: BlockSettings = BlockSettings()): Block =
        register(NetherArchives.id(path), block, settings)

    private inline fun register(path: String, noinline block: (BlockSettings) -> Block = ::Block, settingsInit: BlockSettings.() -> Unit): Block =
        register(path, block, BlockSettings(settingsInit))

    @JvmField
    val MAGNETITE = register("magnetite", ::MagnetiteBlock) {
        strength(0.8f, 9.0f)
        sound(SoundType.BASALT)
    }

    @JvmField
    val SMOLDERING_MAGNETITE = register("smoldering_magnetite", ::SmolderingMagnetiteBlock) {
        strength(0.6f, 1.25f)
        sound(SoundType.BASALT)
        lightLevel { 3 }
        requiresCorrectToolForDrops()
    }

    @JvmField
    val BLAZE_FIRE = register("blaze_fire", ::BlazeFireBlock) {
        replaceable()
        noCollision()
        instabreak()
        lightLevel { 15 }
        sound(SoundType.WOOL)
        pushReaction(PushReaction.DESTROY)
        noOcclusion()
    }

    @JvmField
    val BLAZE_DUST = register("blaze_dust", ::BlazePowderBlock) {
        replaceable()
        noCollision()
        sound(SoundType.SAND)
        pushReaction(PushReaction.DESTROY)
        noOcclusion()
    }

    @JvmField
    val FERMENTED_ROTTEN_FLESH_BLOCK = register("fermented_rotten_flesh_block") {
        strength(1.5f, 1f)
        sound(SoundType.SLIME_BLOCK)
    }

    @JvmField
    val ROTTEN_FLESH_BLOCK = register("rotten_flesh_block", ::RottenFleshBlock) {
        strength(0.7f, 0.7f)
        sound(SoundType.SLIME_BLOCK)
    }

    @JvmField
    val BLAZE_TORCH = register("blaze_torch", ::BlazeTorchBlock) {
        noCollision()
        instabreak()
        lightLevel { 15 }
        sound(SoundType.BONE_BLOCK)
        pushReaction(PushReaction.DESTROY)
    }

    @JvmField
    val WALL_BLAZE_TORCH = register("wall_blaze_torch", ::WallBlazeTorchBlock) {
        copyLootAndTranslation(BLAZE_TORCH)
        noCollision()
        instabreak()
        lightLevel { 15 }
        sound(SoundType.BONE_BLOCK)
        pushReaction(PushReaction.DESTROY)
    }

    val BASALT_GEYSER = register("basalt_geyser", ::BasaltGeyserBlock) {
        strength(1.2f, 4.2f)
        sound(SoundType.BASALT)
        requiresCorrectToolForDrops()
    }

    val ADJUSTABLE_BASALT_GEYSER = register("adjustable_basalt_geyser", ::AdjustableBasaltGeyserBlock) {
        strength(1.2f, 4.2f)
        sound(SoundType.BASALT)
    }

    val SHATTERED_SPECTREGLASS = register("shattered_spectreglass", ::SoulGlassBlock, BlockSettings.ofFullCopy(Blocks.GLASS))

    val SPECTREGLASS = register("spectreglass", { ShatterableSoulGlassBlock(SHATTERED_SPECTREGLASS, it) },
        BlockSettings.ofFullCopy(SHATTERED_SPECTREGLASS).apply {
            strength(0.3f, 1f)
        }
    )

    val SHATTERED_SPECTREGLASS_PANE = register("shattered_spectreglass_pane", { StainedGlassPaneBlock(DyeColor.BLACK, it) }, BlockSettings.ofFullCopy(
        Blocks.GLASS_PANE))

    val SPECTREGLASS_PANE = register("spectreglass_pane", { ShatterableGlassPaneBlock(SHATTERED_SPECTREGLASS_PANE, it) },
        BlockSettings.ofFullCopy(SHATTERED_SPECTREGLASS_PANE).apply {
            strength(0.3f, 1f)
        }
    )

    fun register() {
        FlammableBlockRegistry.getDefaultInstance().add(ROTTEN_FLESH_BLOCK, 15, 30)
    }

}
