package archives.tater.netherarchives.block

import archives.tater.netherarchives.util.get
import archives.tater.netherarchives.util.set
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.redstone.Orientation

class AdjustableBasaltGeyserBlock(settings: Properties) : BasaltGeyserBlock(settings) {
    init {
        registerDefaultState(defaultBlockState().setValue(POWERED, false))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(builder)
        builder.add(POWERED)
    }

    override fun getStateForPlacement(ctx: BlockPlaceContext): BlockState =
        super.getStateForPlacement(ctx).setValue(POWERED, ctx.level.hasNeighborSignal(ctx.clickedPos))

    override fun neighborChanged(
        state: BlockState,
        world: Level,
        pos: BlockPos,
        sourceBlock: Block,
        wireOrientation: Orientation?,
        notify: Boolean
    ) {
        val powered = world.hasNeighborSignal(pos)
        if (state.getValue(POWERED) != powered)
            world[pos] = state.setValue(POWERED, powered)
    }

    override fun getPushDistance(world: Level, pos: BlockPos, state: BlockState): Int =
        15 - world.getBestNeighborSignal(pos)

    override fun animateTick(state: BlockState, world: Level, pos: BlockPos, random: RandomSource) {
        val facing = world[pos].getValue(FACING)
        val distance = getPushDistance(world, pos, state)
        if (distance <= 0) return
        repeat(2) {
            world.addFaceParticle(
                ParticleTypes.SMOKE,
                facing,
                pos,
                (distance / 4.0) * (0.15 + 0.15 * random.nextDouble()),
                posSpread = 0.3,
            )
        }
    }

    override fun addImportantParticles(world: Level, pos: BlockPos, facing: Direction) {
    }

    companion object {
        val POWERED: BooleanProperty = BlockStateProperties.POWERED
    }
}
