package archives.tater.netherarchives.client.render.entity.feature

import archives.tater.netherarchives.NetherArchives
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.entity.feature.FeatureRendererContext
import net.minecraft.client.render.entity.model.WitherEntityModel
import net.minecraft.client.render.entity.state.WitherEntityRenderState
import net.minecraft.util.math.MathHelper

class WitherEyesFeatureRenderer(featureRendererContext: FeatureRendererContext<WitherEntityRenderState, WitherEntityModel>) :
    EntityEyesFeatureRenderer<WitherEntityRenderState, WitherEntityModel>(featureRendererContext) {

    override fun getEyesTexture(state: WitherEntityRenderState?): RenderLayer {
        if (state == null) return SKIN;
        val i = MathHelper.floor(state.invulnerableTimer);
        return if (i > 0 && (i > 80 || i / 5 % 2 != 1)) INVULNERABLE_SKIN else SKIN
    }

    companion object {
        private val SKIN =
            RenderLayer.getEyes(NetherArchives.id("textures/entity/wither/wither_eyes.png"))

        private val INVULNERABLE_SKIN =
            RenderLayer.getEyes(NetherArchives.id("textures/entity/wither/wither_invulnerable_eyes.png"))
    }
}
