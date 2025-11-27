package archives.tater.netherarchives.block

import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.BeaconBeamBlock
import net.minecraft.world.level.block.TransparentBlock
import net.minecraft.world.item.DyeColor
import net.minecraft.core.Direction

open class SoulGlassBlock(settings: Properties) : TransparentBlock(settings), BeaconBeamBlock {
    override fun skipRendering(state: BlockState, stateFrom: BlockState, direction: Direction): Boolean =
        stateFrom.block is SoulGlassBlock

    override fun getColor(): DyeColor = DyeColor.BLACK // unused elsewhere
}
