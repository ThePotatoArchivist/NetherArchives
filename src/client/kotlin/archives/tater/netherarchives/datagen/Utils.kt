@file:Suppress("NOTHING_TO_INLINE")

package archives.tater.netherarchives.datagen

import net.minecraft.data.models.blockstates.Variant
import net.minecraft.data.models.blockstates.VariantProperties
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.resources.ResourceLocation
import java.util.*

internal inline fun ModelTemplate(vararg requiredTextureKeys: TextureSlot, parent: ResourceLocation? = null, variant: String? = null): ModelTemplate =
    ModelTemplate(Optional.ofNullable(parent), Optional.ofNullable(variant), *requiredTextureKeys)

internal fun Variant(
    model: ResourceLocation? = null,
    x: VariantProperties.Rotation? = null,
    y: VariantProperties.Rotation? = null,
    uvLock: Boolean? = null,
    weight: Int? = null,
): Variant = Variant().apply {
    model?.let { with(VariantProperties.MODEL, it) }
    x?.let { with(VariantProperties.X_ROT, it) }
    y?.let { with(VariantProperties.Y_ROT, it) }
    uvLock?.let { with(VariantProperties.UV_LOCK, it) }
    weight?.let { with(VariantProperties.WEIGHT, it) }
}

internal fun TextureMapping(vararg entries: Pair<TextureSlot, ResourceLocation>) = TextureMapping().apply {
    for ((key, id) in entries)
        put(key, id)
}
