package archives.tater.netherarchives.client.render.entity.feature

import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.layers.EyesLayer
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.model.EntityModel
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.world.entity.Entity

/**
 * Thought this was useful but actually wasn't
 */
abstract class EntityEyesFeatureRenderer<T : Entity, M : EntityModel<T>>(featureRendererContext: RenderLayerParent<T, M>?) :
    EyesLayer<T, M>(featureRendererContext) {
    override fun render(
        matrices: PoseStack,
        vertexConsumers: MultiBufferSource,
        light: Int,
        entity: T,
        limbAngle: Float,
        limbDistance: Float,
        tickDelta: Float,
        animationProgress: Float,
        headYaw: Float,
        headPitch: Float
    ) {
        this.parentModel.renderToBuffer(
            matrices,
            vertexConsumers.getBuffer(getEyesTexture(entity)),
            0xF00000,
            OverlayTexture.NO_OVERLAY
        )
    }

    abstract fun getEyesTexture(entity: T?): RenderType

    override fun renderType() = getEyesTexture(null)
}

