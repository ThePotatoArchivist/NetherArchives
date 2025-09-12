package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.block.*
import archives.tater.netherarchives.util.BlockSettings
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.StainedGlassPaneBlock
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.DyeColor
import net.minecraft.util.Identifier
import net.minecraft.block.AbstractBlock.Settings as BlockSettings

object NetherArchivesBlocks {
    private fun register(id: Identifier, block: (BlockSettings) -> Block = ::Block, settings: BlockSettings = BlockSettings()): Block =
        Registry.register(Registries.BLOCK, id, block(settings))

    private fun register(path: String, block: (BlockSettings) -> Block = ::Block, settings: BlockSettings = BlockSettings()): Block =
        register(NetherArchives.id(path), block, settings)

    private inline fun register(path: String, noinline block: (BlockSettings) -> Block = ::Block, settingsInit: BlockSettings.() -> Unit): Block =
        register(path, block, BlockSettings(settingsInit))

    @JvmField
    val MAGNETITE = register("magnetite", ::MagnetiteBlock) {
        strength(0.8f, 9.0f)
        sounds(BlockSoundGroup.BASALT)
    }

    @JvmField
    val SMOLDERING_MAGNETITE = register("smoldering_magnetite", ::SmolderingMagnetiteBlock) {
        strength(0.6f, 1.25f)
        sounds(BlockSoundGroup.BASALT)
        luminance { 3 }
        emissiveLighting(Blocks::always)
        requiresTool()
    }

    @JvmField
    val BLAZE_FIRE = register("blaze_fire", ::BlazeFireBlock) {
        replaceable()
        noCollision()
        breakInstantly()
        luminance { 15 }
        sounds(BlockSoundGroup.WOOL)
        pistonBehavior(PistonBehavior.DESTROY)
        nonOpaque()
    }

    @JvmField
    val BLAZE_DUST = register("blaze_dust", ::BlazePowderBlock) {
        replaceable()
        noCollision()
        sounds(BlockSoundGroup.SAND)
        pistonBehavior(PistonBehavior.DESTROY)
        nonOpaque()
    }

    @JvmField
    val FERMENTED_ROTTEN_FLESH_BLOCK = register("fermented_rotten_flesh_block") {
        strength(1.5f, 1f)
        sounds(BlockSoundGroup.SLIME)
    }

    @JvmField
    val ROTTEN_FLESH_BLOCK = register("rotten_flesh_block", ::RottenFleshBlock) {
        strength(0.7f, 0.7f)
        sounds(BlockSoundGroup.SLIME)
    }

    @JvmField
    val BLAZE_TORCH = register("blaze_torch", ::BlazeTorchBlock) {
        noCollision()
        breakInstantly()
        luminance { 15 }
        sounds(BlockSoundGroup.BONE)
        pistonBehavior(PistonBehavior.DESTROY)
    }

    @JvmField
    val WALL_BLAZE_TORCH = register("wall_blaze_torch", ::WallBlazeTorchBlock) {
        noCollision()
        breakInstantly()
        luminance { 15 }
        sounds(BlockSoundGroup.BONE)
        pistonBehavior(PistonBehavior.DESTROY)
        dropsLike(BLAZE_TORCH)
    }

    val BASALT_GEYSER = register("basalt_geyser", ::BasaltGeyserBlock) {
        strength(1.2f, 4.2f)
        luminance { 5 }
        sounds(BlockSoundGroup.BASALT)
        requiresTool()
    }

    val ADJUSTABLE_BASALT_GEYSER = register("adjustable_basalt_geyser", ::AdjustableBasaltGeyserBlock) {
        strength(1.2f, 4.2f)
        luminance { 5 }
        sounds(BlockSoundGroup.BASALT)
    }

    val SHATTERED_SPECTREGLASS = register("shattered_spectreglass", ::SoulGlassBlock, BlockSettings.copy(Blocks.GLASS))

    val SPECTREGLASS = register("spectreglass", { ShatterableSoulGlassBlock(SHATTERED_SPECTREGLASS, it) },
        BlockSettings.copy(SHATTERED_SPECTREGLASS).apply {
            strength(0.3f, 3f)
        }
    )

    val SHATTERED_SPECTREGLASS_PANE = register("shattered_spectreglass_pane", { StainedGlassPaneBlock(DyeColor.BLACK, it) }, BlockSettings.copy(Blocks.GLASS_PANE))

    val SPECTREGLASS_PANE = register("spectreglass_pane", { ShatterableGlassPaneBlock(SHATTERED_SPECTREGLASS_PANE, it) },
        BlockSettings.copy(SHATTERED_SPECTREGLASS_PANE).apply {
            strength(0.3f, 3f)
        }
    )

    fun register() {
        FlammableBlockRegistry.getDefaultInstance().add(ROTTEN_FLESH_BLOCK, 15, 30)
    }

}
