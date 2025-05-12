package archives.tater.netherarchives

data class NetherArchivesConfig(
    var skeletonEyes: Boolean = true,
    var blazeEntityYellowFire: Boolean = true
)  {
    val fileName: String
        get() = "netherarchives"
}
