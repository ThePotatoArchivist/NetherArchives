package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.NetherArchivesClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Inject(
            method = "renderMiscOverlays",
            at = @At("HEAD")
    )
    private void renderKnifeOverlay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (NetherArchivesClient.usingSoulKnife)
            renderOverlay(context, NetherArchivesClient.KNIFE_OVERLAY, 1f);
    }
}
