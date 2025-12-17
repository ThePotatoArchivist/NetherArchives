package archives.tater.netherarchives.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.player.LocalPlayer;

@Mixin(LocalPlayer.class)
public interface LocalPlayerAccessor {
    @Accessor
    void setFlashOnSetHealth(boolean value);
}
