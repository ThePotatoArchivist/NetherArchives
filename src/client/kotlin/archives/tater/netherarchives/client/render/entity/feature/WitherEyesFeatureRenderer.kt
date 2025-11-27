package archives.tater.netherarchives.client.render.entity.feature

import archives.tater.netherarchives.NetherArchives
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.model.WitherBossModel
import net.minecraft.world.entity.boss.wither.WitherBoss

class WitherEyesFeatureRenderer<T : WitherBoss>(featureRendererContext: RenderLayerParent<T, WitherBossModel<T>>) :
    EntityEyesFeatureRenderer<T, WitherBossModel<T>>(featureRendererContext) {

    override fun getEyesTexture(entity: T?): RenderType {
        if (entity == null) return SKIN
        val i: Int = entity.invulnerableTicks
        return if (i <= 0 || i <= 80 && i / 5 % 2 == 1) {
            SKIN
        } else INVULNERABLE_SKIN
    }

    companion object {
        private val SKIN =
            RenderType.eyes(NetherArchives.id("textures/entity/wither/wither_eyes.png"))

        private val INVULNERABLE_SKIN =
            RenderType.eyes(NetherArchives.id("textures/entity/wither/wither_invulnerable_eyes.png"))
    }
}
