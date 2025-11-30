package archives.tater.netherarchives.datagen.builder

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.data.recipes.*
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.Identifier
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.*
import net.minecraft.world.item.crafting.AbstractCookingRecipe.Factory
import net.minecraft.world.level.ItemLike

// Shaped

fun RecipeBuilder.offerTo(exporter: RecipeOutput, recipeId: Identifier) {
    save(exporter, ResourceKey.create(Registries.RECIPE, recipeId))
}

context(exporter: RecipeOutput)
inline fun RecipeProvider.shaped(
    category: RecipeCategory,
    outputItem: ItemLike,
    count: Int = 1,
    recipeId: Identifier = outputItem.asItem().id,
    init: ShapedRecipeBuilder.() -> Unit
) {
    shaped(category, outputItem, count).apply(init)
        .offerTo(exporter, recipeId)
}

fun ShapedRecipeBuilder.patterns(rows: String) {
    rows.trimIndent().split('\n').forEach(::pattern)
}

context(recipeGenerator: RecipeProvider)
fun ShapedRecipeBuilder.itemCriterion(item: ItemLike) {
    unlockedBy(RecipeProvider.getHasName(item), recipeGenerator.has(item))
}

inline fun ShapedRecipeBuilder.inputs(init: ShapedIngredientsBuilder.() -> Unit) {
    ShapedIngredientsBuilder(this).init()
}

class ShapedIngredientsBuilder(private val recipeBuilder: ShapedRecipeBuilder) {
    infix fun Char.to(item: ItemLike) {
        recipeBuilder.define(this, item)
    }

    infix fun Char.to(ingredient: Ingredient) {
        recipeBuilder.define(this, ingredient)
    }

    infix fun Char.to(tagKey: TagKey<Item>) {
        recipeBuilder.define(this, tagKey)
    }
}

// Shapeless Recipe

context(exporter: RecipeOutput)
inline fun RecipeProvider.shapeless(
    category: RecipeCategory,
    outputItem: ItemLike,
    count: Int = 1,
    recipeId: Identifier = outputItem.asItem().id,
    init: ShapelessRecipeBuilder.() -> Unit
) {
    shapeless(category, outputItem, count).apply(init)
        .offerTo(exporter, recipeId)
}

context(recipeGenerator: RecipeProvider)
fun ShapelessRecipeBuilder.itemCriterion(item: ItemLike) {
    unlockedBy(RecipeProvider.getHasName(item), recipeGenerator.has(item))
}

fun ShapelessRecipeBuilder.inputs(init: ShapelessIngredientsBuilder.() -> Unit) {
    ShapelessIngredientsBuilder(this).init()
}

class ShapelessIngredientsBuilder(private val recipeBuilder: ShapelessRecipeBuilder) {
    operator fun ItemLike.unaryPlus() {
        recipeBuilder.requires(this)
    }

    infix fun Int.of(item: ItemLike) {
        recipeBuilder.requires(item, this)
    }

    operator fun Ingredient.unaryPlus() {
        recipeBuilder.requires(this)
    }

    infix fun Int.of(ingredient: Ingredient) {
        recipeBuilder.requires(ingredient, this)
    }

    operator fun TagKey<Item>.unaryPlus() {
        recipeBuilder.requires(this)
    }
}

// Cooking

val Item.id
    get() = BuiltInRegistries.ITEM.getKey(this)

context(recipeGenerator: RecipeProvider)
fun <T: AbstractCookingRecipe> RecipeOutput.cookingRecipe(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    serializer: RecipeSerializer<T>,
    recipeFactory: Factory<T>,
    method: String,
    cookingTime: Int = 200,
    experience: Float = 0F
) {
    SimpleCookingRecipeBuilder.generic(
        Ingredient.of(inputItem),
        category,
        outputItem,
        experience,
        cookingTime,
        serializer,
        recipeFactory,
    )
        .unlockedBy(RecipeProvider.getHasName(inputItem), recipeGenerator.has(inputItem))
        .offerTo(this, Identifier.fromNamespaceAndPath(outputItem.id.namespace, "${outputItem.id.path}_from_${method}_${inputItem.id.path}"))
}

context(recipeGenerator: RecipeProvider)
fun RecipeOutput.smelting(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 200,
    experience: Float = 0F
) {
    cookingRecipe(category, inputItem, outputItem, RecipeSerializer.SMELTING_RECIPE, ::SmeltingRecipe, "smelting", cookingTime, experience)
}

context(recipeGenerator: RecipeProvider)
fun RecipeOutput.smoking(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 100,
    experience: Float = 0F
) {
    cookingRecipe(category, inputItem, outputItem, RecipeSerializer.SMOKING_RECIPE, ::SmokingRecipe, "smoking", cookingTime, experience)
}

context(recipeGenerator: RecipeProvider)
fun RecipeOutput.blasting(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 50,
    experience: Float = 0F
) {
    cookingRecipe(category, inputItem, outputItem, RecipeSerializer.BLASTING_RECIPE, ::BlastingRecipe, "blasting", cookingTime, experience)
}

context(recipeGenerator: RecipeProvider)
fun RecipeOutput.campfire(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 600,
    experience: Float = 0F
) {
    cookingRecipe(category, inputItem, outputItem, RecipeSerializer.CAMPFIRE_COOKING_RECIPE, ::CampfireCookingRecipe, "campfire", cookingTime, experience)
}

context(recipeGenerator: RecipeProvider)
fun RecipeOutput.oreSmelting(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 200,
    experience: Float = 0F
) {
    smelting(category, inputItem, outputItem, cookingTime, experience)
    blasting(category, inputItem, outputItem, cookingTime / 2, experience)
}

context(recipeGenerator: RecipeProvider)
fun RecipeOutput.foodCooking(
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
