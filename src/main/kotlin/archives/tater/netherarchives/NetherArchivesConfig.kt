package archives.tater.netherarchives

import kotlinx.serialization.Serializable
import org.spaceserve.config.IConfigure

@Serializable
data class NetherArchivesConfig(
    var skeletonEyes: Boolean = true,
    var blazeEntityYellowFire: Boolean = true
) : IConfigure {
    override val fileName: String
        get() = "netherarchives"
}
