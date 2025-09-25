package archives.tater.netherarchives.client.render.entity.model

import net.minecraft.client.model.*
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.entity.model.BipedEntityModel
import net.minecraft.client.render.entity.model.EntityModelPartNames
import net.minecraft.client.render.entity.state.BipedEntityRenderState
import net.minecraft.util.Identifier
import java.util.function.Function

class SkisEntityModel<T : BipedEntityRenderState>(root: ModelPart, renderLayerFactory: Function<Identifier, RenderLayer>) :
    BipedEntityModel<T>(root, renderLayerFactory) {

    constructor(root: ModelPart) : this(root, RenderLayer::getEntityCutoutNoCull)

    companion object {
        fun getTexturedModelData(): TexturedModelData = TexturedModelData.of(ModelData().apply { root.apply {
            addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create()
                .uv(0, 0)
                .cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, Dilation(1.0F))

                .uv(-16, 0)
                .cuboid(-1.5F, 11.75F, -16.0F, 3.0F, 0.0F, 32.0F, Dilation.NONE),
            ModelTransform.origin(-1.9F, 12.25F, 0.0F));

            addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create()
                .uv(0, 0)
                .mirrored()
                .cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, Dilation(1.0F))

                .uv(-16, 0)
                .cuboid(-1.3F, 11.75F, -16.0F, 3.0F, 0.0F, 32.0F, Dilation.NONE),
            ModelTransform.origin(1.9F, 12.25F, 0.0F))

            addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.NONE).apply {
                addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.NONE)
            }
            addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.NONE)
            addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.NONE)
            addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.NONE)
        } }, 32, 32)
    }
}
