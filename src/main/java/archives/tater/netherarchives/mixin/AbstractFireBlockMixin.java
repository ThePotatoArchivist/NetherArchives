package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.block.BlazePowderBlock;
import archives.tater.netherarchives.block.NetherArchivesBlocks;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFireBlock.class)
public abstract class AbstractFireBlockMixin {
    @Inject(at = @At("HEAD"), method = "getState", cancellable = true)
    private static void checkBlazeFire(BlockView world, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        if (world.getBlockState(pos).getBlock() instanceof BlazePowderBlock) {
            cir.setReturnValue(NetherArchivesBlocks.BLAZE_FIRE.getDefaultState());
        }
    }

    @Inject(at = @At("HEAD"), method = "canPlaceAt", cancellable = true)
    private static void allowPlacement(World world, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (world.getBlockState(pos).getBlock() instanceof BlazePowderBlock) {
            cir.setReturnValue(true);
        }
    }

}
