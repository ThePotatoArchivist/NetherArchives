package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.NetherArchives;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.entity.feature.WitherArmorFeatureRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WitherArmorFeatureRenderer.class)
public class WitherArmorFeatureRendererMixin {
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Identifier;ofVanilla(Ljava/lang/String;)Lnet/minecraft/util/Identifier;")
    )
    private static Identifier replaceArmorTexture(Identifier original) {
        return NetherArchives.id("textures/entity/wither/wither_armor.png");
    }
}
