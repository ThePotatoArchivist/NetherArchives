package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.item.SkisItem;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@Mixin(LiquidBlock.class)
public class FluidBlockMixin {
    @ModifyExpressionValue(
            method = "getCollisionShape",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/LiquidBlock;STABLE_SHAPE:Lnet/minecraft/world/phys/shapes/VoxelShape;", ordinal = 1, opcode = Opcodes.GETSTATIC)
    )
    private VoxelShape useSkiShape(VoxelShape original, @Local(argsOnly = true) CollisionContext context) {
        if (context instanceof EntityCollisionContext entityShapeContext && SkisItem.wearsSkis(entityShapeContext.getEntity()))
            return SkisItem.FLUID_SKI_COLLISION_SHAPE;
        return original;
    }

    @ModifyArg(
            method = "getCollisionShape",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/shapes/CollisionContext;isAbove(Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/core/BlockPos;Z)Z"),
            index = 0
    )
    private VoxelShape useReducedHeight(VoxelShape shape, @Local(argsOnly = true) CollisionContext context) {
        if (context instanceof EntityCollisionContext entityShapeContext && SkisItem.wearsSkis(entityShapeContext.getEntity()))
            return SkisItem.FLUID_SKI_HEIGHT_COLLISION_SHAPE;
        return shape;
    }
}
