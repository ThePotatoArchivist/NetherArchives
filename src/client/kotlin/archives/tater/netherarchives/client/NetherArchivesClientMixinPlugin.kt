package archives.tater.netherarchives.client

import archives.tater.netherarchives.NetherArchivesClient
import org.objectweb.asm.tree.ClassNode
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin
import org.spongepowered.asm.mixin.extensibility.IMixinInfo

class NetherArchivesClientMixinPlugin : IMixinConfigPlugin {
    companion object {
        const val WITHER_ARMOR_MIXIN = "archives.tater.netherarchives.mixin.client.WitherArmorFeatureRendererMixin"
        const val BLAZE_MIXIN = "archives.tater.netherarchives.mixin.client.EntityRenderDispatcherMixin"
    }

    override fun onLoad(mixinPackage: String?) {}

    override fun getRefMapperConfig(): String? = null

    override fun shouldApplyMixin(targetClassName: String, mixinClassName: String): Boolean {
        return when(mixinClassName) {
            WITHER_ARMOR_MIXIN -> NetherArchivesClient.config.skeletonEyes
            BLAZE_MIXIN -> NetherArchivesClient.config.blazeEntityFire
            else -> true
        }
    }

    override fun acceptTargets(myTargets: MutableSet<String>?, otherTargets: MutableSet<String>?) {}

    override fun getMixins(): MutableList<String>? = null

    override fun preApply(
        targetClassName: String?,
        targetClass: ClassNode?,
        mixinClassName: String?,
        mixinInfo: IMixinInfo?
    ) {}

    override fun postApply(
        targetClassName: String?,
        targetClass: ClassNode?,
        mixinClassName: String?,
        mixinInfo: IMixinInfo?
    ) {}
}
