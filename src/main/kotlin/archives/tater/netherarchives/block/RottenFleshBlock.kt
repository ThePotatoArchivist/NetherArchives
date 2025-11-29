package archives.tater.netherarchives.block

import archives.tater.netherarchives.registry.NetherArchivesBlocks
import archives.tater.netherarchives.registry.NetherArchivesTags
import archives.tater.netherarchives.util.get
import archives.tater.netherarchives.util.listCopy
import archives.tater.netherarchives.util.set
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level

class RottenFleshBlock(settings: Properties) : Block(settings.randomTicks()) {
    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(AGE, 0)
                .setValue(FERMENTING, false)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(AGE, FERMENTING)
    }

    override fun isRandomlyTicking(state: BlockState) = true

    private fun findCampfireDistance(world: Level, pos: BlockPos): Int {
        // [Iterable.find] stops iterating when it finds the block so the object should still be on the same value
        val campfire = BlockPos.betweenClosed(pos, pos.below(15)).find {
            world.getBlockState(it).`is`(NetherArchivesTags.ROTTEN_FLESH_FERMENTER)
        }
        if (campfire == null) return Integer.MAX_VALUE

        val states = BlockPos.betweenClosed(pos, campfire).listCopy().run {
            subList(1, size - 1) // Ignore top and bottom block
        }.map(world::getBlockState)

        if (!states.all {
            !it.canOcclude() || it.`is`(this) || it.`is`(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK)
        }) return Integer.MAX_VALUE

        val distance = states.count { it.`is`(this) }

        return distance
    }

    override fun randomTick(state: BlockState, world: ServerLevel, pos: BlockPos, random: RandomSource) {
        val distance = findCampfireDistance(world, pos)

        val fermenting = distance <= 3

        val updatedState = if (fermenting == state.getValue(FERMENTING)) state else state.setValue(FERMENTING, fermenting)

        val finalState = when {
            /*
            0 blocks between - 30% chance
            1 block  between - 25% chance
            2 blocks between - 20% chance
            3 blocks between - 15% chance
             */
            !fermenting || random.nextFloat() > 0.3 - 0.05 * distance -> updatedState
            state.getValue(AGE) >= 3 -> NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK.defaultBlockState()
            else -> updatedState.setValue(AGE, state.getValue(AGE) + 1)
        }

        if (finalState != state)
            world[pos] = finalState
    }

    override fun animateTick(state: BlockState, world: Level, pos: BlockPos, random: RandomSource) {
        if (!state.getValue(FERMENTING) || random.nextFloat() > 0.25f) return
        val direction = Direction.getRandom(random)
        if (direction == Direction.DOWN) return
        val blockPos = pos.relative(direction)
        if (state.canOcclude() && world[blockPos].isFaceSturdy(world, blockPos, direction.opposite)) return
        world.addParticle(
            ParticleTypes.SOUL,
            pos.x.toDouble() + if (direction.stepX == 0) random.nextDouble() else 0.5 + direction.stepX.toDouble() * 0.6,
            pos.y.toDouble() + if (direction.stepY == 0) random.nextDouble() else 0.5 + direction.stepY.toDouble() * 0.6,
            pos.z.toDouble() + if (direction.stepZ == 0) random.nextDouble() else 0.5 + direction.stepZ.toDouble() * 0.6,
            0.0,
            0.0,
            0.0
        )
    }

    companion object {
        val AGE: IntegerProperty = BlockStateProperties.AGE_3
        val FERMENTING: BooleanProperty = BooleanProperty.create("fermenting")
    }
}
