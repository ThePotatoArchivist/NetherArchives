package archives.tater.netherarchives.mixin.client;

import archives.tater.netherarchives.NetherArchivesClient;
import archives.tater.netherarchives.item.NetherArchivesItems;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Shadow @Final private ItemModels models;

    @ModifyExpressionValue(
            method = "renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getModel(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)Lnet/minecraft/client/render/model/BakedModel;")
    )
    private BakedModel replaceOarModel(BakedModel original, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ModelTransformationMode mode) {
        return stack.isOf(NetherArchivesItems.BASALT_OAR) && NetherArchivesClient.inHandRenderModes.contains(mode) ? models.getModelManager().getModel(NetherArchivesClient.BASALT_OAR_IN_HAND_ID) : original;
    }
}
