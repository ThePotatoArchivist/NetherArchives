@file:Suppress("NOTHING_TO_INLINE")

package archives.tater.netherarchives.datagen

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.advancements.*
import net.minecraft.advancements.critereon.*
import net.minecraft.data.models.blockstates.Variant
import net.minecraft.data.models.blockstates.VariantProperties
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import java.util.*
import java.util.function.Consumer

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

fun DisplayInfo(
    id: ResourceLocation,
    icon: ItemStack,
    type: AdvancementType = AdvancementType.TASK,
    showToast: Boolean = true,
    announceChat: Boolean = true,
    hidden: Boolean = false,
    background: ResourceLocation? = null,
) = DisplayInfo(
    icon,
    Component.translatable(id.toLanguageKey("advancement", "title")),
    Component.translatable(id.toLanguageKey("advancement", "description")),
    Optional.ofNullable(background),
    type,
    showToast,
    announceChat,
    hidden
)

fun DisplayInfo(
    id: ResourceLocation,
    icon: ItemLike,
    type: AdvancementType = AdvancementType.TASK,
    showToast: Boolean = true,
    announceChat: Boolean = true,
    hidden: Boolean = false,
    background: ResourceLocation? = null,
) = DisplayInfo(id, icon.asItem().defaultInstance, type, showToast, announceChat, hidden, background)

fun Consumer<AdvancementHolder>.advancement(
    id: ResourceLocation,
    icon: ItemStack,
    type: AdvancementType = AdvancementType.TASK,
    showToast: Boolean = true,
    announceChat: Boolean = true,
    hidden: Boolean = false,
    background: ResourceLocation? = null,
    init: Advancement.Builder.() -> Unit
): AdvancementHolder = Advancement.Builder.advancement().apply {
    display(DisplayInfo(id, icon, type, showToast, announceChat, hidden, background))
    init()
}.build(id).also { accept(it) }

fun Consumer<AdvancementHolder>.advancement(
    id: ResourceLocation,
    icon: ItemLike,
    type: AdvancementType = AdvancementType.TASK,
    showToast: Boolean = true,
    announceChat: Boolean = true,
    hidden: Boolean = false,
    background: ResourceLocation? = null,
    init: Advancement.Builder.() -> Unit
): AdvancementHolder = advancement(id, icon.asItem().defaultInstance, type, showToast, announceChat, hidden, background, init)

fun playerPickedUpItemTrigger(
    item: ItemPredicate? = null,
    player: ContextAwarePredicate? = null,
    entity: ContextAwarePredicate? = null,
): Criterion<PickedUpItemTrigger.TriggerInstance> = PickedUpItemTrigger.TriggerInstance.thrownItemPickedUpByPlayer(
    Optional.ofNullable(player),
    Optional.ofNullable(item),
    Optional.ofNullable(entity)
)

fun ItemPredicate(init: ItemPredicate.Builder.() -> Unit): ItemPredicate = ItemPredicate.Builder.item().apply(init).build()

fun EntityPredicate(init: EntityPredicate.Builder.() -> Unit): EntityPredicate = EntityPredicate.Builder.entity().apply(init).build()

fun locationPredicate(init: LocationPredicate.Builder.() -> Unit): LocationPredicate.Builder = LocationPredicate.Builder.location().apply(init)

fun fluidPredicate(init: FluidPredicate.Builder.() -> Unit): FluidPredicate.Builder = FluidPredicate.Builder.fluid().apply(init)

fun FabricLanguageProvider.TranslationBuilder.addAdvancement(id: ResourceLocation, title: String, description: String) {
    add(id.toLanguageKey("advancement", "title"), title)
    add(id.toLanguageKey("advancement", "description"), description)
}