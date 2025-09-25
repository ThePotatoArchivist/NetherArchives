package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.item.SkisItem;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    protected Object2DoubleMap<TagKey<Fluid>> fluidHeight;

    @ModifyExpressionValue(
            method = "isInLava",
            at = @At(value = "CONSTANT", args = "doubleValue=0.0")
    )
    private double handleSkis(double constant) {
        //noinspection ConstantValue
        return constant < SkisItem.MAX_FLUID_DEPTH && SkisItem.wearsSkis((Entity) (Object) this) ? SkisItem.MAX_FLUID_DEPTH : constant;
    }

    @SuppressWarnings("ConstantValue")
    @ModifyExpressionValue(
            method = {
                    "setOnFireFromLava",
                    "igniteByLava"
            },
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isFireImmune()Z")
    )
    private boolean preventSetOnFire(boolean original) {
        return original || SkisItem.wearsSkis((Entity) (Object) this) && fluidHeight.getDouble(FluidTags.LAVA) < SkisItem.MAX_FLUID_DEPTH;
    }
}
