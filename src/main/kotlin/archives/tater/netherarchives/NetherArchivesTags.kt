package archives.tater.netherarchives

import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.Util

object NetherArchivesTags {
    val MAGNETIC: TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, Identifier(NetherArchives.MOD_ID, "magnetic"))

    val BLAZE_FIRE_TARGET: TagKey<Block> =
        TagKey.of(RegistryKeys.BLOCK, Identifier(NetherArchives.MOD_ID, "blaze_fire_target"))

    val ROTTEN_FLESH_FERMENTER: TagKey<Block> =
        TagKey.of(RegistryKeys.BLOCK, Identifier(NetherArchives.MOD_ID, "rotten_flesh_fermenter"))

    val BASALT_GEYSER_REPLACEABLE: TagKey<Block> =
        TagKey.of(RegistryKeys.BLOCK, Identifier(NetherArchives.MOD_ID, "basalt_geyser_replaceable"))
    val BASALT_GEYSER_REPLACEABLE_SUBMERGED: TagKey<Block> =
        TagKey.of(RegistryKeys.BLOCK, Identifier(NetherArchives.MOD_ID, "basalt_geyser_replaceable_submerged"))

    @JvmField
    val SKIS_CAN_WALK_ON: TagKey<Fluid> =
        TagKey.of(RegistryKeys.FLUID, Identifier(NetherArchives.MOD_ID, "skis_can_walk_on"))
    val BURNS_WHEN_PADDLE: TagKey<Fluid> =
        TagKey.of(RegistryKeys.FLUID, Identifier(NetherArchives.MOD_ID, "burns_when_paddle"))

    val <T> TagKey<T>.translationKey: String
        get() {
            return "tag.${Util.createTranslationKey(this.registry.value.toShortTranslationKey(), this.id)}"
        }
}
