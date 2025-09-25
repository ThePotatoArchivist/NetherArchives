package archives.tater.netherarchives.datagen.builder

import net.minecraft.data.recipe.*
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.recipe.*
import net.minecraft.recipe.AbstractCookingRecipe.RecipeFactory
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier

// Shaped

fun CraftingRecipeJsonBuilder.offerTo(exporter: RecipeExporter, recipeId: Identifier) {
    offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, recipeId))
}

context(exporter: RecipeExporter)
inline fun RecipeGenerator.shaped(
    category: RecipeCategory,
    outputItem: ItemConvertible,
    count: Int = 1,
    recipeId: Identifier = outputItem.asItem().id,
    init: ShapedRecipeJsonBuilder.() -> Unit
) {
    createShaped(category, outputItem, count).apply(init)
        .offerTo(exporter, recipeId)
}

fun ShapedRecipeJsonBuilder.patterns(rows: String) {
    rows.trimIndent().split('\n').forEach(::pattern)
}

context(recipeGenerator: RecipeGenerator)
fun ShapedRecipeJsonBuilder.itemCriterion(item: ItemConvertible) {
    criterion(RecipeGenerator.hasItem(item), recipeGenerator.conditionsFromItem(item))
}

inline fun ShapedRecipeJsonBuilder.inputs(init: ShapedIngredientsBuilder.() -> Unit) {
    ShapedIngredientsBuilder(this).init()
}

class ShapedIngredientsBuilder(private val recipeBuilder: ShapedRecipeJsonBuilder) {
    infix fun Char.to(item: ItemConvertible) {
        recipeBuilder.input(this, item)
    }

    infix fun Char.to(ingredient: Ingredient) {
        recipeBuilder.input(this, ingredient)
    }

    infix fun Char.to(tagKey: TagKey<Item>) {
        recipeBuilder.input(this, tagKey)
    }
}

// Shapeless Recipe

context(exporter: RecipeExporter)
inline fun RecipeGenerator.shapeless(
    category: RecipeCategory,
    outputItem: ItemConvertible,
    count: Int = 1,
    recipeId: Identifier = outputItem.asItem().id,
    init: ShapelessRecipeJsonBuilder.() -> Unit
) {
    createShapeless(category, outputItem, count).apply(init)
        .offerTo(exporter, recipeId)
}

context(recipeGenerator: RecipeGenerator)
fun ShapelessRecipeJsonBuilder.itemCriterion(item: ItemConvertible) {
    criterion(RecipeGenerator.hasItem(item), recipeGenerator.conditionsFromItem(item))
}

fun ShapelessRecipeJsonBuilder.inputs(init: ShapelessIngredientsBuilder.() -> Unit) {
    ShapelessIngredientsBuilder(this).init()
}

class ShapelessIngredientsBuilder(private val recipeBuilder: ShapelessRecipeJsonBuilder) {
    operator fun ItemConvertible.unaryPlus() {
        recipeBuilder.input(this)
    }

    infix fun Int.of(item: ItemConvertible) {
        recipeBuilder.input(item, this)
    }

    operator fun Ingredient.unaryPlus() {
        recipeBuilder.input(this)
    }

    infix fun Int.of(ingredient: Ingredient) {
        recipeBuilder.input(ingredient, this)
    }

    operator fun TagKey<Item>.unaryPlus() {
        recipeBuilder.input(this)
    }
}

// Cooking

val Item.id
    get() = Registries.ITEM.getId(this as Item?)

context(recipeGenerator: RecipeGenerator)
fun <T: AbstractCookingRecipe> RecipeExporter.cookingRecipe(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    serializer: RecipeSerializer<T>,
    recipeFactory: RecipeFactory<T>,
    method: String,
    cookingTime: Int = 200,
    experience: Float = 0F
) {
    CookingRecipeJsonBuilder.create(
        Ingredient.ofItems(inputItem),
        category,
        outputItem,
        experience,
        cookingTime,
        serializer,
        recipeFactory,
    )
        .criterion(RecipeGenerator.hasItem(inputItem), recipeGenerator.conditionsFromItem(inputItem))
        .offerTo(this, Identifier.of(outputItem.id.namespace, "${outputItem.id.path}_from_${method}_${inputItem.id.path}"))
}

context(recipeGenerator: RecipeGenerator)
fun RecipeExporter.smelting(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 200,
    experience: Float = 0F
) {
    cookingRecipe(category, inputItem, outputItem, RecipeSerializer.SMELTING, ::SmeltingRecipe, "smelting", cookingTime, experience)
}

context(recipeGenerator: RecipeGenerator)
fun RecipeExporter.smoking(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 100,
    experience: Float = 0F
) {
    cookingRecipe(category, inputItem, outputItem, RecipeSerializer.SMOKING, ::SmokingRecipe, "smoking", cookingTime, experience)
}

context(recipeGenerator: RecipeGenerator)
fun RecipeExporter.blasting(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 50,
    experience: Float = 0F
) {
    cookingRecipe(category, inputItem, outputItem, RecipeSerializer.BLASTING, ::BlastingRecipe, "blasting", cookingTime, experience)
}

context(recipeGenerator: RecipeGenerator)
fun RecipeExporter.campfire(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 600,
    experience: Float = 0F
) {
    cookingRecipe(category, inputItem, outputItem, RecipeSerializer.CAMPFIRE_COOKING, ::CampfireCookingRecipe, "campfire", cookingTime, experience)
}

context(recipeGenerator: RecipeGenerator)
fun RecipeExporter.oreSmelting(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 200,
    experience: Float = 0F
) {
    smelting(category, inputItem, outputItem, cookingTime, experience)
    blasting(category, inputItem, outputItem, cookingTime / 2, experience)
}

context(recipeGenerator: RecipeGenerator)
fun RecipeExporter.foodCooking(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 200,
    experience: Float = 0F
) {
    smelting(category, inputItem, outputItem, cookingTime, experience)
    smoking(category, inputItem, outputItem, cookingTime / 2, experience)
    campfire(category, inputItem, outputItem, cookingTime * 3, experience)
}
