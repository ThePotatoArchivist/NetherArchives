package archives.tater.netherarchives.mixin.damage;

import archives.tater.netherarchives.network.NoDamageEffectsPayload;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.level.ServerPlayer;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin extends PlayerMixin {
    @Inject(
            method = "doTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V", ordinal = 0)
    )
    private void sendEffectPayload(CallbackInfo ci) {
        if (netherarchives$damageNoEffect)
            ServerPlayNetworking.send((ServerPlayer) (Object) this, NoDamageEffectsPayload.INSTANCE);
    }
}
