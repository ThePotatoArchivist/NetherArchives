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
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements AirSkiier {
    @Shadow @Final public WalkAnimationState walkAnimation;

    @Shadow public abstract boolean isFallFlying();

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    @Shadow
    public abstract float getSpeed();

    @Unique
    private boolean netherarchives$isAirSkiing = false;

    @Unique
    private int netherarchives$ticksSkiing = 0;

    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(
            method = "canStandOnFluid",
            at = @At("HEAD"),
            cancellable = true)
    private void checkBasaltSkis(FluidState state, CallbackInfoReturnable<Boolean> cir) {
        if (SkisItem.canSki((LivingEntity) (Object) this, state) && getFluidHeight(FluidTags.WATER) <= SkisItem.MAX_FLUID_DEPTH && getFluidHeight(FluidTags.LAVA) <= SkisItem.MAX_FLUID_DEPTH)
            cir.setReturnValue(true);
    }

    @SuppressWarnings("DataFlowIssue")
    @ModifyExpressionValue(
            method = "travel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;getFriction()F")
    )
    private float skiFriction(float original, @Local FluidState fluidState, @Share("isSkiing") LocalBooleanRef isSkiing) {
        isSkiing.set(SkisItem.canSki((LivingEntity) (Object) this, fluidState));

        return isSkiing.get() ? 1.08f : original;
        // Air drag is 0.91
    }

    @WrapOperation(
            method = "travel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;calculateEntityAnimation(Z)V")
    )
    private void damageSkisAndNoSkiLimbs(LivingEntity instance, boolean flutter, Operation<Void> original, @Share("isSkiing") LocalBooleanRef isSkiing) {
        if (!level().isClientSide && (isSkiing.get() || netherarchives$isAirSkiing) && getDeltaMovement().length() > SkisItem.MIN_DAMAGE_VELOCITY) {
            netherarchives$ticksSkiing += 1;
            if (netherarchives$ticksSkiing >= SkisItem.DAMAGE_FREQUENCY) {
                getItemBySlot(EquipmentSlot.FEET).hurtAndBreak(1, (LivingEntity) (Object) this, EquipmentSlot.FEET);
                netherarchives$ticksSkiing = 0;
            }
        }

        if (!isSkiing.get() && !SkisItem.isSkiing(instance) && !netherarchives$isAirSkiing) {
            original.call(instance, flutter);
            return;
        }

        walkAnimation.update(0, 0.4f);
    }

    @ModifyReturnValue(
            method = "getFrictionInfluencedSpeed",
            at = @At("RETURN")
    )
    private float preventMovementOnSkis(float original) {
        if (SkisItem.isSkiing((LivingEntity) (Object) this)) return 0.0f;
        return original;
    }

    @ModifyExpressionValue(
            method = "getFrictionInfluencedSpeed",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getFlyingSpeed()F")
    )
    private float increasedAirSkiMovement(float original) {
        return netherarchives$isAirSkiing ? 0.35f * getSpeed() : original;
    }

    @Inject(
            method = "jumpFromGround",
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

    @ModifyVariable(
            method = "travel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/core/Holder;)Z", ordinal = 0)
    )
    private double slowFallingWhileAirSkiing(double original) {
        return netherarchives$isAirSkiing && !isShiftKeyDown() ? 0.01 : original;
    }

    @Inject(
            method = "travel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;")
    )
    private void stopAirSkiing(Vec3 movementInput, CallbackInfo ci) {
        //noinspection ConstantValue
        if (this.isInWater() || this.isInLava() || this.isFallFlying() || ((Object) this instanceof Player playerEntity && playerEntity.getAbilities().flying))
            netherarchives$isAirSkiing = false;
    }

    @ModifyExpressionValue(
            method = "checkFallDamage",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/LivingEntity;fallDistance:F", ordinal = 1, opcode = Opcodes.GETFIELD)
    )
    private float noFallDamageWhileAirSkiing(float original) {
        if (!netherarchives$isAirSkiing) return original;
        return 0f;
    }

    @WrapWithCondition(
            method = "checkFallDamage",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;checkFallDamage(DZLnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)V")
    )
    private boolean noFallDamageWhileAirSkiing(Entity instance, double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
        if (!onGround || !netherarchives$isAirSkiing) return true;
        netherarchives$isAirSkiing = false;
        resetFallDistance();
        return false;
    }
}
