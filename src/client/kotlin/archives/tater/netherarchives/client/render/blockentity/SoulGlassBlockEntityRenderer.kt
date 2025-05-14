package archives.tater.netherarchives.client.render.blockentity

import archives.tater.netherarchives.NetherArchivesClient
import archives.tater.netherarchives.block.entity.SoulGlassBlockEntity
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.util.math.MatrixStack

class SoulGlassBlockEntityRenderer(ctx: BlockEntityRendererFactory.Context) : BlockEntityRenderer<SoulGlassBlockEntity> {
//    val renderDispatcher = ctx.renderDispatcher

    override fun render(
        entity: SoulGlassBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        overlay: Int
    ) {
        NetherArchivesClient.renderingSoulGlass = true
//        val matrix = matrices.peek().positionMatrix
//        val vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEndPortal())
//
//        renderSide(entity, matrix, vertexConsumer, 0f, 1f, 0f, 1f, OFFSET, OFFSET, OFFSET, OFFSET, Direction.NORTH)
//        renderSide(entity, matrix, vertexConsumer, 0f, 1f, 1f, 0f, 1- OFFSET, 1- OFFSET, 1- OFFSET, 1- OFFSET, Direction.SOUTH)
//        renderSide(entity, matrix, vertexConsumer, OFFSET, OFFSET, 1f, 0f, 0f, 1f, 1f, 0f, Direction.WEST)
//        renderSide(entity, matrix, vertexConsumer, 1- OFFSET, 1- OFFSET, 0f, 1f, 0f, 1f, 1f, 0f, Direction.EAST)
//        renderSide(entity, matrix, vertexConsumer, 0f, 1f, 1-OFFSET, 1-OFFSET, 0f, 0f, 1f, 1f, Direction.UP)
//        renderSide(entity, matrix, vertexConsumer, 0f, 1f, OFFSET,  OFFSET, 1f, 1f, 0f, 0f, Direction.DOWN)
    }

//    private fun renderSide(
//        entity: SoulGlassBlockEntity,
//        model: Matrix4f,
//        vertices: VertexConsumer,
//        x1: Float,
//        x2: Float,
//        y1: Float,
//        y2: Float,
//        z1: Float,
//        z2: Float,
//        z3: Float,
//        z4: Float,
//        side: Direction
//    ) {
//        if (!entity.run { Block.shouldDrawSide(cachedState, world, pos, side, pos.offset(side)) }) return
//        vertices.vertex(model, x1, y1, z1)
//        vertices.vertex(model, x2, y1, z2)
//        vertices.vertex(model, x2, y2, z3)
//        vertices.vertex(model, x1, y2, z4)
//    }

    companion object {
        const val OFFSET = 0.001f
    }
}
