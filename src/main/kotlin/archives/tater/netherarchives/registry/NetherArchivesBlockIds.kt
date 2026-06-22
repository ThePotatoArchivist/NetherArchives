package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey

object NetherArchivesBlockIds {
    private fun create(id: Identifier) = ResourceKey.create(Registries.BLOCK, id)
    private fun create(path: String) = create(NetherArchives.id(path))

    val WALL_BLAZE_TORCH = create("wall_blaze_torch")
    val BLAZE_FIRE = create("blaze_fire")
}

internal typealias ModBlockIds = NetherArchivesBlockIds