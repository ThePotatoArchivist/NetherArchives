package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.duck.AirSkiier;
import archives.tater.netherarchives.item.SkisItem;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements AirSkiier {
    @Shadow public abstract boolean isFallFlying();

    @Shadow public abstract float getSpeed();

    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlot slot);

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
            method = "travelInAir",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;getFriction()F")
    )
    private float skiFriction(float original, @Local BlockPos blockPos) {
        return SkisItem.canSki((LivingEntity) (Object) this, level().getFluidState(blockPos)) ? 1.08f : original;
        // Air drag is 0.91
    }

    @Inject(
            method = "aiStep",
            at = @At("HEAD")
    )
    private void damageSkis(CallbackInfo ci) {
        if (level().isClientSide()) return;
        if ((SkisItem.isSkiing((LivingEntity) (Object) this) || netherarchives$isAirSkiing) && getKnownMovement().horizontalDistanceSqr() > SkisItem.MIN_DAMAGE_VELOCITY * SkisItem.MIN_DAMAGE_VELOCITY) {
            netherarchives$ticksSkiing++;
            if (netherarchives$ticksSkiing >= SkisItem.DAMAGE_FREQUENCY) {
                getItemBySlot(EquipmentSlot.FEET).hurtAndBreak(1, (LivingEntity) (Object) this, EquipmentSlot.FEET);
                netherarchives$ticksSkiing = 0;
            }
        }
    }

    @ModifyArg(
            method = "calculateEntityAnimation(Z)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;updateWalkAnimation(F)V")
    )
    private float noLimbMovement(float posDelta) {
        return SkisItem.isSkiing((LivingEntity) (Object) this) || netherarchives$isAirSkiing ? 0 : posDelta;
    }

    @ModifyReturnValue(
            method = "getSpeed()F",
            at = @At("RETURN")
    )
    private float preventMovementOnSkis(float original) {
        if (SkisItem.isSkiing((LivingEntity) (Object) this)) return 0.0f;
        return original;
    }

    @ModifyExpressionValue(
            method = "getFrictionInfluencedSpeed(F)F",
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

    @ModifyExpressionValue(
            method = "getEffectiveGravity",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/core/Holder;)Z")
    )
    private boolean slowFallingWhileAirSkiing(boolean original) {
        return original || netherarchives$isAirSkiing && !isShiftKeyDown();
    }

    @Inject(
            method = "travelInAir",
            at = @At("TAIL")
    )
    private void stopAirSkiing(Vec3 movementInput, CallbackInfo ci) {
        //noinspection ConstantValue
        if (this.isInWater() || this.isInLava() || this.isFallFlying() || ((Object) this instanceof Player playerEntity && playerEntity.getAbilities().flying))
            netherarchives$isAirSkiing = false;
    }

    @ModifyExpressionValue(
            method = "checkFallDamage",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/LivingEntity;fallDistance:D", ordinal = 1, opcode = Opcodes.GETFIELD)
    )
    private double noFallDamageWhileAirSkiing(double original) {
        if (!netherarchives$isAirSkiing) return original;
        return 0.0;
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
