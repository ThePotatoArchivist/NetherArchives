package archives.tater.netherarchives.mixin.damage;

import archives.tater.netherarchives.registry.NetherArchivesTags;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.objectweb.asm.Opcodes;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(
            method = "handleDamageEvent",
            at = @At("HEAD"),
            cancellable = true
    )
    private void preventEvent(DamageSource damageSource, CallbackInfo ci) {
        if (damageSource.is(NetherArchivesTags.NO_EFFECTS))
            ci.cancel();
    }

    @WrapWithCondition(
            method = "hurtServer",
            at = {
                    @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;playHurtSound(Lnet/minecraft/world/damagesource/DamageSource;)V"),
                    @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;playSecondaryHurtSound(Lnet/minecraft/world/damagesource/DamageSource;)V"),
            }
    )
    private boolean preventSound(LivingEntity instance, DamageSource damageSource) {
        return !damageSource.is(NetherArchivesTags.NO_EFFECTS);
    }

    @WrapWithCondition(
            method = "hurtServer",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/LivingEntity;hurtTime:I", opcode = Opcodes.PUTFIELD)
    )
    private boolean preventHurtTime(LivingEntity instance, int value, @Local(argsOnly = true) DamageSource source) {
        return !source.is(NetherArchivesTags.NO_EFFECTS);
    }
}
