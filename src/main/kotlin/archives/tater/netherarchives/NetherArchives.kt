package archives.tater.netherarchives

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.FallingBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.ShovelItem
import net.minecraft.item.ToolItem
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

object NetherArchives : ModInitializer {
    private val logger = LoggerFactory.getLogger("nether-archives")

	val MAGNETITE: Block = Registry.register(Registries.BLOCK, Identifier("netherarchives", "magnetite"), FallingBlock(
		FabricBlockSettings.create()
			.strength(4.0f)
			.sounds(BlockSoundGroup.NETHERITE)
	))
	val MAGNETITE_ITEM: BlockItem = Registry.register(Registries.ITEM, Identifier("netherarchives", "magnetite"), BlockItem(MAGNETITE, FabricItemSettings()))

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")
	}

}