package archives.tater.netherarchives.client.render.entity.feature

import net.minecraft.client.render.LightmapTextureManager.MAX_SKY_LIGHT_COORDINATE
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.command.OrderedRenderCommandQueue
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
        queue: OrderedRenderCommandQueue?,
        light: Int,
        state: T,
        limbAngle: Float,
        limbDistance: Float
    ) {
        queue!!.getBatchingQueue(1).submitModel(
                contextModel,
                state,
                matrices,
                getEyesTexture(state),
                MAX_SKY_LIGHT_COORDINATE,
                OverlayTexture.DEFAULT_UV,
                -1,
                null,
                state.outlineColor,
                null
            )
    }

    abstract fun getEyesTexture(state: T?): RenderLayer

    override fun getEyesTexture() = getEyesTexture(null)
}

