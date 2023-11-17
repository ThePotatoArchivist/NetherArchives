package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.block.BlazeFireBlock;
import archives.tater.netherarchives.block.BlazePowderBlock;
import archives.tater.netherarchives.block.NetherArchivesBlocks;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlintAndSteelItem.class)
public abstract class FlintAndSteelItemMixin {
    @Inject(at = @At("HEAD"), method = "useOnBlock", cancellable = true)
    private void checkBlazeFire(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        if (world.getBlockState(blockPos).getBlock() instanceof BlazePowderBlock) {
            PlayerEntity playerEntity = context.getPlayer();

            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4f + 0.8f);
            BlockState blockState = NetherArchivesBlocks.BLAZE_FIRE.getDefaultState();
            world.setBlockState(blockPos, blockState, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
            world.emitGameEvent(playerEntity, GameEvent.BLOCK_PLACE, blockPos);
            ItemStack itemStack = context.getStack();
            if (playerEntity instanceof ServerPlayerEntity) {
                Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
                itemStack.damage(1, playerEntity, p -> p.sendToolBreakStatus(context.getHand()));
            }

            cir.setReturnValue(ActionResult.success(world.isClient()));


        }
    }

}
