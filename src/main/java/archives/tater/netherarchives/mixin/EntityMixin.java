package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.item.SkisItem;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @ModifyConstant(
            method = "isInLava",
            constant = @Constant(doubleValue = 0.0)
    )
    private double handleSkis(double constant) {
        //noinspection ConstantValue
        return constant < SkisItem.MAX_FLUID_DEPTH && SkisItem.wearsSkis((Entity) (Object) this) ? SkisItem.MAX_FLUID_DEPTH : constant;
    }
}
