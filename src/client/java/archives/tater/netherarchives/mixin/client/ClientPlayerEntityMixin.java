package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.item.SkisItem;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

@Mixin(LocalPlayer.class)
public abstract class ClientPlayerEntityMixin extends Player {

    public ClientPlayerEntityMixin(Level world, GameProfile profile) {
        super(world, profile);
    }

    @ModifyReturnValue(
            method = "canStartSprinting",
            at = @At("RETURN")
    )
    private boolean preventSprintingWithSkies(boolean original) {
        //noinspection ConstantValue
        return original && (!SkisItem.wearsSkis((LocalPlayer) (Object) this) || getAbilities().flying);
    }
}
