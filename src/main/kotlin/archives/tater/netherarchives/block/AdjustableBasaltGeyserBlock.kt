package archives.tater.netherarchives.block

import archives.tater.netherarchives.block.entity.BasaltGeyserBlockEntity
import archives.tater.netherarchives.util.get
import archives.tater.netherarchives.util.set
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.util.RandomSource
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
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
        level: Level,
        pos: BlockPos,
        sourceBlock: Block,
        wireOrientation: Orientation?,
        notify: Boolean
    ) {
        val powered = level.hasNeighborSignal(pos)
        if (state.getValue(POWERED) != powered)
            level[pos] = state.setValue(POWERED, powered)
    }

    override fun getPushDistance(level: Level, pos: BlockPos, state: BlockState): Int =
        15 - level.getBestNeighborSignal(pos)

    override fun animateTick(state: BlockState, level: Level, pos: BlockPos, random: RandomSource) {
        val blockEntity = level.getBlockEntity(pos) as? BasaltGeyserBlockEntity ?: return
        val facing = level[pos].getValue(FACING)
        val distance = blockEntity.pushDistance
        if (distance <= 0) return
        repeat(2) {
            level.addFaceParticle(
                ParticleTypes.SMOKE,
                facing,
                pos,
                (distance / 4.0) * (0.15 + 0.15 * random.nextDouble()),
                posSpread = 0.3,
            )
        }
    }

    override fun addImportantParticles(level: Level, pos: BlockPos, facing: Direction) {
    }

    companion object {
        val POWERED: BooleanProperty = BlockStateProperties.POWERED
    }
}
