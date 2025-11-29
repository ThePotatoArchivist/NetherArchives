@file:Suppress("NOTHING_TO_INLINE")

package archives.tater.netherarchives.datagen

import net.minecraft.client.data.models.model.ModelTemplate
import net.minecraft.client.data.models.model.TextureMapping
import net.minecraft.client.data.models.model.TextureSlot
import net.minecraft.client.data.models.model.TexturedModel
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import java.util.*

internal inline fun ModelTemplate(vararg requiredTextureKeys: TextureSlot, parent: ResourceLocation? = null, variant: String? = null): ModelTemplate =
    ModelTemplate(Optional.ofNullable(parent), Optional.ofNullable(variant), *requiredTextureKeys)

internal fun TextureMapping(vararg entries: Pair<TextureSlot, ResourceLocation>) = TextureMapping().apply {
    for ((key, id) in entries)
        put(key, id)
}

internal inline fun texturedModelFactory(model: ModelTemplate, noinline texturesGetter: (Block) -> TextureMapping): TexturedModel.Provider =
    TexturedModel.createDefault(texturesGetter, model)
