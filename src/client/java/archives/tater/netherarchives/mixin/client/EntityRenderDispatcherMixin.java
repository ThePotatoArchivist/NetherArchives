package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.NetherArchives;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Unique
    @SuppressWarnings("deprecation")
    private static final SpriteIdentifier BLAZE_FIRE_0 = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(NetherArchives.NAMESPACE, "block/blaze_fire_0"));
    @Unique
    @SuppressWarnings("deprecation")
    private static final SpriteIdentifier BLAZE_FIRE_1 = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(NetherArchives.NAMESPACE, "block/blaze_fire_1"));

    @ModifyVariable(
            method = "renderFire",
            ordinal = 0,
            at = @At(value = "STORE")
    )
    private Sprite setBlazeFireSprite0(Sprite originalSprite, MatrixStack matrices, VertexConsumerProvider vertexConsumers, Entity entity) {
        return entity instanceof BlazeEntity ? BLAZE_FIRE_0.getSprite() : originalSprite;
    }

    @ModifyVariable(
            method = "renderFire",
            ordinal = 1,
            at = @At(value = "STORE")
    )
    private Sprite setBlazeFireSprite1(Sprite originalSprite, MatrixStack matrices, VertexConsumerProvider vertexConsumers, Entity entity) {
        return entity instanceof BlazeEntity ? BLAZE_FIRE_1.getSprite() : originalSprite;
    }
}
