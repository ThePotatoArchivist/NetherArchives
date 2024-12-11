package archives.tater.netherarchives.client.render.entity.feature

import archives.tater.netherarchives.NetherArchives
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer
import net.minecraft.client.render.entity.feature.FeatureRendererContext
import net.minecraft.client.render.entity.model.SkeletonEntityModel
import net.minecraft.entity.mob.AbstractSkeletonEntity
import net.minecraft.util.Identifier

@Environment(value = EnvType.CLIENT)
class WitherSkeletonEyesFeatureRenderer<T : AbstractSkeletonEntity>(featureRendererContext: FeatureRendererContext<T, SkeletonEntityModel<T>>) :
    EyesFeatureRenderer<T, SkeletonEntityModel<T>>(featureRendererContext) {

    companion object {
        private val SKIN = RenderLayer.getEyes(
            Identifier(
                NetherArchives.MOD_ID,
                "textures/entity/skeleton/wither_skeleton_eyes.png"
            )
        )
    }

    override fun getEyesTexture(): RenderLayer {
        return SKIN
    }

}

