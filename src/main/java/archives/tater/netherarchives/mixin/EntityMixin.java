package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.item.SkisItem;
import net.minecraft.entity.Entity;
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
        return constant < 0.375 && SkisItem.wearsSkis((Entity) (Object) this) ? 0.375 : constant;
    }
}
