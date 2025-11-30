package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.NetherArchives;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WitherSkullRenderer.class)
public class WitherSkullRendererMixin {
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourceLocation;withDefaultNamespace(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;", ordinal = 0)
    )
    private static ResourceLocation replaceTexture(ResourceLocation original) {
        return NetherArchives.id("textures/entity/wither/wither_invulnerable_eyes.png");
    }
}
