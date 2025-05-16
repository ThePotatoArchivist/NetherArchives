package archives.tater.netherarchives.block

import archives.tater.netherarchives.get
import archives.tater.netherarchives.set
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.ItemPlacementContext
import net.minecraft.particle.ParticleTypes
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.world.World

class PolishedBasaltGeyserBlock(settings: Settings) : BasaltGeyserBlock(settings) {
    init {
        defaultState = defaultState.with(POWERED, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder)
        builder.add(POWERED)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState =
        super.getPlacementState(ctx).with(POWERED, ctx.world.isReceivingRedstonePower(ctx.blockPos))

    override fun neighborUpdate(
        state: BlockState,
        world: World,
        pos: BlockPos,
        sourceBlock: Block,
        sourcePos: BlockPos,
        notify: Boolean
    ) {
        val powered = world.isReceivingRedstonePower(pos)
        if (state[POWERED] != powered)
            world[pos] = state.with(POWERED, powered)
    }

    override fun getPushDistance(world: World, pos: BlockPos, state: BlockState): Int =
        15 - world.getReceivedRedstonePower(pos)

    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        val facing = world[pos][FACING]
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

    override fun addImportantParticles(world: World, pos: BlockPos, facing: Direction) {
    }

    companion object {
        val POWERED: BooleanProperty = Properties.POWERED
    }
}
