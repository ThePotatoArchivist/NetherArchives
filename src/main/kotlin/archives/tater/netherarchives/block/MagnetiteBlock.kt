package archives.tater.netherarchives.block

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.registry.NetherArchivesBlocks
import archives.tater.netherarchives.registry.NetherArchivesTags
import com.mojang.serialization.MapCodec
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.FallingBlock
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.tags.FluidTags
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.ScheduledTickAccess

class MagnetiteBlock(settings: Properties) : FallingBlock(settings.randomTicks()) {
    override fun getStateForPlacement(ctx: BlockPlaceContext): BlockState {
        return updateDistanceFromLodestone(defaultBlockState(), ctx.level, ctx.clickedPos)
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
        builder.add(DISTANCE)
    }

    override fun isRandomlyTicking(state: BlockState?) = true

    override fun randomTick(state: BlockState?, world: ServerLevel, pos: BlockPos, random: RandomSource?) {
        if (Direction.entries.any {
                world.getFluidState(pos.relative(it)).`is`(FluidTags.LAVA)
            }) {
            world.setBlockAndUpdate(pos, NetherArchivesBlocks.SMOLDERING_MAGNETITE.defaultBlockState())
        }
    }

    override fun tick(state: BlockState, world: ServerLevel, pos: BlockPos, random: RandomSource?) {
        world.setBlockAndUpdate(pos, updateDistanceFromLodestone(state, world, pos))
        if (state.getValue(DISTANCE) == 7) {
            super.tick(state, world, pos, random)
        }
    }

    override fun getDustColor(state: BlockState?, world: BlockGetter?, pos: BlockPos?): Int = 0x25252E

    override fun codec(): MapCodec<out FallingBlock> = CODEC

    override fun updateShape(
        state: BlockState,
        world: LevelReader,
        tickView: ScheduledTickAccess,
        pos: BlockPos,
        direction: Direction,
        neighborPos: BlockPos,
        neighborState: BlockState,
        random: RandomSource
    ): BlockState {
        if ((getDistanceFromLodestone(neighborState)) != 0 || state.getValue(DISTANCE) != 0) {
            tickView.scheduleTick(pos, this, 1)
        }
        return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random)
    }

    companion object {
        val CODEC: MapCodec<MagnetiteBlock> = simpleCodec(::MagnetiteBlock)

        val DISTANCE: IntegerProperty = BlockStateProperties.DISTANCE

        private fun getDistanceFromLodestone(state: BlockState): Int {
            if (state.`is`(NetherArchivesTags.MAGNETIC)) return 0
            if (state.block == NetherArchivesBlocks.MAGNETITE) return state.getValue(DISTANCE)
            return 7
        }

        private fun updateDistanceFromLodestone(state: BlockState, world: LevelAccessor, pos: BlockPos): BlockState {
            val minDistance = Direction.entries.minOf { getDistanceFromLodestone(world.getBlockState(pos.relative(it))) }
            NetherArchives.logger.debug("distance: $minDistance")
            return state.setValue(DISTANCE, if (minDistance < 7) minDistance + 1 else 7)
        }
    }

}
