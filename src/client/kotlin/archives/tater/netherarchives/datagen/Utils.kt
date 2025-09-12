@file:Suppress("NOTHING_TO_INLINE")

package archives.tater.netherarchives.datagen

import net.minecraft.data.client.BlockStateVariant
import net.minecraft.data.client.Model
import net.minecraft.data.client.TextureKey
import net.minecraft.data.client.TextureMap
import net.minecraft.data.client.VariantSettings
import net.minecraft.util.Identifier
import java.util.*

internal inline fun Model(vararg requiredTextureKeys: TextureKey, parent: Identifier? = null, variant: String? = null): Model =
    Model(Optional.ofNullable(parent), Optional.ofNullable(variant), *requiredTextureKeys)

internal fun BlockStateVariant(
    model: Identifier? = null,
    x: VariantSettings.Rotation? = null,
    y: VariantSettings.Rotation? = null,
    uvLock: Boolean? = null,
    weight: Int? = null,
): BlockStateVariant = BlockStateVariant().apply {
    model?.let { put(VariantSettings.MODEL, it) }
    x?.let { put(VariantSettings.X, it) }
    y?.let { put(VariantSettings.Y, it) }
    uvLock?.let { put(VariantSettings.UVLOCK, it) }
    weight?.let { put(VariantSettings.WEIGHT, it) }
}

internal fun TextureMap(vararg entries: Pair<TextureKey, Identifier>) = TextureMap().apply {
    for ((key, id) in entries)
        put(key, id)
}