package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.item.SkisItem;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.entity.LimbAnimator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow @Final public LimbAnimator limbAnimator;

    @Inject(
            method = "canWalkOnFluid",
            at = @At("HEAD"),
            cancellable = true)
    private void checkBasaltSkis(FluidState state, CallbackInfoReturnable<Boolean> cir) {
        if (SkisItem.canSki((LivingEntity) (Object) this, state))
            cir.setReturnValue(true);
    }

    @ModifyExpressionValue(
            method = "travel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getSlipperiness()F")
    )
    private float skiFriction(float original, @Local FluidState fluidState, @Share("isSkiing") LocalBooleanRef isSkiing) {
        isSkiing.set(SkisItem.canSki((LivingEntity) (Object) this, fluidState));
        return isSkiing.get() ? 1.08f : original;
        // Air drag is 0.91
    }

    @WrapOperation(
            method = "travel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;updateLimbs(Z)V")
    )
    private void noSkiLimbs(LivingEntity instance, boolean flutter, Operation<Void> original, @Share("isSkiing") LocalBooleanRef isSkiing) {
        if (!isSkiing.get() && !SkisItem.isSkiing(instance)) {
            original.call(instance, flutter);
            return;
        }

        limbAnimator.updateLimbs(0, 0.4f);
    }

    @Inject(
            method = "getMovementSpeed()F",
            at = @At("HEAD"),
            cancellable = true)
    private void preventMovementOnSkis(CallbackInfoReturnable<Float> cir) {
        if (SkisItem.isSkiing((LivingEntity) (Object) this)) cir.setReturnValue(0.0f);
    }

    @Inject(
            method = "jump",
            at = @At("HEAD"),
            cancellable = true)
    private void preventJumpOnSkis(CallbackInfo ci) {
        if (SkisItem.isSkiing((LivingEntity) (Object) this)) ci.cancel();
    }
}
