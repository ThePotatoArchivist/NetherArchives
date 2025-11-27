package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.NetherArchivesClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.DeltaTracker;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class InGameHudMixin {
    @Shadow
    protected abstract void renderTextureOverlay(GuiGraphics guiGraphics, ResourceLocation shaderLocation, float alpha);

    @Inject(
            method = "renderCameraOverlays",
            at = @At("HEAD")
    )
    private void renderKnifeOverlay(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
        if (NetherArchivesClient.usingSoulKnife)
            renderTextureOverlay(context, NetherArchivesClient.KNIFE_OVERLAY, 1f);
    }
}
