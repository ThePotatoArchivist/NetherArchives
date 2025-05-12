package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.NetherArchives;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.BlazeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Unique
    @SuppressWarnings("deprecation")
    private static final SpriteIdentifier BLAZE_FIRE_0 = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, NetherArchives.id("block/blaze_fire_0"));
    @Unique
    @SuppressWarnings("deprecation")
    private static final SpriteIdentifier BLAZE_FIRE_1 = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, NetherArchives.id("block/blaze_fire_1"));

    @ModifyExpressionValue(
            method = "renderFire",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/model/ModelLoader;FIRE_0:Lnet/minecraft/client/util/SpriteIdentifier;")
    )
    private SpriteIdentifier setBlazeFireSprite0(SpriteIdentifier original, @Local(argsOnly = true) Entity entity) {
        return entity instanceof BlazeEntity ? BLAZE_FIRE_0 : original;
    }

    @ModifyExpressionValue(
            method = "renderFire",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/model/ModelLoader;FIRE_1:Lnet/minecraft/client/util/SpriteIdentifier;")
    )
    private SpriteIdentifier setBlazeFireSprite1(SpriteIdentifier original, @Local(argsOnly = true) Entity entity) {
        return entity instanceof BlazeEntity ? BLAZE_FIRE_1 : original;
    }
}
