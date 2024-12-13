package archives.tater.netherarchives.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.ShapeContext
import net.minecraft.entity.Entity
import net.minecraft.entity.ai.pathing.NavigationType
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView

class BlazePowderBlock(settings: Settings) : Block(settings) {
    companion object {
        val SHAPE: VoxelShape = createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape = SHAPE

    @Suppress("OVERRIDE_DEPRECATION")
    override fun canPlaceAt(state: BlockState?, world: WorldView, pos: BlockPos): Boolean {
        val blockPos = pos.down()
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, Direction.UP)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getStateForNeighborUpdate(
        state: BlockState,
        direction: Direction?,
        neighborState: BlockState?,
        world: WorldAccess?,
        pos: BlockPos?,
        neighborPos: BlockPos?
    ): BlockState? {
        if (!state.canPlaceAt(world, pos)) {
            return Blocks.AIR.defaultState
        }
        @Suppress("DEPRECATION")
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    override fun isTransparent(state: BlockState, world: BlockView?, pos: BlockPos?): Boolean {
        return state.fluidState.isEmpty
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun canPathfindThrough(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        type: NavigationType
    ): Boolean {
        if (type == NavigationType.AIR && !collidable) {
            return true
        }
        @Suppress("DEPRECATION")
        return super.canPathfindThrough(state, world, pos, type)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        if (!world.isClient && entity is ProjectileEntity && entity.isOnFire && entity.canModifyAt(world, pos)) {
            world.setBlockState(pos, NetherArchivesBlocks.BLAZE_FIRE.defaultState, NOTIFY_ALL or REDRAW_ON_MAIN_THREAD)
        }
    }
}
