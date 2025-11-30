package archives.tater.netherarchives.client.render.entity.feature

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.model.EntityModel
import net.minecraft.client.renderer.LightTexture.FULL_SKY
import net.minecraft.client.renderer.SubmitNodeCollector
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.EyesLayer
import net.minecraft.client.renderer.entity.state.EntityRenderState
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture

/**
 * Thought this was useful but actually wasn't
 */
abstract class EntityEyesFeatureRenderer<T : EntityRenderState, M : EntityModel<T>>(featureRendererContext: RenderLayerParent<T, M>) :
    EyesLayer<T, M>(featureRendererContext) {

    override fun submit(
        matrices: PoseStack,
        queue: SubmitNodeCollector,
        light: Int,
        state: T,
        limbAngle: Float,
        limbDistance: Float
    ) {
        queue.order(1).submitModel(
            parentModel,
                state,
                matrices,
                getEyesTexture(state),
            FULL_SKY,
                OverlayTexture.NO_OVERLAY,
                -1,
                null,
                state.outlineColor,
                null
            )
    }

    abstract fun getEyesTexture(state: T?): RenderType

    override fun renderType() = getEyesTexture(null)
}

