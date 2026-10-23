package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey

object NetherArchivesBlockTags {
    private fun create(path: String) = TagKey.create(Registries.BLOCK, NetherArchives.id(path))

    @JvmField val MAGNETIC = create("magnetic")
    @JvmField val ROTTEN_FLESH_FERMENTER = create("rotten_flesh_fermenter")
    @JvmField val BASALT_GEYSER_REPLACEABLE = create("basalt_geyser_replaceable")
    @JvmField val BASALT_GEYSER_REPLACEABLE_SUBMERGED = create("basalt_geyser_replaceable_submerged")
    @JvmField val INVERTS_BEACON = create("inverts_beacon")
    @JvmField val REVEALS_INVISIBLES = create("reveals_invisibles")
}
