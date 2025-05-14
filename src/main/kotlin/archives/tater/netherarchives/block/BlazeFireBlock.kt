package archives.tater.netherarchives.block

import archives.tater.netherarchives.listCopy
import archives.tater.netherarchives.registry.NetherArchivesParticles
import com.mojang.serialization.MapCodec
import net.minecraft.block.AbstractFireBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.entity.Entity
import net.minecraft.entity.ItemEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.world.GameRules
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView

class BlazeFireBlock(settings: Settings) : AbstractFireBlock(settings, 2.0f) {
    init {
        defaultState = stateManager.defaultState.with(AGE, 0)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(AGE)
    }

    override fun isFlammable(state: BlockState?) = true

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        val blockPos = pos.down()
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, Direction.UP)
    }

    override fun getCodec(): MapCodec<out AbstractFireBlock> = CODEC

    override fun getStateForNeighborUpdate(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        world: WorldAccess,
        pos: BlockPos,
        neighborPos: BlockPos
    ): BlockState {
        if (canPlaceAt(state, world, pos)) {
            return state
        }
        return Blocks.AIR.defaultState
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        world.scheduleBlockTick(pos, this, getFireTickDelay(world.random))
        if (!world.gameRules.getBoolean(GameRules.DO_FIRE_TICK)) return

        val blockBelow = world.getBlockState(pos.down())
        val infiniburn = blockBelow.isIn(world.dimension.infiniburn())
        val age = state.get(AGE)

        val newAge = (age + random.nextInt(3) / 2).coerceAtMost(15)

        if (age != newAge) {
            val newState = state.with(AGE, newAge)
            world.setBlockState(pos, newState, Block.NO_REDRAW)
        }

        if (!infiniburn && (!canPlaceAt(state, world, pos) || age > 12)) {
            world.removeBlock(pos, false)
            return
        }

        BlockPos.iterateOutwards(pos, 1, 1, 1)
            .listCopy()
            .filter { world.getBlockState(it).block is BlazePowderBlock }
            .also {
                if (it.isEmpty()) return
                world.setBlockState(it[random.nextInt(it.size)], this.defaultState)
            }
    }

    override fun onBlockAdded(
        state: BlockState,
        world: World,
        pos: BlockPos,
        oldState: BlockState,
        notify: Boolean
    ) {
        super.onBlockAdded(state, world, pos, oldState, notify)
        if (oldState.block !is BlazeFireBlock) {
            world.playSound(
                null,
                pos,
                SoundEvents.ITEM_FIRECHARGE_USE,
                SoundCategory.NEUTRAL,
                1.0f,
                0.4f + 0.4f * world.random.nextFloat()
            )
        }
        world.scheduleBlockTick(pos, this, world.random.nextInt(10))
    }

    override fun onEntityCollision(state: BlockState?, world: World?, pos: BlockPos?, entity: Entity?) {
        if (entity is ItemEntity) return
        super.onEntityCollision(state, world, pos, entity)
    }

    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        if (random.nextInt(24) == 0) {
            world.playSound(
                pos.x + 0.5,
                pos.y + 0.5,
                pos.z + 0.5,
                SoundEvents.BLOCK_FIRE_AMBIENT,
                SoundCategory.BLOCKS,
                1.0f + random.nextFloat(),
                random.nextFloat() * 0.7f + 0.3f,
                false
            )
        }
        repeat(random.nextInt(2) + 2) {
            world.addParticle(
                NetherArchivesParticles.BLAZE_SPARK,
                pos.x + random.nextDouble(),
                pos.y + 0.25 + 0.5 * random.nextDouble(),
                pos.z + random.nextDouble(),
                0.0,
                0.0,
                0.0
            )
        }
    }

    companion object {
        val AGE: IntProperty = Properties.AGE_15

        private fun getFireTickDelay(random: Random) = 20 + random.nextInt(20)

        val CODEC: MapCodec<BlazeFireBlock> = createCodec(::BlazeFireBlock)
    }
}
