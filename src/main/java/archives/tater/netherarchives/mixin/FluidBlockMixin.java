package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.item.SkisItem;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.shape.VoxelShape;

@Mixin(FluidBlock.class)
public class FluidBlockMixin {
    @ModifyExpressionValue(
            method = "getCollisionShape",
            at = @At(value = "FIELD", target = "Lnet/minecraft/block/FluidBlock;COLLISION_SHAPE:Lnet/minecraft/util/shape/VoxelShape;", ordinal = 1)
    )
    private VoxelShape useSkiShape(VoxelShape original, @Local(argsOnly = true) ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext && SkisItem.wearsSkis(entityShapeContext.getEntity()))
            return SkisItem.FLUID_SKI_COLLISION_SHAPE;
        return original;
    }

    @ModifyArg(
            method = "getCollisionShape",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/ShapeContext;isAbove(Lnet/minecraft/util/shape/VoxelShape;Lnet/minecraft/util/math/BlockPos;Z)Z"),
            index = 0
    )
    private VoxelShape useReducedHeight(VoxelShape shape, @Local(argsOnly = true) ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext && SkisItem.wearsSkis(entityShapeContext.getEntity()))
            return SkisItem.FLUID_SKI_HEIGHT_COLLISION_SHAPE;
        return shape;
    }
}
