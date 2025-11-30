package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.NetherArchives;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.entity.layers.WitherArmorLayer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WitherArmorLayer.class)
public class WitherArmorFeatureRendererMixin {
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/Identifier;withDefaultNamespace(Ljava/lang/String;)Lnet/minecraft/resources/Identifier;")
    )
    private static Identifier replaceArmorTexture(Identifier original) {
        return NetherArchives.id("textures/entity/wither/wither_armor.png");
    }
}
