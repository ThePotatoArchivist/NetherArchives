package archives.tater.netherarchives.client.render.entity.model

import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartNames
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeDeformation
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import java.util.function.Function

class SkisEntityModel<T : LivingEntity>(root: ModelPart, renderLayerFactory: Function<ResourceLocation, RenderType>) :
    HumanoidModel<T>(root, renderLayerFactory) {

    constructor(root: ModelPart) : this(root, RenderType::entityCutoutNoCull)

    companion object {
        fun getTexturedModelData(): LayerDefinition = LayerDefinition.create(MeshDefinition().apply { root.apply {
            addOrReplaceChild(
                PartNames.RIGHT_LEG, CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, CubeDeformation(1.0F))

                .texOffs(-16, 0)
                .addBox(-1.5F, 11.75F, -16.0F, 3.0F, 0.0F, 32.0F, CubeDeformation.NONE),
            PartPose.offset(-1.9F, 12.25F, 0.0F));

            addOrReplaceChild(
                PartNames.LEFT_LEG, CubeListBuilder.create()
                .texOffs(0, 0)
                .mirror()
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, CubeDeformation(1.0F))

                .texOffs(-16, 0)
                .addBox(-1.3F, 11.75F, -16.0F, 3.0F, 0.0F, 32.0F, CubeDeformation.NONE),
            PartPose.offset(1.9F, 12.25F, 0.0F))

            addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.ZERO)
            addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.ZERO)
            addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.ZERO)
            addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.ZERO)
            addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.ZERO)
        } }, 32, 32)
    }
}
