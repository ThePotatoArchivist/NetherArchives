package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.item.SkisItem;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @ModifyReturnValue(
            method = "getMovementSpeed()F",
            at = @At("RETURN")
    )
    private float preventMovementOnSkis(float original) {
        return (SkisItem.isSkiing((PlayerEntity) (Object) this)) ? 0f : original;
    }
}
