package archives.tater.netherarchives.client.render.entity.feature

import archives.tater.netherarchives.NetherArchives
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer
import net.minecraft.client.render.entity.feature.FeatureRendererContext
import net.minecraft.client.render.entity.model.SkeletonEntityModel
import net.minecraft.entity.mob.AbstractSkeletonEntity

class WitherSkeletonEyesFeatureRenderer<T : AbstractSkeletonEntity>(featureRendererContext: FeatureRendererContext<T, SkeletonEntityModel<T>>) :
    EyesFeatureRenderer<T, SkeletonEntityModel<T>>(featureRendererContext) {

    companion object {
        private val SKIN =
            RenderLayer.getEyes(NetherArchives.id("textures/entity/skeleton/wither_skeleton_eyes.png"))
    }

    override fun getEyesTexture(): RenderLayer {
        return SKIN
    }

}

