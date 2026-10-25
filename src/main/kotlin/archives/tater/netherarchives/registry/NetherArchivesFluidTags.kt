package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.level.material.Fluid

object NetherArchivesFluidTags {
    private fun create(path: String) = TagKey.create(Registries.FLUID, NetherArchives.id(path))

    @JvmField val SKIS_CAN_WALK_ON: TagKey<Fluid> = create("skis_can_walk_on")
    @JvmField val BURNS_WHEN_PADDLE: TagKey<Fluid> = create("burns_when_paddle")
}