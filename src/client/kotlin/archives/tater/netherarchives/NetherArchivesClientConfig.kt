package archives.tater.netherarchives

import folk.sisby.kaleido.api.WrappedConfig
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment

class NetherArchivesClientConfig : WrappedConfig() {
    @Comment("Whether wither skeletons and withers should have soul-colored emissive eyes. Requires restart.")
    var skeletonEyes: Boolean = true
        private set
    @Comment("Whether blazes should render with blaze fire. Requires restart.")
    var blazeEntityFire: Boolean = true
        private set
}
