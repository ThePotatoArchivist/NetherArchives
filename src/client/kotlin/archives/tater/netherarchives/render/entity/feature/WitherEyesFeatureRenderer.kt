package archives.tater.netherarchives.render.entity.feature

import archives.tater.netherarchives.NetherArchives
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.entity.feature.FeatureRendererContext
import net.minecraft.client.render.entity.model.WitherEntityModel
import net.minecraft.entity.boss.WitherEntity
import net.minecraft.util.Identifier

@Environment(value = EnvType.CLIENT)
class WitherEyesFeatureRenderer<T : WitherEntity>(featureRendererContext: FeatureRendererContext<T, WitherEntityModel<T>>) :
    EntityEyesFeatureRenderer<T, WitherEntityModel<T>>(featureRendererContext) {

    companion object {
        private val SKIN = RenderLayer.getEyes(Identifier(NetherArchives.NAMESPACE, "textures/entity/wither/wither_eyes.png"))
        private val INVULNERABLE_SKIN = RenderLayer.getEyes(Identifier(NetherArchives.NAMESPACE, "textures/entity/wither/wither_invulnerable_eyes.png"))
    }

    override fun getEyesTexture(entity: T?): RenderLayer {
        if (entity == null) return SKIN
        val i: Int = entity.invulnerableTimer
        return if (i <= 0 || i <= 80 && i / 5 % 2 == 1) {
            SKIN
        } else INVULNERABLE_SKIN
    }
}
