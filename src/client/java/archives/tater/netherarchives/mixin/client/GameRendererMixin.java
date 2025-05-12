package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.item.SkisItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow @Final MinecraftClient client;

    @Inject(
            method = "bobView",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void noBobSkis(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (SkisItem.wearsSkis(client.getCameraEntity()))
            ci.cancel();
    }
}
