package archives.tater.netherarchives.client.render.entity.feature

import archives.tater.netherarchives.NetherArchives
import net.minecraft.client.model.monster.skeleton.SkeletonModel
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.EyesLayer
import net.minecraft.client.renderer.entity.state.SkeletonRenderState
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.rendertype.RenderTypes

class WitherSkeletonEyesFeatureRenderer<T : SkeletonRenderState>(featureRendererContext: RenderLayerParent<T, SkeletonModel<T>>) :
    EyesLayer<T, SkeletonModel<T>>(featureRendererContext) {

    companion object {
        private val SKIN =
            RenderTypes.eyes(NetherArchives.id("textures/entity/skeleton/wither_skeleton_eyes.png"))
    }

    override fun renderType(): RenderType {
        return SKIN
    }

}

