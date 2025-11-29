package archives.tater.netherarchives.client.render.entity.feature

import archives.tater.netherarchives.NetherArchives
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.layers.EyesLayer
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.model.SkeletonModel
import net.minecraft.client.renderer.entity.state.SkeletonRenderState

class WitherSkeletonEyesFeatureRenderer<T : SkeletonRenderState>(featureRendererContext: RenderLayerParent<T, SkeletonModel<T>>) :
    EyesLayer<T, SkeletonModel<T>>(featureRendererContext) {

    companion object {
        private val SKIN =
            RenderType.eyes(NetherArchives.id("textures/entity/skeleton/wither_skeleton_eyes.png"))
    }

    override fun renderType(): RenderType {
        return SKIN
    }

}

