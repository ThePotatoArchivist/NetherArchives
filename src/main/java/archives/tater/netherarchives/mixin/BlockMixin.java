package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.item.SkisItem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(Block.class)
public class BlockMixin {
    // I think this is bettern than adding an override in FluidBlock?
    @Inject(
            method = "onLandedUpon",
            at = @At("HEAD"),
            cancellable = true)
    private void preventFallOnSkis(World world, BlockState state, BlockPos pos, Entity entity, double fallDistance, CallbackInfo ci) {
        //noinspection ConstantValue
        if (!((Object) this instanceof FluidBlock)) return;
        if (!(entity instanceof LivingEntity livingEntity) || !SkisItem.canSki(livingEntity, state.getFluidState())) return;
        entity.handleFallDamage(fallDistance, 0.0F, world.getDamageSources().fall());
        ci.cancel();
    }
}
