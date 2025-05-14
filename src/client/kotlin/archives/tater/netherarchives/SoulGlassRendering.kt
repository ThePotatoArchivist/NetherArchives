package archives.tater.netherarchives

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents.ClientStarted
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents.AfterEntities
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents.BeforeEntities
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gl.Framebuffer
import net.minecraft.client.gl.SimpleFramebuffer
import net.minecraft.client.texture.AbstractTexture
import net.minecraft.resource.ResourceManager

object SoulGlassRendering : BeforeEntities, AfterEntities, ClientStarted {
    @JvmStatic
    val invisibleFramebuffer: Framebuffer by lazy {
        MinecraftClient.getInstance().framebuffer.run { SimpleFramebuffer(viewportWidth, viewportHeight, true, false) }
    }

    val invisibleFramebufferTexture by lazy {
        object : AbstractTexture() {
            override fun load(manager: ResourceManager?) {
            }

            override fun getGlId(): Int = invisibleFramebuffer.colorAttachment

            override fun clearGlId() {
                // no-op
            }

            override fun close() {
                // no-op

            }
        }
    }

    override fun beforeEntities(context: WorldRenderContext) {
//        invisibleFramebuffer.clear(MinecraftClient.IS_SYSTEM_MAC)
//            invisibleFramebuffer.beginWrite(false)
//
//            context.gameRenderer().client.itemRenderer.renderItem(Items.GLASS_BOTTLE.defaultStack, ModelTransformationMode.NONE, LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, context.matrixStack(), context.consumers(), context.world(), 0)
//
//            invisibleFramebuffer.endWrite()
//            MinecraftClient.getInstance().framebuffer.beginWrite(false)
    }

    override fun afterEntities(context: WorldRenderContext) {
        invisibleFramebuffer.copyDepthFrom(MinecraftClient.getInstance().framebuffer)
//            context.worldRenderer().drawEntityOutlinesFramebuffer()
        // or?
        RenderSystem.enableBlend()
        RenderSystem.blendFuncSeparate(
            GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE
        )
        invisibleFramebuffer.draw(MinecraftClient.getInstance().window.framebufferWidth, MinecraftClient.getInstance().window.framebufferHeight, false);
        RenderSystem.disableBlend()
        RenderSystem.defaultBlendFunc()
//
        MinecraftClient.getInstance().framebuffer.beginWrite(false)
    }

    override fun onClientStarted(client: MinecraftClient) {
        client.textureManager.registerTexture(NetherArchives.id("invisible_entities"), invisibleFramebufferTexture)
    }

    fun register() {
        ClientLifecycleEvents.CLIENT_STARTED.register(this)

        WorldRenderEvents.BEFORE_ENTITIES.register(this)
        WorldRenderEvents.AFTER_ENTITIES.register(this)
    }

}