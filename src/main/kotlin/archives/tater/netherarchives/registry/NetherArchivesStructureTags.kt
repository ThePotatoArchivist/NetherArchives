package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.level.levelgen.structure.Structure

object NetherArchivesStructureTags {
    private fun create(path: String) = TagKey.create(Registries.STRUCTURE, NetherArchives.id(path))

    @JvmField val BLAZE_TORCH_LOCATED: TagKey<Structure> = create("blaze_torch_located")
}

internal typealias ModStructureTags = NetherArchivesStructureTags