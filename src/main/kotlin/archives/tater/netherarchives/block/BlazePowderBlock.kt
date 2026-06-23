package archives.tater.netherarchives.block

import archives.tater.netherarchives.registry.ModBlocks
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.InsideBlockEffectApplier
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.ScheduledTickAccess
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.MultifaceBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class BlazePowderBlock(settings: Properties) : Block(settings) {
    companion object {
        val SHAPE: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0)
    }

    override fun getShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape = SHAPE

    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        val blockPos = pos.below()
        return MultifaceBlock.canAttachTo(level, Direction.DOWN, blockPos, level.getBlockState(blockPos))
    }

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
        if (!state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState()
        }
        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighborPos, neighborState, random)
    }

    override fun propagatesSkylightDown(state: BlockState): Boolean {
        return state.fluidState.isEmpty
    }

    override fun useItemOn(
        stack: ItemStack,
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hit: BlockHitResult,
    ): InteractionResult {
        if (!stack.`is`(Items.FLINT_AND_STEEL) && !stack.`is`(Items.FIRE_CHARGE)) return InteractionResult.TRY_WITH_EMPTY_HAND

        level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, level.getRandom().nextFloat() * 0.4f + 0.8f)
        val blockState = ModBlocks.BLAZE_FIRE.defaultBlockState()
        level.setBlock(pos, blockState, UPDATE_ALL or UPDATE_IMMEDIATE)
        level.gameEvent(player, GameEvent.BLOCK_PLACE, pos)
        if (player is ServerPlayer)
            CriteriaTriggers.PLACED_BLOCK.trigger(player, pos, stack)
        if (stack.`is`(Items.FLINT_AND_STEEL))
            stack.hurtAndBreak(1, player, hand)
        else
            stack.shrink(1)
        return InteractionResult.SUCCESS.heldItemTransformedTo(stack)
    }

    override fun entityInside(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        entity: Entity,
        effectApplier: InsideBlockEffectApplier,
        isPrecise: Boolean
    ) {
        if (!level.isClientSide && entity is Projectile && entity.isOnFire && level is ServerLevel && entity.mayInteract(level, pos)) {
            level.setBlock(pos, ModBlocks.BLAZE_FIRE.defaultBlockState(), UPDATE_ALL or UPDATE_IMMEDIATE)
        }
    }
}
