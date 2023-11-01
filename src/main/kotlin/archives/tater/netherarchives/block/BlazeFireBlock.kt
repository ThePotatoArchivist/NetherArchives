package archives.tater.netherarchives.block

import archives.tater.netherarchives.datagen.BlockTagGenerator
import net.minecraft.block.AbstractFireBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.IntProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.world.GameRules
import net.minecraft.world.WorldView

class BlazeFireBlock(settings: Settings) : AbstractFireBlock(settings, 2.0f) {
    companion object {
        val AGE: IntProperty = Properties.AGE_15

        private fun getFireTickDelay(random: Random) = 30 + random.nextInt(10)

    }

    init {
        defaultState = stateManager.defaultState.with(AGE, 0);
    }
    
    override fun isFlammable(state: BlockState?) = true

    // Copied from FireBlock
    @Suppress("OVERRIDE_DEPRECATION")
    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        world.scheduleBlockTick(pos, this, getFireTickDelay(world.random))
        if (!world.gameRules.getBoolean(GameRules.DO_FIRE_TICK)) return

        val blockState = world.getBlockState(pos.down())
        val infiniburn = blockState.isIn(world.dimension.infiniburn())
        val age = state.get(AGE);
        
        if (!infiniburn && world.isRaining && random.nextFloat() < 0.2f + age * 0.03f) {
            world.removeBlock(pos, false)
            return
        }
        
        val newAge = 15.coerceAtMost(age + random.nextInt(3) / 2)
        
        if (age != newAge) {
            val newState = state.with(AGE, newAge)
            world.setBlockState(pos, newState, Block.NO_REDRAW)
        }
        
        if (!infiniburn) {
            if (!world.getBlockState(pos).isSideSolidFullSquare(world, pos, Direction.UP) || age > 3) {
                world.removeBlock(pos, false)
                return
            }
            if (age == 15 && random.nextInt(4) == 0 && !isFlammable(blockState)) {
                world.removeBlock(pos, false)
                return
            }
        }
        
    }

}
