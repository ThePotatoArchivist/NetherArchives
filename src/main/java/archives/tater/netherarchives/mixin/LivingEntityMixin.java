package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.duck.AirSkiier;
import archives.tater.netherarchives.item.SkisItem;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements AirSkiier {
    @Shadow @Final public LimbAnimator limbAnimator;

    @Shadow public abstract boolean isFallFlying();

    @Shadow public abstract float getMovementSpeed();

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Unique
    private boolean netherarchives$isAirSkiing = false;

    @Unique
    private int netherarchives$ticksSkiing = 0;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "canWalkOnFluid",
            at = @At("HEAD"),
            cancellable = true)
    private void checkBasaltSkis(FluidState state, CallbackInfoReturnable<Boolean> cir) {
        if (SkisItem.canSki((LivingEntity) (Object) this, state) && getFluidHeight(FluidTags.WATER) <= SkisItem.MAX_FLUID_DEPTH && getFluidHeight(FluidTags.LAVA) <= SkisItem.MAX_FLUID_DEPTH)
            cir.setReturnValue(true);
    }

    @SuppressWarnings("DataFlowIssue")
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
    private void damageSkisAndNoSkiLimbs(LivingEntity instance, boolean flutter, Operation<Void> original, @Share("isSkiing") LocalBooleanRef isSkiing) {
        if ((isSkiing.get() || netherarchives$isAirSkiing) && getVelocity().length() > SkisItem.MIN_DAMAGE_VELOCITY) {
            netherarchives$ticksSkiing += 1;
            if (netherarchives$ticksSkiing >= SkisItem.DAMAGE_FREQUENCY) {
                getEquippedStack(EquipmentSlot.FEET).damage(1, (LivingEntity) (Object) this, p -> p.sendEquipmentBreakStatus(EquipmentSlot.FEET));
                netherarchives$ticksSkiing = 0;
            }
        }

        if (!isSkiing.get() && !SkisItem.isSkiing(instance) && !netherarchives$isAirSkiing) {
            original.call(instance, flutter);
            return;
        }

        limbAnimator.updateLimbs(0, 0.4f);
    }

    @ModifyReturnValue(
            method = "getMovementSpeed()F",
            at = @At("RETURN")
    )
    private float preventMovementOnSkis(float original) {
        if (SkisItem.isSkiing((LivingEntity) (Object) this)) return 0.0f;
        return original;
    }

    @ModifyExpressionValue(
            method = "getMovementSpeed(F)F",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getOffGroundSpeed()F")
    )
    private float increasedAirSkiMovement(float original) {
        return netherarchives$isAirSkiing ? 0.35f * getMovementSpeed() : original;
    }

    @Inject(
            method = "jump",
            at = @At("HEAD"),
            cancellable = true)
    private void preventJumpOnSkis(CallbackInfo ci) {
        if (SkisItem.isSkiing((LivingEntity) (Object) this)) ci.cancel();
    }

    @Override
    public boolean netherarchives$isAirSkiing() {
        return netherarchives$isAirSkiing;
    }

    @Override
    public void netherarchives$setAirSkiing(boolean airSkiing) {
        netherarchives$isAirSkiing = airSkiing;
    }

    @WrapOperation(
            method = "travel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z", ordinal = 0)
    )
    private boolean slowFallingWhileAirSkiing(LivingEntity instance, StatusEffect effect, Operation<Boolean> original) {
        return original.call(instance, effect) || netherarchives$isAirSkiing && !instance.isSneaking();
    }

    @Inject(
            method = "travel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getFluidState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/fluid/FluidState;")
    )
    private void stopAirSkiing(Vec3d movementInput, CallbackInfo ci) {
        //noinspection ConstantValue
        if (this.isTouchingWater() || this.isInLava() || this.isFallFlying() || ((Object) this instanceof PlayerEntity playerEntity && playerEntity.getAbilities().flying))
            netherarchives$isAirSkiing = false;
    }

    @ModifyExpressionValue(
            method = "fall",
            at = @At(value = "FIELD", target = "Lnet/minecraft/entity/LivingEntity;fallDistance:F", ordinal = 1)
    )
    private float noFallDamageWhileAirSkiing(float original) {
        if (!netherarchives$isAirSkiing) return original;
        return 0f;
    }

    @WrapWithCondition(
            method = "fall",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;fall(DZLnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)V")
    )
    private boolean noFallDamageWhileAirSkiing(Entity instance, double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
        if (!onGround || !netherarchives$isAirSkiing) return true;
        netherarchives$isAirSkiing = false;
        onLanding();
        return false;
    }
}
