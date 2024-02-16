package archives.tater.netherarchives.mixin.client;

import net.minecraft.client.render.entity.feature.WitherArmorFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(WitherArmorFeatureRenderer.class)
public class WitherArmorFeatureRendererMixin {
    @ModifyConstant(
            method = "<clinit>",
            constant = @Constant(stringValue = "textures/entity/wither/wither_armor.png")
    )
    private static String modifyTexture(String constant) {
        return "netherarchives:textures/entity/wither/wither_armor.png";
    }
}
