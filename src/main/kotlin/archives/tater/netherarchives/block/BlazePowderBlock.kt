package archives.tater.netherarchives.block

import archives.tater.netherarchives.registry.NetherArchivesBlocks
import net.minecraft.advancement.criterion.Criteria
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.ShapeContext
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity.getSlotForHand
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Hand
import net.minecraft.util.ItemActionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import net.minecraft.world.event.GameEvent

class BlazePowderBlock(settings: Settings) : Block(settings) {
    companion object {
        val SHAPE: VoxelShape = createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0)
    }

    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape = SHAPE

    override fun canPlaceAt(state: BlockState?, world: WorldView, pos: BlockPos): Boolean {
        val blockPos = pos.down()
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, Direction.UP)
    }

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
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    override fun isTransparent(state: BlockState, world: BlockView?, pos: BlockPos?): Boolean {
        return state.fluidState.isEmpty
    }

    override fun onUseWithItem(
        stack: ItemStack,
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult,
    ): ItemActionResult {
        if (!stack.isOf(Items.FLINT_AND_STEEL) && !stack.isOf(Items.FIRE_CHARGE)) return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION

        world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4f + 0.8f)
        val blockState = NetherArchivesBlocks.BLAZE_FIRE.defaultState
        world.setBlockState(pos, blockState, NOTIFY_ALL or REDRAW_ON_MAIN_THREAD)
        world.emitGameEvent(player, GameEvent.BLOCK_PLACE, pos)
        if (player is ServerPlayerEntity)
            Criteria.PLACED_BLOCK.trigger(player, pos, stack)
        if (stack.isOf(Items.FLINT_AND_STEEL))
            stack.damage(1, player, getSlotForHand(hand))
        else
            stack.decrement(1)
        return ItemActionResult.SUCCESS
    }

    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        if (!world.isClient && entity is ProjectileEntity && entity.isOnFire && entity.canModifyAt(world, pos)) {
            world.setBlockState(pos, NetherArchivesBlocks.BLAZE_FIRE.defaultState, NOTIFY_ALL or REDRAW_ON_MAIN_THREAD)
        }
    }
}
