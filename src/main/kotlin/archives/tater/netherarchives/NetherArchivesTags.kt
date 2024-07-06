package archives.tater.netherarchives

import net.minecraft.block.Block
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

    val <T> TagKey<T>.translationKey: String
        get() {
            return "tag.${Util.createTranslationKey(this.registry.value.toShortTranslationKey(), this.id)}"
        }
}
