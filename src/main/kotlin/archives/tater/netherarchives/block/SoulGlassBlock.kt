package archives.tater.netherarchives.block

import archives.tater.netherarchives.block.entity.SoulGlassBlockEntity
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.TransparentBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos

class SoulGlassBlock(settings: Settings) : TransparentBlock(settings), BlockEntityProvider {
    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = SoulGlassBlockEntity(pos, state)
}