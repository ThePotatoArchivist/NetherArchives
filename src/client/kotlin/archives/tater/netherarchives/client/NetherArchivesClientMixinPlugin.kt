package archives.tater.netherarchives.client

import archives.tater.netherarchives.NetherArchivesClientConfig
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin
import org.spongepowered.asm.mixin.extensibility.IMixinInfo
import org.objectweb.asm.tree.ClassNode

class NetherArchivesClientMixinPlugin : IMixinConfigPlugin {
    companion object {
        const val WITHER_ARMOR_MIXIN = "archives.tater.netherarchives.mixin.client.WitherArmorFeatureRendererMixin"
        const val WITHER_SKULL_MIXIN = "archives.tater.netherarchives.mixin.client.WitherSkullRendererMixin"
        const val BLAZE_MIXIN = "archives.tater.netherarchives.mixin.client.EntityRenderDispatcherMixin"
    }

    override fun onLoad(mixinPackage: String?) {}

    override fun getRefMapperConfig(): String? = null

    override fun shouldApplyMixin(targetClassName: String, mixinClassName: String): Boolean {
        return when(mixinClassName) {
            WITHER_ARMOR_MIXIN, WITHER_SKULL_MIXIN -> NetherArchivesClientConfig.config.skeletonEyes
            BLAZE_MIXIN -> NetherArchivesClientConfig.config.blazeEntityFire
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
