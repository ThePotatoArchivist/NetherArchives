package archives.tater.netherarchives.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos

class SoulGlassBlockEntity(pos: BlockPos, state: BlockState) :
    BlockEntity(NetherArchivesBlockEntities.SOUL_GLASS_BLOCK_ENTITY, pos, state)