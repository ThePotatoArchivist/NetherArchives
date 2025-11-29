package archives.tater.netherarchives.block.entity

import archives.tater.netherarchives.block.BasaltGeyserBlock
import archives.tater.netherarchives.registry.NetherArchivesBlockEntities
import archives.tater.netherarchives.util.get
import archives.tater.netherarchives.util.iterateLinearBlockPos
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING

class BasaltGeyserBlockEntity(pos: BlockPos, state: BlockState) :
    BlockEntity(NetherArchivesBlockEntities.BASALT_GEYSER_ENTITY, pos, state) {

    var pushDistance = 0
        private set
    var distance = 0
        private set

    private var updateTicks = 0

    fun updateDistance(world: Level, pos: BlockPos, state: BlockState) {
        if (updateTicks > 0) {
            updateTicks--
            return
        }

        updateTicks = UPDATE_FREQUENCY

        val geyserBlock = world[pos].block as? BasaltGeyserBlock ?: return
        pushDistance = geyserBlock.getPushDistance(world, pos, state)
        if (pushDistance == 0) {
            distance = 0
            return
        }
        val facing = state.getValue(FACING)

        distance = iterateLinearBlockPos(pos, facing, pushDistance)
            .indexOfFirst { world[it].run { isFaceSturdy(world, it, facing) or isFaceSturdy(world, it, facing.opposite) } }
            .let { if (it == -1) pushDistance else it }
    }

    companion object {
        const val UPDATE_FREQUENCY = 10
    }
}
