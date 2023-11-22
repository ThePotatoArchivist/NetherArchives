package archives.tater.netherarchives.datagen.builder

import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.item.ItemConvertible
import net.minecraft.recipe.book.RecipeCategory

inline fun RecipeExporter.shapedRecipe(category: RecipeCategory, outputItem: ItemConvertible, count: Int = 1, init: ShapedRecipeJsonBuilder.() -> Unit) {
    ShapedRecipeJsonBuilder.create(category, outputItem).apply(init).offerTo(this)
}

fun ShapedRecipeJsonBuilder.pattern(vararg rows: String) {
    rows.forEach { pattern(it) }
}

fun ShapedRecipeJsonBuilder.itemCriterion(item: ItemConvertible) {
    criterion(FabricRecipeProvider.hasItem(item), FabricRecipeProvider.conditionsFromItem(item))
}

fun ShapedRecipeJsonBuilder.crInput(c: Char, item: ItemConvertible) {
    input(c, item)
    itemCriterion(item)
}
