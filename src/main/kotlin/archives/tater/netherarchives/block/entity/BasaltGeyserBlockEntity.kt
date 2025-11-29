package archives.tater.netherarchives.block.entity

import archives.tater.netherarchives.registry.NetherArchivesBlockEntities
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.core.BlockPos

class BasaltGeyserBlockEntity(pos: BlockPos, state: BlockState) :
    BlockEntity(NetherArchivesBlockEntities.BASALT_GEYSER_ENTITY, pos, state)
