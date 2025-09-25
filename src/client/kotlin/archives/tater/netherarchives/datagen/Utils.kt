@file:Suppress("NOTHING_TO_INLINE")

package archives.tater.netherarchives.datagen

import net.minecraft.client.data.Model
import net.minecraft.client.data.TextureKey
import net.minecraft.client.data.TextureMap
import net.minecraft.util.Identifier
import java.util.*

internal inline fun Model(vararg requiredTextureKeys: TextureKey, parent: Identifier? = null, variant: String? = null): Model =
    Model(Optional.ofNullable(parent), Optional.ofNullable(variant), *requiredTextureKeys)

internal fun TextureMap(vararg entries: Pair<TextureKey, Identifier>) = TextureMap().apply {
    for ((key, id) in entries)
        put(key, id)
}