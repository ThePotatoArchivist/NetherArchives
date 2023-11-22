package archives.tater.netherarchives.entity

import archives.tater.netherarchives.NetherArchives
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object NetherArchivesEntities {
    val BLAZE_LANTERN: EntityType<BlazeLanternEntity> = Registry.register(
        Registries.ENTITY_TYPE,
        Identifier(NetherArchives.NAMESPACE, "blaze_lantern"),
        FabricEntityTypeBuilder.create(SpawnGroup.MISC, ::BlazeLanternEntity).apply {
            dimensions(EntityDimensions.fixed(0.25f, 0.25f))
            trackRangeChunks(4)
            trackedUpdateRate(10)
        }.build()
    )
}
