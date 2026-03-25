package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.item.SkisItem;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow @Final
    private Minecraft minecraft;

    @Inject(
            method = "bobView",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void noBobSkis(CameraRenderState cameraState, PoseStack poseStack, CallbackInfo ci) {
        if (SkisItem.wearsSkis(minecraft.getCameraEntity()))
            ci.cancel();
    }
}
