package archives.tater.netherarchives.client.render.entity.feature

import archives.tater.netherarchives.NetherArchives
import net.minecraft.client.model.monster.wither.WitherBossModel
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.state.WitherRenderState
import net.minecraft.client.renderer.rendertype.RenderType
import net.minecraft.client.renderer.rendertype.RenderTypes
import net.minecraft.util.Mth

class WitherEyesFeatureRenderer(featureRendererContext: RenderLayerParent<WitherRenderState, WitherBossModel>) :
    EntityEyesFeatureRenderer<WitherRenderState, WitherBossModel>(featureRendererContext) {

    override fun getEyesTexture(state: WitherRenderState?): RenderType {
        if (state == null) return SKIN
        val i = Mth.floor(state.invulnerableTicks)
        return if (i > 0 && (i > 80 || i / 5 % 2 != 1)) INVULNERABLE_SKIN else SKIN
    }

    companion object {
        private val SKIN =
            RenderTypes.eyes(NetherArchives.id("textures/entity/wither/wither_eyes.png"))

        private val INVULNERABLE_SKIN =
            RenderTypes.eyes(NetherArchives.id("textures/entity/wither/wither_invulnerable_eyes.png"))
    }
}
