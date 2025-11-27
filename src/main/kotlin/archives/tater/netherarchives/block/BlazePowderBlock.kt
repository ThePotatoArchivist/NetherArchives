package archives.tater.netherarchives.block

import archives.tater.netherarchives.registry.NetherArchivesBlocks
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity.getSlotForHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.phys.shapes.VoxelShape
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.gameevent.GameEvent

class BlazePowderBlock(settings: Properties) : Block(settings) {
    companion object {
        val SHAPE: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0)
    }

    override fun getShape(
        state: BlockState?,
        world: BlockGetter?,
        pos: BlockPos?,
        context: CollisionContext?
    ): VoxelShape = SHAPE

    override fun canSurvive(state: BlockState?, world: LevelReader, pos: BlockPos): Boolean {
        val blockPos = pos.below()
        return world.getBlockState(blockPos).isFaceSturdy(world, blockPos, Direction.UP)
    }

    override fun updateShape(
        state: BlockState,
        direction: Direction?,
        neighborState: BlockState?,
        world: LevelAccessor?,
        pos: BlockPos?,
        neighborPos: BlockPos?
    ): BlockState? {
        if (!state.canSurvive(world, pos)) {
            return Blocks.AIR.defaultBlockState()
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos)
    }

    override fun propagatesSkylightDown(state: BlockState, world: BlockGetter?, pos: BlockPos?): Boolean {
        return state.fluidState.isEmpty
    }

    override fun useItemOn(
        stack: ItemStack,
        state: BlockState,
        world: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hit: BlockHitResult,
    ): ItemInteractionResult {
        if (!stack.`is`(Items.FLINT_AND_STEEL) && !stack.`is`(Items.FIRE_CHARGE)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION

        world.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4f + 0.8f)
        val blockState = NetherArchivesBlocks.BLAZE_FIRE.defaultBlockState()
        world.setBlock(pos, blockState, UPDATE_ALL or UPDATE_IMMEDIATE)
        world.gameEvent(player, GameEvent.BLOCK_PLACE, pos)
        if (player is ServerPlayer)
            CriteriaTriggers.PLACED_BLOCK.trigger(player, pos, stack)
        if (stack.`is`(Items.FLINT_AND_STEEL))
            stack.hurtAndBreak(1, player, getSlotForHand(hand))
        else
            stack.shrink(1)
        return ItemInteractionResult.SUCCESS
    }

    override fun entityInside(state: BlockState, world: Level, pos: BlockPos, entity: Entity) {
        if (!world.isClientSide && entity is Projectile && entity.isOnFire && entity.mayInteract(world, pos)) {
            world.setBlock(pos, NetherArchivesBlocks.BLAZE_FIRE.defaultBlockState(), UPDATE_ALL or UPDATE_IMMEDIATE)
        }
    }
}
