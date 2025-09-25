package archives.tater.netherarchives.block

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.registry.NetherArchivesBlocks
import archives.tater.netherarchives.registry.NetherArchivesTags
import com.mojang.serialization.MapCodec
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.FallingBlock
import net.minecraft.item.ItemPlacementContext
import net.minecraft.registry.tag.FluidTags
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import net.minecraft.world.tick.ScheduledTickView

class MagnetiteBlock(settings: Settings) : FallingBlock(settings.ticksRandomly()) {
    override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
        return updateDistanceFromLodestone(defaultState, ctx.world, ctx.blockPos)
    }

    override fun appendProperties(builder: StateManager.Builder<Block?, BlockState?>) {
        builder.add(DISTANCE)
    }

    override fun hasRandomTicks(state: BlockState?) = true

    override fun randomTick(state: BlockState?, world: ServerWorld, pos: BlockPos, random: Random?) {
        if (Direction.entries.any {
                world.getFluidState(pos.offset(it)).isIn(FluidTags.LAVA)
            }) {
            world.setBlockState(pos, NetherArchivesBlocks.SMOLDERING_MAGNETITE.defaultState)
        }
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random?) {
        world.setBlockState(pos, updateDistanceFromLodestone(state, world, pos))
        if (state.get(DISTANCE) == 7) {
            super.scheduledTick(state, world, pos, random)
        }
    }

    override fun getColor(state: BlockState?, world: BlockView?, pos: BlockPos?): Int = 0x25252E

    override fun getCodec(): MapCodec<out FallingBlock> = CODEC

    override fun getStateForNeighborUpdate(
        state: BlockState,
        world: WorldView,
        tickView: ScheduledTickView,
        pos: BlockPos,
        direction: Direction,
        neighborPos: BlockPos,
        neighborState: BlockState,
        random: Random
    ): BlockState {
        if ((getDistanceFromLodestone(neighborState)) != 0 || state.get(DISTANCE) != 0) {
            tickView.scheduleBlockTick(pos, this, 1)
        }
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random)
    }

    companion object {
        val CODEC: MapCodec<MagnetiteBlock> = createCodec(::MagnetiteBlock)

        val DISTANCE: IntProperty = Properties.DISTANCE_1_7

        private fun getDistanceFromLodestone(state: BlockState): Int {
            if (state.isIn(NetherArchivesTags.MAGNETIC)) return 0
            if (state.block == NetherArchivesBlocks.MAGNETITE) return state.get(DISTANCE)
            return 7
        }

        private fun updateDistanceFromLodestone(state: BlockState, world: WorldAccess, pos: BlockPos): BlockState {
            val minDistance = Direction.entries.minOf { getDistanceFromLodestone(world.getBlockState(pos.offset(it))) }
            NetherArchives.logger.debug("distance: $minDistance")
            return state.with(DISTANCE, if (minDistance < 7) minDistance + 1 else 7)
        }
    }

}
