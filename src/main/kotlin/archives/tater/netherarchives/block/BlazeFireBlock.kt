package archives.tater.netherarchives.block

import archives.tater.netherarchives.registry.NetherArchivesParticles
import archives.tater.netherarchives.util.isIn
import archives.tater.netherarchives.util.listCopy
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.InsideBlockEffectApplier
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.ScheduledTickAccess
import net.minecraft.world.level.block.BaseFireBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.MultifaceBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.IntegerProperty

class BlazeFireBlock(settings: Properties) : BaseFireBlock(settings, 2.0f) {
    init {
        registerDefaultState(stateDefinition.any().setValue(AGE, 0))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(AGE)
    }

    override fun canBurn(state: BlockState) = true

    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        val blockPos = pos.below()
        return MultifaceBlock.canAttachTo(level, Direction.DOWN, blockPos, level.getBlockState(blockPos))
    }

    override fun codec(): MapCodec<out BaseFireBlock> = CODEC

    override fun updateShape(
        state: BlockState,
        level: LevelReader,
        ticks: ScheduledTickAccess,
        pos: BlockPos,
        directionToNeighbour: Direction,
        neighborPos: BlockPos,
        neighborState: BlockState,
        random: RandomSource
    ): BlockState {
        if (canSurvive(state, level, pos)) {
            return state
        }
        return Blocks.AIR.defaultBlockState()
    }

    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        level.scheduleTick(pos, this, getFireTickDelay(level.random))
        if (!level.canSpreadFireAround(pos)) return

        val blockBelow = level.getBlockState(pos.below())
        val infiniburn = blockBelow isIn level.dimensionType().infiniburn()
        val age = state.getValue(AGE)

        val newAge = (age + random.nextInt(3) / 2).coerceAtMost(15)

        if (age != newAge) {
            val newState = state.setValue(AGE, newAge)
            level.setBlock(pos, newState, UPDATE_INVISIBLE)
        }

        if (!infiniburn && (!canSurvive(state, level, pos) || age > 12)) {
            level.removeBlock(pos, false)
            return
        }

        BlockPos.withinManhattan(pos, 1, 1, 1)
            .listCopy()
            .filter { level.getBlockState(it).block is BlazePowderBlock }
            .also {
                if (it.isEmpty()) return
                level.setBlockAndUpdate(it[random.nextInt(it.size)], this.defaultBlockState())
            }
    }

    override fun onPlace(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        oldState: BlockState,
        notify: Boolean
    ) {
        super.onPlace(state, level, pos, oldState, notify)
        if (oldState.block !is BlazeFireBlock) {
            level.playSound(
                null,
                pos,
                SoundEvents.FIRECHARGE_USE,
                SoundSource.NEUTRAL,
                1.0f,
                0.4f + 0.4f * level.random.nextFloat()
            )
        }
        level.scheduleTick(pos, this, level.random.nextInt(10))
    }

    override fun entityInside(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        entity: Entity,
        affectsApplier: InsideBlockEffectApplier,
        isPrecise: Boolean
    ) {
        if (entity is ItemEntity) return
        super.entityInside(state, level, pos, entity, affectsApplier, isPrecise)
    }

    override fun animateTick(state: BlockState, level: Level, pos: BlockPos, random: RandomSource) {
        if (random.nextInt(24) == 0) {
            level.playLocalSound(
                pos.x + 0.5,
                pos.y + 0.5,
                pos.z + 0.5,
                SoundEvents.FIRE_AMBIENT,
                SoundSource.BLOCKS,
                1.0f + random.nextFloat(),
                random.nextFloat() * 0.7f + 0.3f,
                false
            )
        }
        repeat(random.nextInt(2) + 2) {
            level.addParticle(
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
        val AGE: IntegerProperty = BlockStateProperties.AGE_15

        private fun getFireTickDelay(random: RandomSource) = 20 + random.nextInt(20)

        val CODEC: MapCodec<BlazeFireBlock> = simpleCodec(::BlazeFireBlock)
    }
}
