package archives.tater.netherarchives.block

import archives.tater.netherarchives.util.get
import archives.tater.netherarchives.util.isOf
import archives.tater.netherarchives.util.set
import net.minecraft.core.BlockPos
import net.minecraft.core.BlockPos.betweenClosed
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.ScheduledTickAccess
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.levelgen.PositionalRandomFactory
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class NecroticAshBlock(properties: Properties) : Block(properties) {
    init {
        registerDefaultState(stateDefinition.any().setValue(LIT, false))
    }

    fun shouldCombust(level: ServerLevel, pos: BlockPos): Boolean = !Monster.isDarkEnoughToSpawn(level, pos, FAKE_RANDOM)

    fun tryIgnite(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (state[LIT] || !shouldCombust(level, pos)) return

        level[pos] = state.setValue(LIT, true)
        level.scheduleTick(pos, this, random.nextInt(40, 100))

        for (check in betweenClosed(pos.x - 1, pos.y - 1, pos.z - 1, pos.x + 1, pos.y + 1, pos.z + 1)) {
            if (level[check] isOf this && !level[check][LIT] && !(check isSame pos))
                level.scheduleTick(check, this, random.nextInt(2, 6))
        }
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(LIT)
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape = SHAPE

    override fun updateShape(
        state: BlockState,
        level: LevelReader,
        ticks: ScheduledTickAccess,
        pos: BlockPos,
        directionToNeighbour: Direction,
        neighbourPos: BlockPos,
        neighbourState: BlockState,
        random: RandomSource
    ): BlockState = if (!state.canSurvive(level, pos))
        Blocks.AIR.defaultBlockState()
    else
        super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random)

    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean =
        isFaceFull(level[pos.below()].getCollisionShape(level, pos.below()), Direction.UP)

    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, movedByPiston: Boolean) {
        if (level is ServerLevel && !(oldState isOf this))
            tryIgnite(state, level, pos, level.random)
    }

    override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        tryIgnite(state, level, pos, random)
    }

    override fun animateTick(state: BlockState, level: Level, pos: BlockPos, random: RandomSource) {
        if (!state[LIT]) return

        if (random.nextInt(12) == 0) {
            level.playLocalSound(
                pos,
                SoundEvents.FIRE_AMBIENT,
                SoundSource.BLOCKS,
                1.0f + random.nextFloat(),
                random.nextFloat() * 0.7f + 0.3f,
                false
            )
        }

//        val shape = getShape(state, level, pos, CollisionContext.empty()).bounds()

//        repeat(2) {
//            level.addParticle(
//                ParticleTypes.SMALL_FLAME,
//                pos.x + random.nextFloat() * shape.xsize,
//                pos.y + shape.ysize + 1 / 16f,
//                pos.z + random.nextFloat() * shape.zsize,
//                0.0,
//                1 / 32.0 * random.nextFloat(),
//                0.0
//            )
//        }
    }

    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (!state[LIT]) {
            tryIgnite(state, level, pos, random)
            return
        }

        if (!shouldCombust(level, pos)) {
            level[pos] = state.setValue(LIT, false)
            return
        }

        level[pos] = Blocks.AIR.defaultBlockState()
        val shape = getShape(state, level, pos, CollisionContext.empty()).bounds()
        level.sendParticles(
            ParticleTypes.SMALL_FLAME,
            pos.x + shape.center.x,
            pos.y + shape.center.y,
            pos.z + shape.center.z,
            24,
            shape.xsize / 4,
            shape.ysize / 4,
            shape.zsize / 4,
            0.01
        )
    }

    companion object {
        val LIT = BlockStateProperties.LIT

        val SHAPE = column(16.0, 0.0, 1.0)

        private infix fun BlockPos.isSame(other: BlockPos) = x == other.x && y == other.y && z == other.z

        private val FAKE_RANDOM = object : RandomSource {
            private val self = this
            private val POSITIONAL = object : PositionalRandomFactory {
                override fun fromHashOf(name: String): RandomSource = self
                override fun fromSeed(seed: Long): RandomSource = self
                override fun at(x: Int, y: Int, z: Int): RandomSource = self
                override fun parityConfigString(sb: StringBuilder) {}
            }

            override fun fork(): RandomSource = this
            override fun forkPositional(): PositionalRandomFactory = POSITIONAL
            override fun setSeed(seed: Long) {}
            override fun nextInt(): Int = 0
            override fun nextInt(bound: Int): Int = bound - 1
            override fun nextLong(): Long = 0
            override fun nextBoolean(): Boolean = false
            override fun nextFloat(): Float = 0f
            override fun nextDouble(): Double = 0.0
            override fun nextGaussian(): Double = 0.0

        }
    }
}