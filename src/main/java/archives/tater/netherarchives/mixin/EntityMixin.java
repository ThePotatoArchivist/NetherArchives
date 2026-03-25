package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.item.SkisItem;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityFluidInteraction;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    @Final
    private EntityFluidInteraction fluidInteraction;

    @SuppressWarnings("ConstantValue")
    @ModifyReturnValue(
            method = "isInLava",
            at = @At("RETURN")
    )
    private boolean handleSkis(boolean original) {
        return original && (!SkisItem.wearsSkis((Entity) (Object) this) || !(fluidInteraction.getFluidHeight(FluidTags.LAVA) < SkisItem.MAX_FLUID_DEPTH));
    }

    @SuppressWarnings("ConstantValue")
    @ModifyExpressionValue(
            method = {
                    "lavaHurt",
                    "lavaIgnite"
            },
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;fireImmune()Z")
    )
    private boolean preventSetOnFire(boolean original) {
        return original || SkisItem.wearsSkis((Entity) (Object) this) && fluidInteraction.getFluidHeight(FluidTags.LAVA) < SkisItem.MAX_FLUID_DEPTH;
    }
}
