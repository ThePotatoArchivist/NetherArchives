package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.item.SkisItem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

@Mixin(Block.class)
public class BlockMixin {
    // I think this is bettern than adding an override in FluidBlock?
    @Inject(
            method = "fallOn",
            at = @At("HEAD"),
            cancellable = true)
    private void preventFallOnSkis(Level world, BlockState state, BlockPos pos, Entity entity, double fallDistance, CallbackInfo ci) {
        //noinspection ConstantValue
        if (!((Object) this instanceof LiquidBlock)) return;
        if (!(entity instanceof LivingEntity livingEntity) || !SkisItem.canSki(livingEntity, state.getFluidState())) return;
        entity.causeFallDamage(fallDistance, 0.0F, world.damageSources().fall());
        ci.cancel();
    }
}
