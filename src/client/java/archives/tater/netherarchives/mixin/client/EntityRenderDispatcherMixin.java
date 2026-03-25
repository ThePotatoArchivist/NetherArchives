package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.NetherArchives;
import archives.tater.netherarchives.registry.NetherArchivesTags;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.objectweb.asm.Opcodes;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.feature.FlameFeatureRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.registries.BuiltInRegistries;

@Mixin(FlameFeatureRenderer.class)
public class EntityRenderDispatcherMixin {

    @Unique
    @SuppressWarnings("deprecation")
    private static final SpriteId BLAZE_FIRE_0 = new SpriteId(TextureAtlas.LOCATION_BLOCKS, NetherArchives.id("block/blaze_fire_0"));
    @Unique
    @SuppressWarnings("deprecation")
    private static final SpriteId BLAZE_FIRE_1 = new SpriteId(TextureAtlas.LOCATION_BLOCKS, NetherArchives.id("block/blaze_fire_1"));

    @ModifyExpressionValue(
            method = "renderFlame",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/resources/model/ModelBakery;FIRE_0:Lnet/minecraft/client/resources/model/sprite/SpriteId;", opcode = Opcodes.GETSTATIC)
    )
    private SpriteId setBlazeFireSprite0(SpriteId original, @Local(argsOnly = true) EntityRenderState renderState) {
        return BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(renderState.entityType).is(NetherArchivesTags.BLAZE_COLORED_FIRE) ? BLAZE_FIRE_0 : original;
    }

    @ModifyExpressionValue(
            method = "renderFlame",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/resources/model/ModelBakery;FIRE_1:Lnet/minecraft/client/resources/model/sprite/SpriteId;", opcode = Opcodes.GETSTATIC)
    )
    private SpriteId setBlazeFireSprite1(SpriteId original, @Local(argsOnly = true) EntityRenderState renderState) {
        return BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(renderState.entityType).is(NetherArchivesTags.BLAZE_COLORED_FIRE) ? BLAZE_FIRE_1 : original;
    }
}
