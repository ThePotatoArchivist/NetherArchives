package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.NetherArchives;
import archives.tater.netherarchives.registry.NetherArchivesTags;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.feature.FeatureFrameContext;
import net.minecraft.client.renderer.feature.FlameFeatureRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.List;

@Mixin(FlameFeatureRenderer.class)
public class FlameFeatureRendererMixin {

    @Unique
    @SuppressWarnings("deprecation")
    private static final SpriteId BLAZE_FIRE_0 = new SpriteId(TextureAtlas.LOCATION_BLOCKS, NetherArchives.id("block/blaze_fire_0"));
    @Unique
    @SuppressWarnings("deprecation")
    private static final SpriteId BLAZE_FIRE_1 = new SpriteId(TextureAtlas.LOCATION_BLOCKS, NetherArchives.id("block/blaze_fire_1"));

    @Inject(
            method = "buildGroup",
            at = @At("HEAD")
    )
    private void getBlazeFireSprites(FeatureFrameContext context, List<FlameFeatureRenderer.Submit> submits, CallbackInfo ci, @Share("blazeFire1") LocalRef<TextureAtlasSprite> blazeFire1, @Share("blazeFire2") LocalRef<TextureAtlasSprite> blazeFire2) {
        blazeFire1.set(context.atlasManager().get(BLAZE_FIRE_0));
        blazeFire2.set(context.atlasManager().get(BLAZE_FIRE_1));
    }

    @WrapOperation(
            method = "buildGroup",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/feature/FlameFeatureRenderer;prepare(Lnet/minecraft/client/renderer/feature/FlameFeatureRenderer$Submit;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V")
    )
    private void useBlazeFireSprites(FlameFeatureRenderer instance, FlameFeatureRenderer.Submit submit, VertexConsumer buffer, TextureAtlasSprite fire1, TextureAtlasSprite fire2, Operation<Void> original, @Share("blazeFire1") LocalRef<TextureAtlasSprite> blazeFire1, @Share("blazeFire2") LocalRef<TextureAtlasSprite> blazeFire2) {
        var isBlazeFire = BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(submit.entityRenderState().entityType).is(NetherArchivesTags.BLAZE_COLORED_FIRE);
        original.call(instance, submit, buffer, isBlazeFire ? blazeFire1.get() : fire1, isBlazeFire ? blazeFire2.get() : fire2);
    }
}
