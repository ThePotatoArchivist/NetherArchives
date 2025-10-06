package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.NetherArchives;
import archives.tater.netherarchives.registry.NetherArchivesTags;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.render.command.FireCommandRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;

@Mixin(FireCommandRenderer.class)
public class EntityRenderDispatcherMixin {

    @Unique
    @SuppressWarnings("deprecation")
    private static final SpriteIdentifier BLAZE_FIRE_0 = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, NetherArchives.id("block/blaze_fire_0"));
    @Unique
    @SuppressWarnings("deprecation")
    private static final SpriteIdentifier BLAZE_FIRE_1 = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, NetherArchives.id("block/blaze_fire_1"));

    @ModifyExpressionValue(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack$Entry;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/entity/state/EntityRenderState;Lorg/joml/Quaternionf;Lnet/minecraft/client/texture/AtlasManager;)V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/model/ModelBaker;FIRE_0:Lnet/minecraft/client/util/SpriteIdentifier;")
    )
    private SpriteIdentifier setBlazeFireSprite0(SpriteIdentifier original, @Local(argsOnly = true) EntityRenderState renderState) {
        return renderState.entityType.isIn(NetherArchivesTags.BLAZE_COLORED_FIRE) ? BLAZE_FIRE_0 : original;
    }

    @ModifyExpressionValue(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack$Entry;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/entity/state/EntityRenderState;Lorg/joml/Quaternionf;Lnet/minecraft/client/texture/AtlasManager;)V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/model/ModelBaker;FIRE_1:Lnet/minecraft/client/util/SpriteIdentifier;")
    )
    private SpriteIdentifier setBlazeFireSprite1(SpriteIdentifier original, @Local(argsOnly = true) EntityRenderState renderState) {
        return renderState.entityType.isIn(NetherArchivesTags.BLAZE_COLORED_FIRE) ? BLAZE_FIRE_1 : original;
    }
}
