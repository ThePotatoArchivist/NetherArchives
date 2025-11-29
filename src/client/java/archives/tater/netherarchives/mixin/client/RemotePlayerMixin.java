package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.duck.AirSkiier;
import archives.tater.netherarchives.item.SkisItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.player.RemotePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RemotePlayer.class)
public class RemotePlayerMixin {
    @WrapOperation(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/RemotePlayer;calculateEntityAnimation(Z)V")
    )
    private void noLimbOnSkis(RemotePlayer instance, boolean b, Operation<Void> original) {
        if (!SkisItem.isSkiing(instance) && !((AirSkiier) instance).netherarchives$isAirSkiing()) {
            original.call(instance, b);
            return;
        }

        instance.walkAnimation.update(0, 0.4f);
    }
}
