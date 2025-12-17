package archives.tater.netherarchives.mixin.damage;

import archives.tater.netherarchives.registry.NetherArchivesTags;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

@Mixin(Player.class)
public class PlayerMixin {
    @Unique
    protected boolean netherarchives$damageNoEffect = false;

    @Inject(
            method = "actuallyHurt",
            at = @At("HEAD")
    )
    private void saveNoEffect(ServerLevel serverLevel, DamageSource damageSource, float f, CallbackInfo ci) {
        netherarchives$damageNoEffect = damageSource.is(NetherArchivesTags.NO_EFFECTS);
    }

}
