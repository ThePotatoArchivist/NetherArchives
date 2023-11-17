package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.render.entity.feature.WitherEyesFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.WitherEntityRenderer;
import net.minecraft.client.render.entity.model.WitherEntityModel;
import net.minecraft.entity.boss.WitherEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherEntityRenderer.class)
public abstract class WitherEntityRendererMixin extends MobEntityRenderer<WitherEntity, WitherEntityModel<WitherEntity>> {
    public WitherEntityRendererMixin(EntityRendererFactory.Context context, WitherEntityModel<WitherEntity> entityModel, float f) {
        super(context, entityModel, f);
    }


    @Inject(at = @At("TAIL"), method = "<init>")
    private void addEyesFeature(EntityRendererFactory.Context context, CallbackInfo ci) {
        addFeature(new WitherEyesFeatureRenderer<>(this));
    }

}

