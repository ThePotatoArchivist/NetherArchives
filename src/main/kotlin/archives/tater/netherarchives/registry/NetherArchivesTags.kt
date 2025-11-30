package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.core.Registry
import net.minecraft.core.registries.Registries.*
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.TagKey
import net.minecraft.util.Util
import net.minecraft.world.level.levelgen.structure.Structure
import net.minecraft.world.level.material.Fluid

object NetherArchivesTags {
    private fun <T: Any> of(registry: ResourceKey<Registry<T>>, path: String): TagKey<T> =
        TagKey.create(registry, NetherArchives.id(path))

    val MAGNETIC = of(BLOCK, "magnetic")
    val BLAZE_FIRE_TARGET = of(BLOCK, "blaze_fire_target")
    val ROTTEN_FLESH_FERMENTER = of(BLOCK, "rotten_flesh_fermenter")
    val BASALT_GEYSER_REPLACEABLE = of(BLOCK, "basalt_geyser_replaceable")
    val BASALT_GEYSER_REPLACEABLE_SUBMERGED = of(BLOCK, "basalt_geyser_replaceable_submerged")
    @JvmField
    val INVERTS_BEACON = of(BLOCK, "inverts_beacon")
    val REVEALS_INVISIBLES = of(BLOCK, "reveals_invisibles")

    val BASALT_EQUIPMENT_REPAIR = of(ITEM, "basalt_equipment_repair")
    val SKIS = of(ITEM, "skis")

    @JvmField
    val SKIS_CAN_WALK_ON: TagKey<Fluid> = of(FLUID, "skis_can_walk_on")
    val BURNS_WHEN_PADDLE: TagKey<Fluid> = of(FLUID, "burns_when_paddle")

    val NON_SHATTER_PROJECTILES = of(ENTITY_TYPE, "non_shatter_projectile")
    val NON_CHAIN_SHATTER_PROJECTILES = of(ENTITY_TYPE, "non_chain_shatter_projectile")
    @JvmField
    val BLAZE_COLORED_FIRE = of(ENTITY_TYPE, "blaze_colored_fire")

    val BLAZE_TORCH_LOCATED: TagKey<Structure> = of(STRUCTURE, "blaze_torch_located")

    val <T: Any> TagKey<T>.translationKey: String
        get() {
            return "tag.${Util.makeDescriptionId(this.registry.identifier().toShortLanguageKey(), this.location)}"
        }
}
