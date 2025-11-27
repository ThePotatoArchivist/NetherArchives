package archives.tater.netherarchives.datagen.builder

import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.recipes.*
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.*
import net.minecraft.world.item.crafting.AbstractCookingRecipe.Factory
import net.minecraft.world.level.ItemLike

// Shaped

inline fun RecipeOutput.shaped(
    category: RecipeCategory,
    outputItem: ItemLike,
    count: Int = 1,
    recipeId: ResourceLocation = outputItem.asItem().id,
    init: ShapedRecipeBuilder.() -> Unit
) {
    ShapedRecipeBuilder.shaped(category, outputItem, count).apply(init)
        .save(this, recipeId)
}

fun ShapedRecipeBuilder.patterns(rows: String) {
    rows.trimIndent().split('\n').forEach(::pattern)
}

fun ShapedRecipeBuilder.itemCriterion(item: ItemLike) {
    unlockedBy(FabricRecipeProvider.getHasName(item), FabricRecipeProvider.has(item))
}

fun ShapedRecipeBuilder.crInput(c: Char, item: ItemLike) {
    define(c, item)
    itemCriterion(item)
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

inline fun RecipeOutput.shapeless(
    category: RecipeCategory,
    outputItem: ItemLike,
    count: Int = 1,
    recipeId: ResourceLocation = outputItem.asItem().id,
    init: ShapelessRecipeBuilder.() -> Unit
) {
    ShapelessRecipeBuilder.shapeless(category, outputItem, count).apply(init)
        .save(this, recipeId)
}

fun ShapelessRecipeBuilder.itemCriterion(item: ItemLike) {
    unlockedBy(FabricRecipeProvider.getHasName(item), FabricRecipeProvider.has(item))
}

fun ShapelessRecipeBuilder.crInput(item: ItemLike) {
    requires(item)
    itemCriterion(item)
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
    get() = BuiltInRegistries.ITEM.getKey(this as Item?)

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
        .unlockedBy(FabricRecipeProvider.getHasName(inputItem), FabricRecipeProvider.has(inputItem))
        .save(this, ResourceLocation.fromNamespaceAndPath(outputItem.id.namespace, "${outputItem.id.path}_from_${method}_${inputItem.id.path}"))
}

fun RecipeOutput.smelting(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 200,
    experience: Float = 0F
) {
    cookingRecipe(category, inputItem, outputItem, RecipeSerializer.SMELTING_RECIPE, ::SmeltingRecipe, "smelting", cookingTime, experience)
}

fun RecipeOutput.smoking(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 100,
    experience: Float = 0F
) {
    cookingRecipe(category, inputItem, outputItem, RecipeSerializer.SMOKING_RECIPE, ::SmokingRecipe, "smoking", cookingTime, experience)
}

fun RecipeOutput.blasting(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 50,
    experience: Float = 0F
) {
    cookingRecipe(category, inputItem, outputItem, RecipeSerializer.BLASTING_RECIPE, ::BlastingRecipe, "blasting", cookingTime, experience)
}

fun RecipeOutput.campfire(
    category: RecipeCategory,
    inputItem: Item,
    outputItem: Item,
    cookingTime: Int = 600,
    experience: Float = 0F
) {
    cookingRecipe(category, inputItem, outputItem, RecipeSerializer.CAMPFIRE_COOKING_RECIPE, ::CampfireCookingRecipe, "campfire", cookingTime, experience)
}

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
