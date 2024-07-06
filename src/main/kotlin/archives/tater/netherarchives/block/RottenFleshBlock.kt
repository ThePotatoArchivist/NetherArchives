package archives.tater.netherarchives.block

import archives.tater.netherarchives.NetherArchivesTags
import archives.tater.netherarchives.listCopy
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random

class RottenFleshBlock(settings: FabricBlockSettings) : Block(settings.ticksRandomly()) {
    companion object {
        val AGE: IntProperty = Properties.AGE_7
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(AGE)
    }

    override fun hasRandomTicks(state: BlockState) = true

    @Suppress("OVERRIDE_DEPRECATION")
    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        // [Iterable.find] stops iterating when it finds the block so the object should still be on the same value
        val campfire = BlockPos.iterate(pos, pos.down(15)).find {
            world.getBlockState(it).isIn(NetherArchivesTags.ROTTEN_FLESH_FERMENTER)
        }
        if (campfire === null) return

        val states = BlockPos.iterate(pos, campfire).listCopy().run {
            subList(1, size - 1) // Ignore top and bottom block
        }.map(world::getBlockState)

        if (!states.all {
            !it.isOpaque || it.isOf(this) || it.isOf(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK)
        }) return

        val amountBetween = states.count { it.isOf(this) }

        if (amountBetween > 3) return

        /*
        0 blocks between - 30% chance
        1 block  between - 25% chance
        2 blocks between - 20% chance
        3 blocks between - 15% chance
         */
        if (random.nextFloat() > 0.3 - 0.05 * amountBetween) return

        if (state.get(AGE) < 7) {
            world.setBlockState(pos, state.with(AGE, state.get(AGE) + 1))
            return
        }

        world.setBlockState(pos, NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK.defaultState)
        // TODO play a conversion sound
    }
}
