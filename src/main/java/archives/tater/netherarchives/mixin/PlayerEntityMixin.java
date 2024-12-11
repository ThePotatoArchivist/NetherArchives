package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.item.SkisItem;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(
            method = "getMovementSpeed()F",
            at = @At("HEAD"),
            cancellable = true)
    private void preventMovementOnSkis(CallbackInfoReturnable<Float> cir) {
        if (SkisItem.isSkiing((PlayerEntity) (Object) this)) cir.setReturnValue(0.0f);
    }
}
