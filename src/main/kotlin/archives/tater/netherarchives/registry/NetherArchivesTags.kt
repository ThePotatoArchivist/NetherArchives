package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries.*
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.TagKey
import net.minecraft.world.level.levelgen.structure.Structure
import net.minecraft.world.level.material.Fluid

object NetherArchivesTags {
    private fun <T: Any> create(registry: ResourceKey<Registry<T>>, path: String): TagKey<T> =
        TagKey.create(registry, NetherArchives.id(path))

    val MAGNETIC = create(BLOCK, "magnetic")
    val ROTTEN_FLESH_FERMENTER = create(BLOCK, "rotten_flesh_fermenter")
    val BASALT_GEYSER_REPLACEABLE = create(BLOCK, "basalt_geyser_replaceable")
    val BASALT_GEYSER_REPLACEABLE_SUBMERGED = create(BLOCK, "basalt_geyser_replaceable_submerged")
    @JvmField
    val INVERTS_BEACON = create(BLOCK, "inverts_beacon")
    val REVEALS_INVISIBLES = create(BLOCK, "reveals_invisibles")

    val BASALT_EQUIPMENT_REPAIR = create(ITEM, "basalt_equipment_repair")
    val SKIS = create(ITEM, "skis")
    val ROTTEN_FLESH_FERMENTER_ITEM = create(ITEM, "rotten_flesh_fermenter")

    @JvmField
    val SKIS_CAN_WALK_ON: TagKey<Fluid> = create(FLUID, "skis_can_walk_on")
    val BURNS_WHEN_PADDLE: TagKey<Fluid> = create(FLUID, "burns_when_paddle")

    val NON_SHATTER_PROJECTILES = create(ENTITY_TYPE, "non_shatter_projectile")
    val NON_CHAIN_SHATTER_PROJECTILES = create(ENTITY_TYPE, "non_chain_shatter_projectile")
    @JvmField
    val BLAZE_COLORED_FIRE = create(ENTITY_TYPE, "blaze_colored_fire")

    val BLAZE_TORCH_LOCATED: TagKey<Structure> = create(STRUCTURE, "blaze_torch_located")
}

internal typealias ModTags = NetherArchivesTags