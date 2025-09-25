package archives.tater.netherarchives.client.render.entity.feature

import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer
import net.minecraft.client.render.entity.feature.FeatureRendererContext
import net.minecraft.client.render.entity.model.EntityModel
import net.minecraft.client.render.entity.state.EntityRenderState
import net.minecraft.client.util.math.MatrixStack

/**
 * Thought this was useful but actually wasn't
 */
abstract class EntityEyesFeatureRenderer<T : EntityRenderState, M : EntityModel<T>>(featureRendererContext: FeatureRendererContext<T, M>?) :
    EyesFeatureRenderer<T, M>(featureRendererContext) {
    override fun render(
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        entity: T,
        limbAngle: Float,
        limbDistance: Float
    ) {
        this.contextModel.render(
            matrices,
            vertexConsumers.getBuffer(getEyesTexture(entity)),
            0xF00000,
            OverlayTexture.DEFAULT_UV
        )
    }

    abstract fun getEyesTexture(entity: T?): RenderLayer

    override fun getEyesTexture() = getEyesTexture(null)
}

