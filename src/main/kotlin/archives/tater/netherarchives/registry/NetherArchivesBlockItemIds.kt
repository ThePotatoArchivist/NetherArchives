package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.references.BlockItemId
import net.minecraft.resources.Identifier

object NetherArchivesBlockItemIds {
    private fun create(id: Identifier) = BlockItemId.create(id, id)
    private fun create(path: String) = create(NetherArchives.id(path))

    @JvmField val MAGNETITE = create("magnetite")
    @JvmField val SMOLDERING_MAGNETITE = create("smoldering_magnetite")
    @JvmField val BLAZE_DUST = create("blaze_dust")
    @JvmField val ROTTEN_FLESH_BLOCK = create("rotten_flesh_block")
    @JvmField val FERMENTED_ROTTEN_FLESH_BLOCK = create("fermented_rotten_flesh_block")
    @JvmField val BLAZE_TORCH = create("blaze_torch")
    @JvmField val BASALT_GEYSER = create("basalt_geyser")
    @JvmField val ADJUSTABLE_BASALT_GEYSER = create("adjustable_basalt_geyser")
    @JvmField val SPECTREGLASS = create("spectreglass")
    @JvmField val SHATTERED_SPECTREGLASS = create("shattered_spectreglass")
    @JvmField val SPECTREGLASS_PANE = create("spectreglass_pane")
    @JvmField val SHATTERED_SPECTREGLASS_PANE = create("shattered_spectreglass_pane")
}
