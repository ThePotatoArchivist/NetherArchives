package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.registry.NetherArchivesTags;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.util.DyeColor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static archives.tater.netherarchives.UtilsKt.invertArgb;

@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntityMixin {
    @ModifyExpressionValue(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/DyeColor;getEntityColor()I"),
            require = 0 // Fails on sinytra
    )
    private static int soulGlassInvertColor(int value, @Local(ordinal = 1) BlockState state, @Local @Nullable BeaconBlockEntity.BeamSegment beamSegment, @Share("inverted") LocalBooleanRef inverted) {
        if (!(state.isIn(NetherArchivesTags.INVERTS_BEACON))) {
            inverted.set(false);
            return value;
        }
        inverted.set(true);
        return invertArgb(beamSegment == null ? DyeColor.WHITE.getEntityColor() : beamSegment.getColor());
    }

    @WrapOperation(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/ColorHelper$Argb;averageArgb(II)I")
    ) private static int dontBlendColor(int a, int b, Operation<Integer> original, @Share("inverted") LocalBooleanRef inverted) {
        return inverted.get() ? b : original.call(a, b);
    }
}
