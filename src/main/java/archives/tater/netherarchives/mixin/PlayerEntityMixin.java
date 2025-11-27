package archives.tater.netherarchives.mixin;

import archives.tater.netherarchives.item.SkisItem;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerEntityMixin {
    @ModifyReturnValue(
            method = "getSpeed",
            at = @At("RETURN")
    )
    private float preventMovementOnSkis(float original) {
        return (SkisItem.isSkiing((Player) (Object) this)) ? 0f : original;
    }
}
