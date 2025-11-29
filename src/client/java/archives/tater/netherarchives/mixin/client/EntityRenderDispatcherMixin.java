package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.NetherArchives;
import archives.tater.netherarchives.registry.NetherArchivesTags;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.renderer.feature.FlameFeatureRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;

@Mixin(FlameFeatureRenderer.class)
public class EntityRenderDispatcherMixin {

    @Unique
    @SuppressWarnings("deprecation")
    private static final Material BLAZE_FIRE_0 = new Material(TextureAtlas.LOCATION_BLOCKS, NetherArchives.id("block/blaze_fire_0"));
    @Unique
    @SuppressWarnings("deprecation")
    private static final Material BLAZE_FIRE_1 = new Material(TextureAtlas.LOCATION_BLOCKS, NetherArchives.id("block/blaze_fire_1"));

    @ModifyExpressionValue(
            method = "renderFlame(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lorg/joml/Quaternionf;Lnet/minecraft/client/resources/model/AtlasManager;)V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/resources/model/ModelBakery;FIRE_0:Lnet/minecraft/client/resources/model/Material;", opcode = Opcodes.GETSTATIC)
    )
    private Material setBlazeFireSprite0(Material original, @Local(argsOnly = true) EntityRenderState renderState) {
        return renderState.entityType.is(NetherArchivesTags.BLAZE_COLORED_FIRE) ? BLAZE_FIRE_0 : original;
    }

    @ModifyExpressionValue(
            method = "renderFlame(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lorg/joml/Quaternionf;Lnet/minecraft/client/resources/model/AtlasManager;)V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/resources/model/ModelBakery;FIRE_1:Lnet/minecraft/client/resources/model/Material;", opcode = Opcodes.GETSTATIC)
    )
    private Material setBlazeFireSprite1(Material original, @Local(argsOnly = true) EntityRenderState renderState) {
        return renderState.entityType.is(NetherArchivesTags.BLAZE_COLORED_FIRE) ? BLAZE_FIRE_1 : original;
    }
}
