package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.fluid.Fluid
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys.*
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Util
import net.minecraft.world.gen.structure.Structure

object NetherArchivesTags {
    private fun <T> of(registry: RegistryKey<Registry<T>>, path: String): TagKey<T> =
        TagKey.of(registry, NetherArchives.id(path))

    val MAGNETIC = of(BLOCK, "magnetic")
    val BLAZE_FIRE_TARGET = of(BLOCK, "blaze_fire_target")
    val ROTTEN_FLESH_FERMENTER = of(BLOCK, "rotten_flesh_fermenter")
    val BASALT_GEYSER_REPLACEABLE = of(BLOCK, "basalt_geyser_replaceable")
    val BASALT_GEYSER_REPLACEABLE_SUBMERGED = of(BLOCK, "basalt_geyser_replaceable_submerged")
    @JvmField
    val INVERTS_BEACON = of(BLOCK, "inverts_beacon")
    val REVEALS_INVISIBLES = of(BLOCK, "reveals_invisibles")

    @JvmField
    val SKIS_CAN_WALK_ON: TagKey<Fluid> = of(FLUID, "skis_can_walk_on")
    val BURNS_WHEN_PADDLE: TagKey<Fluid> = of(FLUID, "burns_when_paddle")

    val NON_CHAIN_SHATTER_PROJECTILES = of(ENTITY_TYPE, "non_chain_shatter_projectile")

    val BLAZE_TORCH_LOCATED: TagKey<Structure> = of(STRUCTURE, "blaze_torch_located")

    val <T> TagKey<T>.translationKey: String
        get() {
            return "tag.${Util.createTranslationKey(this.registry.value.toShortTranslationKey(), this.id)}"
        }
}
