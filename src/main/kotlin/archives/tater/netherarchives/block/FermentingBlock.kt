package archives.tater.netherarchives.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.registry.tag.TagKey
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.world.World

class FermentingBlock(
    private val fermentingTag: TagKey<Block>,
    private val fermentingChance: Float,
    private val resultBlock: Block, settings: Settings,
) : Block(settings.ticksRandomly()) {
    companion object {
        val AGE: IntProperty = Properties.AGE_7
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(AGE)
    }

    override fun hasRandomTicks(state: BlockState) = true

    @Suppress("OVERRIDE_DEPRECATION")
    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        if (!overValidBlock(world, pos)) return
        if (random.nextFloat() > fermentingChance) return
        if (state.get(AGE) < 7) {
            world.setBlockState(pos, state.with(AGE, state.get(AGE) + 1))
            return
        }
        world.setBlockState(pos, resultBlock.defaultState)
        // TODO play a conversion sound
    }

    private fun overValidBlock(world: World, pos: BlockPos): Boolean {
        return world.getBlockState(pos.down()).isIn(fermentingTag)
    }
}
