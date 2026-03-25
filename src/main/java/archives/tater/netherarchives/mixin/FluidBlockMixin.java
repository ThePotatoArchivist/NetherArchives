package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.item.SkisItem;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@Mixin(LiquidBlock.class)
public class FluidBlockMixin {
    @ModifyReturnValue(
            method = "getCollisionShape",
            at = @At("RETURN")
    )
    private VoxelShape useSkiShape(VoxelShape original, BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext entityShapeContext
                && SkisItem.wearsSkis(entityShapeContext.getEntity())
                && context.isAbove(SkisItem.FLUID_SKI_HEIGHT_COLLISION_SHAPE, pos, true)
                && context.canStandOnFluid(level.getFluidState(pos.above()), state.getFluidState()))
            return SkisItem.FLUID_SKI_COLLISION_SHAPE;
        return original;
    }
}
