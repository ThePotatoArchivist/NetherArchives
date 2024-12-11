package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.item.SkisItem;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity {
    public ClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @ModifyReturnValue(
            method = "canStartSprinting",
            at = @At("RETURN")
    )
    private boolean preventSprintingWithSkies(boolean original) {
        //noinspection ConstantValue
        return original && !(SkisItem.wearsSkis((ClientPlayerEntity) (Object) this) || getAbilities().flying);
    }
}
