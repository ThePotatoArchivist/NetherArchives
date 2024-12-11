package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.item.SkisItem;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FluidBlock.class)
public class FluidBlockMixin {
    @ModifyExpressionValue(
            method = "getCollisionShape",
            at = @At(value = "FIELD", target = "Lnet/minecraft/block/FluidBlock;COLLISION_SHAPE:Lnet/minecraft/util/shape/VoxelShape;")
    )
    private VoxelShape useSkiShape(VoxelShape original, @Local(argsOnly = true) ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext && SkisItem.wearsSkis(entityShapeContext.getEntity()))
            return SkisItem.FLUID_SKI_COLLISION_SHAPE;
        return original;
    }

}
