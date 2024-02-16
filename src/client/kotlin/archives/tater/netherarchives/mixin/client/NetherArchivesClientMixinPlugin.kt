package archives.tater.netherarchives.mixin.client

import archives.tater.netherarchives.NetherArchives
import org.objectweb.asm.tree.ClassNode
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin
import org.spongepowered.asm.mixin.extensibility.IMixinInfo

class NetherArchivesClientMixinPlugin : IMixinConfigPlugin {
    companion object {
        val eyesMixins = listOf(
            "archives.tater.netherarchives.mixin.client.WitherSkeletonEntityRendererMixin",
            "archives.tater.netherarchives.mixin.client.WitherEntityRendererMixin",
            "archives.tater.netherarchives.mixin.client.WitherArmorFeatureRendererMixin",
        )

        val blazeMixin = "archives.tater.netherarchives.mixin.client.EntityRenderDispatcherMixin"
    }

    override fun onLoad(mixinPackage: String?) {}

    override fun getRefMapperConfig(): String? = null

    override fun shouldApplyMixin(targetClassName: String, mixinClassName: String): Boolean {
        return when(mixinClassName) {
            in eyesMixins -> NetherArchives.config.skeletonEyes
            blazeMixin -> NetherArchives.config.blazeEntityYellowFire
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
