package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.references.BlockItemId
import net.minecraft.resources.Identifier

object NetherArchivesBlockItemIds {
    private fun create(id: Identifier) = BlockItemId.create(id, id)
    private fun create(path: String) = create(NetherArchives.id(path))

    val MAGNETITE = create("magnetite")
    val SMOLDERING_MAGNETITE = create("smoldering_magnetite")
    val BLAZE_DUST = create("blaze_dust")
    val ROTTEN_FLESH_BLOCK = create("rotten_flesh_block")
    val FERMENTED_ROTTEN_FLESH_BLOCK = create("fermented_rotten_flesh_block")
    val BLAZE_TORCH = create("blaze_torch")
    val BASALT_GEYSER = create("basalt_geyser")
    val ADJUSTABLE_BASALT_GEYSER = create("adjustable_basalt_geyser")
    val SPECTREGLASS = create("spectreglass")
    val SHATTERED_SPECTREGLASS = create("shattered_spectreglass")
    val SPECTREGLASS_PANE = create("spectreglass_pane")
    val SHATTERED_SPECTREGLASS_PANE = create("shattered_spectreglass_pane")
}

internal typealias ModBlockItemIds = NetherArchivesBlockItemIds