package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey

object NetherArchivesEntityTags {
    private fun create(path: String) = TagKey.create(Registries.ENTITY_TYPE, NetherArchives.id(path))

    @JvmField val NON_SHATTER_PROJECTILES = create("non_shatter_projectile")
    @JvmField val NON_CHAIN_SHATTER_PROJECTILES = create("non_chain_shatter_projectile")
    @JvmField val BLAZE_COLORED_FIRE = create("blaze_colored_fire")
}

internal typealias ModEntityTags = NetherArchivesEntityTags