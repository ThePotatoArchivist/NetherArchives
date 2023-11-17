package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.render.entity.feature.WitherSkeletonEyesFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.render.entity.WitherSkeletonEntityRenderer;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherSkeletonEntityRenderer.class)
public class WitherSkeletonEntityRendererMixin extends SkeletonEntityRenderer {

    public WitherSkeletonEntityRendererMixin(EntityRendererFactory.Context context) {
        super(context);
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    private void addEyesFeature(EntityRendererFactory.Context context, CallbackInfo ci) {
        addFeature(new WitherSkeletonEyesFeatureRenderer<AbstractSkeletonEntity>((WitherSkeletonEntityRenderer) (Object) this));
    }
}
