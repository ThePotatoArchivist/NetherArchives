package archives.tater.netherarchives.block

import archives.tater.netherarchives.util.listCopy
import archives.tater.netherarchives.registry.NetherArchivesParticles
import com.mojang.serialization.MapCodec
import net.minecraft.world.level.block.BaseFireBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundSource
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.RandomSource
import net.minecraft.world.level.GameRules
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader

class BlazeFireBlock(settings: Properties) : BaseFireBlock(settings, 2.0f) {
    init {
        registerDefaultState(stateDefinition.any().setValue(AGE, 0))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(AGE)
    }

    override fun canBurn(state: BlockState?) = true

    override fun canSurvive(state: BlockState, world: LevelReader, pos: BlockPos): Boolean {
        val blockPos = pos.below()
        return world.getBlockState(blockPos).isFaceSturdy(world, blockPos, Direction.UP)
    }

    override fun codec(): MapCodec<out BaseFireBlock> = CODEC

    override fun updateShape(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        world: LevelAccessor,
        pos: BlockPos,
        neighborPos: BlockPos
    ): BlockState {
        if (canSurvive(state, world, pos)) {
            return state
        }
        return Blocks.AIR.defaultBlockState()
    }

    override fun tick(state: BlockState, world: ServerLevel, pos: BlockPos, random: RandomSource) {
        world.scheduleTick(pos, this, getFireTickDelay(world.random))
        if (!world.gameRules.getBoolean(GameRules.RULE_DOFIRETICK)) return

        val blockBelow = world.getBlockState(pos.below())
        val infiniburn = blockBelow.`is`(world.dimensionType().infiniburn())
        val age = state.getValue(AGE)

        val newAge = (age + random.nextInt(3) / 2).coerceAtMost(15)

        if (age != newAge) {
            val newState = state.setValue(AGE, newAge)
            world.setBlock(pos, newState, Block.UPDATE_INVISIBLE)
        }

        if (!infiniburn && (!canSurvive(state, world, pos) || age > 12)) {
            world.removeBlock(pos, false)
            return
        }

        BlockPos.withinManhattan(pos, 1, 1, 1)
            .listCopy()
            .filter { world.getBlockState(it).block is BlazePowderBlock }
            .also {
                if (it.isEmpty()) return
                world.setBlockAndUpdate(it[random.nextInt(it.size)], this.defaultBlockState())
            }
    }

    override fun onPlace(
        state: BlockState,
        world: Level,
        pos: BlockPos,
        oldState: BlockState,
        notify: Boolean
    ) {
        super.onPlace(state, world, pos, oldState, notify)
        if (oldState.block !is BlazeFireBlock) {
            world.playSound(
                null,
                pos,
                SoundEvents.FIRECHARGE_USE,
                SoundSource.NEUTRAL,
                1.0f,
                0.4f + 0.4f * world.random.nextFloat()
            )
        }
        world.scheduleTick(pos, this, world.random.nextInt(10))
    }

    override fun entityInside(state: BlockState?, world: Level?, pos: BlockPos?, entity: Entity?) {
        if (entity is ItemEntity) return
        super.entityInside(state, world, pos, entity)
    }

    override fun animateTick(state: BlockState, world: Level, pos: BlockPos, random: RandomSource) {
        if (random.nextInt(24) == 0) {
            world.playLocalSound(
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
        val AGE: IntegerProperty = BlockStateProperties.AGE_15

        private fun getFireTickDelay(random: RandomSource) = 20 + random.nextInt(20)

        val CODEC: MapCodec<BlazeFireBlock> = simpleCodec(::BlazeFireBlock)
    }
}
