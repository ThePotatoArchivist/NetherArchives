package archives.tater.netherarchives

import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.Util

object NetherArchivesTags {
    val MAGNETIC: TagKey<Block> = TagKey.of(RegistryKeys.BLOCK, Identifier(NetherArchives.NAMESPACE, "magnetic"))

    val BLAZE_FIRE_TARGET: TagKey<Block> =
        TagKey.of(RegistryKeys.BLOCK, Identifier(NetherArchives.NAMESPACE, "blaze_fire_target"))

    val ROTTEN_FLESH_FERMENTER: TagKey<Block> =
        TagKey.of(RegistryKeys.BLOCK, Identifier(NetherArchives.NAMESPACE, "rotten_flesh_fermenter"))

    val EMI_ROTTEN_FLESH_FERMENTER: TagKey<Item> =
        TagKey.of(RegistryKeys.ITEM, Identifier(NetherArchives.NAMESPACE, "emi/rotten_flesh_fermenter"))

    @JvmField
    val SKIS_CAN_WALK_ON: TagKey<Fluid> =
        TagKey.of(RegistryKeys.FLUID, Identifier(NetherArchives.NAMESPACE, "skis_can_walk_on"))
    val BURNS_WHEN_PADDLE: TagKey<Fluid> =
        TagKey.of(RegistryKeys.FLUID, Identifier(NetherArchives.NAMESPACE, "burns_when_paddle"))

    val <T> TagKey<T>.translationKey: String
        get() {
            return "tag.${Util.createTranslationKey(this.registry.value.toShortTranslationKey(), this.id)}"
        }
}
