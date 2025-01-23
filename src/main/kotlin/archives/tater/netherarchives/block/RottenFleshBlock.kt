package archives.tater.netherarchives.block

import archives.tater.netherarchives.NetherArchivesTags
import archives.tater.netherarchives.get
import archives.tater.netherarchives.listCopy
import archives.tater.netherarchives.set
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.IntProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.world.World

class RottenFleshBlock(settings: FabricBlockSettings) : Block(settings.ticksRandomly()) {
    init {
        defaultState = stateManager.defaultState
            .with(AGE, 0)
            .with(FERMENTING, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(AGE, FERMENTING)
    }

    override fun hasRandomTicks(state: BlockState) = true

    private fun findCampfireDistance(world: World, pos: BlockPos): Int {
        // [Iterable.find] stops iterating when it finds the block so the object should still be on the same value
        val campfire = BlockPos.iterate(pos, pos.down(15)).find {
            world.getBlockState(it).isIn(NetherArchivesTags.ROTTEN_FLESH_FERMENTER)
        }
        if (campfire == null) return Integer.MAX_VALUE

        val states = BlockPos.iterate(pos, campfire).listCopy().run {
            subList(1, size - 1) // Ignore top and bottom block
        }.map(world::getBlockState)

        if (!states.all {
            !it.isOpaque || it.isOf(this) || it.isOf(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK)
        }) return Integer.MAX_VALUE

        val distance = states.count { it.isOf(this) }

        return distance
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val distance = findCampfireDistance(world, pos)

        val fermenting = distance <= 3

        val updatedState = if (fermenting == state[FERMENTING]) state else state.with(FERMENTING, fermenting)

        val finalState = when {
            /*
            0 blocks between - 30% chance
            1 block  between - 25% chance
            2 blocks between - 20% chance
            3 blocks between - 15% chance
             */
            !fermenting || random.nextFloat() > 0.3 - 0.05 * distance -> updatedState
            state[AGE] >= 3 -> NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK.defaultState
            else -> updatedState.with(AGE, state[AGE] + 1)
        }

        if (finalState != state)
            world[pos] = finalState
    }

    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        if (!state[FERMENTING] || random.nextFloat() > 0.25f) return
        val direction = Direction.random(random)
        if (direction == Direction.DOWN) return
        val blockPos = pos.offset(direction)
        if (state.isOpaque && world[blockPos].isSideSolidFullSquare(world, blockPos, direction.opposite)) return
        world.addParticle(
            ParticleTypes.SOUL,
            pos.x.toDouble() + if (direction.offsetX == 0) random.nextDouble() else 0.5 + direction.offsetX.toDouble() * 0.6,
            pos.y.toDouble() + if (direction.offsetY == 0) random.nextDouble() else 0.5 + direction.offsetY.toDouble() * 0.6,
            pos.z.toDouble() + if (direction.offsetZ == 0) random.nextDouble() else 0.5 + direction.offsetZ.toDouble() * 0.6,
            0.0,
            0.0,
            0.0
        )
    }

    companion object {
        val AGE: IntProperty = Properties.AGE_3
        val FERMENTING: BooleanProperty = BooleanProperty.of("fermenting")
    }
}
