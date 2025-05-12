package archives.tater.netherarchives.client.render.entity.feature

import archives.tater.netherarchives.NetherArchives
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.entity.feature.FeatureRendererContext
import net.minecraft.client.render.entity.model.WitherEntityModel
import net.minecraft.entity.boss.WitherEntity

class WitherEyesFeatureRenderer<T : WitherEntity>(featureRendererContext: FeatureRendererContext<T, WitherEntityModel<T>>) :
    EntityEyesFeatureRenderer<T, WitherEntityModel<T>>(featureRendererContext) {

    override fun getEyesTexture(entity: T?): RenderLayer {
        if (entity == null) return SKIN
        val i: Int = entity.invulnerableTimer
        return if (i <= 0 || i <= 80 && i / 5 % 2 == 1) {
            SKIN
        } else INVULNERABLE_SKIN
    }

    companion object {
        private val SKIN =
            RenderLayer.getEyes(NetherArchives.id("textures/entity/wither/wither_eyes.png"))

        private val INVULNERABLE_SKIN =
            RenderLayer.getEyes(NetherArchives.id("textures/entity/wither/wither_invulnerable_eyes.png"))
    }
}
