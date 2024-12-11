package archives.tater.netherarchives.block

import archives.tater.netherarchives.FabricBlockSettings
import archives.tater.netherarchives.NetherArchives
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier

object NetherArchivesBlocks {
    private fun register(path: String, block: Block): Block =
        Registry.register(Registries.BLOCK, Identifier(NetherArchives.MOD_ID, path), block)

    @JvmField
    val MAGNETITE = register("magnetite", MagnetiteBlock(
        FabricBlockSettings {
            strength(0.8f, 9.0f)
            sounds(BlockSoundGroup.BASALT)
        }
    ))

    @JvmField
    val SMOLDERING_MAGNETITE = register("smoldering_magnetite", SmolderingMagnetiteBlock(
        FabricBlockSettings {
            strength(0.6f, 1.25f)
            sounds(BlockSoundGroup.BASALT)
            luminance(3)
            emissiveLighting(Blocks::always)
            requiresTool()
        }
    ))

    @JvmField
    val BLAZE_FIRE = register("blaze_fire", BlazeFireBlock(
        FabricBlockSettings {
            replaceable()
            noCollision()
            breakInstantly()
            luminance(15)
            sounds(BlockSoundGroup.WOOL)
            pistonBehavior(PistonBehavior.DESTROY)
            nonOpaque()
        }
    ))

    @JvmField
    val BLAZE_DUST = register("blaze_dust", BlazePowderBlock(
        FabricBlockSettings {
            replaceable()
            noCollision()
            sounds(BlockSoundGroup.SAND)
            pistonBehavior(PistonBehavior.DESTROY)
            nonOpaque()
        }
    ))

    @JvmField
    val FERMENTED_ROTTEN_FLESH_BLOCK = register("fermented_rotten_flesh_block", Block(
        FabricBlockSettings {
            strength(1.5f, 1f)
            sounds(BlockSoundGroup.SLIME)
        }
    ))

    @JvmField
    val ROTTEN_FLESH_BLOCK = register("rotten_flesh_block", RottenFleshBlock(
        FabricBlockSettings {
            strength(0.7f, 0.7f)
            sounds(BlockSoundGroup.SLIME)
        }
    ))

    @JvmField
    val BLAZE_TORCH = register("blaze_torch", BlazeTorchBlock(
        FabricBlockSettings {
            noCollision()
            breakInstantly()
            luminance(15)
            sounds(BlockSoundGroup.BONE)
            pistonBehavior(PistonBehavior.DESTROY)
        }
    ))

    @JvmField
    val WALL_BLAZE_TORCH = register("wall_blaze_torch", WallBlazeTorchBlock(
        FabricBlockSettings {
            noCollision()
            breakInstantly()
            luminance(15)
            sounds(BlockSoundGroup.BONE)
            pistonBehavior(PistonBehavior.DESTROY)
            dropsLike(BLAZE_TORCH)
        }
    ))

    val BASALT_GEYSER = register("basalt_geyser", BasaltGeyserBlock(FabricBlockSettings {
        strength(1.2f, 4.2f)
        luminance(7)
        sounds(BlockSoundGroup.BASALT)
        requiresTool()
    }))

    fun register() {}

}
