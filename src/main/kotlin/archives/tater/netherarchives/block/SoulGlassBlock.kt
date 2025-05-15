package archives.tater.netherarchives.block

import net.minecraft.block.BlockState
import net.minecraft.block.Stainable
import net.minecraft.block.TransparentBlock
import net.minecraft.util.DyeColor
import net.minecraft.util.math.Direction

open class SoulGlassBlock(settings: Settings) : TransparentBlock(settings), Stainable {
    override fun isSideInvisible(state: BlockState, stateFrom: BlockState, direction: Direction): Boolean =
        stateFrom.block is SoulGlassBlock

    override fun getColor(): DyeColor = DyeColor.BLACK // unused elsewhere
}
