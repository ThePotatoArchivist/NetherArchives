package archives.tater.netherarchives.entity

import archives.tater.netherarchives.NetherArchives
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object NetherArchivesEntities {
    val BLAZE_LANTERN: EntityType<BlazeLanternEntity> = Registry.register(
        Registries.ENTITY_TYPE,
        NetherArchives.id("blaze_lantern"),
        EntityType.Builder.create(::BlazeLanternEntity, SpawnGroup.MISC).apply {
            dimensions(0.25f, 0.25f)
            maxTrackingRange(4)
            trackingTickInterval(10)
        }.build()
    )

    fun register() {}
}
