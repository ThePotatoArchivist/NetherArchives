package archives.tater.netherarchives.client.render.entity.layer

import archives.tater.netherarchives.NetherArchivesClient
import archives.tater.netherarchives.client.render.entity.model.SkisEntityModel
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.model.geom.EntityModelSet
import net.minecraft.client.renderer.SubmitNodeCollector
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.client.renderer.entity.state.HumanoidRenderState
import net.minecraft.client.resources.model.EquipmentClientInfo
import net.minecraft.core.component.DataComponents
import kotlin.jvm.optionals.getOrNull

class SkisLayer<S : HumanoidRenderState, M : HumanoidModel<S>, A : HumanoidModel<S>>(
    renderer: RenderLayerParent<S, M>,
    modelSet: EntityModelSet,
    private val equipmentRenderer: EquipmentLayerRenderer,
) : RenderLayer<S, M>(renderer) {

    private val model = SkisEntityModel<S>(modelSet.bakeLayer(NetherArchivesClient.SKIS_MODEL_LAYER))

    override fun submit(
        poseStack: PoseStack,
        submitNodeCollector: SubmitNodeCollector,
        lightCoords: Int,
        state: S,
        yRot: Float,
        xRot: Float
    ) {
        val equipment = state.feetEquipment
        val assetId = equipment[DataComponents.EQUIPPABLE]?.assetId?.getOrNull() ?: return
        equipmentRenderer.renderLayers(
            EquipmentClientInfo.LayerType.NETHERARCHIVES_SKIS,
            assetId,
            model,
            state,
            equipment,
            poseStack,
            submitNodeCollector,
            lightCoords,
            null,
            state.outlineColor,
            1
        )
    }
}