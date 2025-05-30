package archives.tater.netherarchives.datagen.builder

import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.recipe.*
import net.minecraft.recipe.AbstractCookingRecipe.RecipeFactory
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.registry.Registries
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier

// Shaped

inline fun RecipeExporter.shaped(
    category: RecipeCategory,
    outputItem: ItemConvertible,
    count: Int = 1,
    recipeId: Identifier = outputItem.asItem().id,
    init: ShapedRecipeJsonBuilder.() -> Unit
) {
    ShapedRecipeJsonBuilder.create(category, outputItem, count).apply(init)
        .offerTo(this, recipeId)
}

fun ShapedRecipeJsonBuilder.patterns(rows: String) {
    rows.trimIndent().split('\n').forEach(::pattern)
}

fun ShapedRecipeJsonBuilder.itemCriterion(item: ItemConvertible) {
    criterion(FabricRecipeProvider.hasItem(item), FabricRecipeProvider.conditionsFromItem(item))
}

fun ShapedRecipeJsonBuilder.crInput(c: Char, item: ItemConvertible) {
    input(c, item)
    itemCriterion(item)
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

inline fun RecipeExporter.shapeless(
    category: RecipeCategory,
    outputItem: ItemConvertible,
    count: Int = 1,
    recipeId: Identifier = outputItem.asItem().id,
    init: ShapelessRecipeJsonBuilder.() -> Unit
) {
    ShapelessRecipeJsonBuilder.create(category, outputItem, count).apply(init)
        .offerTo(this, recipeId)
}

fun ShapelessRecipeJsonBuilder.itemCriterion(item: ItemConvertible) {
    criterion(FabricRecipeProvider.hasItem(item), FabricRecipeProvider.conditionsFromItem(item))
}

fun ShapelessRecipeJsonBuilder.crInput(item: ItemConvertible) {
    input(item)
    itemCriterion(item)
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
        .criterion(FabricRecipeProvider.hasItem(inputItem), FabricRecipeProvider.conditionsFromItem(inputItem))
        .offerTo(this, Identifier.of(outputItem.id.namespace, "${outputItem.id.path}_from_${method}_${inputItem.id.path}"))
}

fun RecipeExporter.smelting(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 200,
    experience: Float = 0F
) {
    cookingRecipe(category, inputItem, outputItem, RecipeSerializer.SMELTING, ::SmeltingRecipe, "smelting", cookingTime, experience)
}

fun RecipeExporter.smoking(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 100,
    experience: Float = 0F
) {
    cookingRecipe(category, inputItem, outputItem, RecipeSerializer.SMOKING, ::SmokingRecipe, "smoking", cookingTime, experience)
}

fun RecipeExporter.blasting(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 50,
    experience: Float = 0F
) {
    cookingRecipe(category, inputItem, outputItem, RecipeSerializer.BLASTING, ::BlastingRecipe, "blasting", cookingTime, experience)
}

fun RecipeExporter.campfire(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 600,
    experience: Float = 0F
) {
    cookingRecipe(category, inputItem, outputItem, RecipeSerializer.CAMPFIRE_COOKING, ::CampfireCookingRecipe, "campfire", cookingTime, experience)
}

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
