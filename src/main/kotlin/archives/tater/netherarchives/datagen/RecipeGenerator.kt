package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.datagen.builder.crInput
import archives.tater.netherarchives.datagen.builder.pattern
import archives.tater.netherarchives.datagen.builder.shapedRecipe
import archives.tater.netherarchives.item.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.data.server.recipe.RecipeProvider
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.item.Items
import net.minecraft.recipe.book.RecipeCategory

class RecipeGenerator(output: FabricDataOutput) : FabricRecipeProvider(output) {
    // TODO Recipe Builder
    override fun generate(exporter: RecipeExporter) {
        // TODO: These advancements try to trigger the minecraft: recipe for some reason?
        RecipeProvider.offerSmelting(exporter, listOf(NetherArchivesItems.IRON_SLAG), RecipeCategory.MISC, Items.IRON_NUGGET, 0F, 25, null)
        RecipeProvider.offerBlasting(exporter, listOf(NetherArchivesItems.IRON_SLAG), RecipeCategory.MISC, Items.IRON_NUGGET, 0F, 12, null)

//        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.COMPASS)

        exporter.shapedRecipe(RecipeCategory.TOOLS, Items.COMPASS) {
            pattern(
                " # ",
                "#X#",
                " # "
            )
            input('#', Items.IRON_INGOT)
            crInput('X', NetherArchivesItems.MAGNETITE)
        }

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, NetherArchivesItems.ROTTEN_FLESH_BLOCK)
            .input(Items.ROTTEN_FLESH, 9)
            .criterion(hasItem(Items.ROTTEN_FLESH), conditionsFromItem(Items.ROTTEN_FLESH))
            .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, NetherArchivesItems.BLAZE_DUST, 4)
            .input(Items.BLAZE_POWDER)
            .criterion(hasItem(Items.BLAZE_POWDER), conditionsFromItem(Items.BLAZE_POWDER))
            .offerTo(exporter)

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, NetherArchivesItems.BLAZE_LANTERN)
            .pattern("###")
            .pattern("#X#")
            .pattern("###")
            .input('#', Items.NETHER_BRICK)
            .input('X', Items.BLAZE_POWDER)
            .criterion(hasItem(Items.BLAZE_POWDER), conditionsFromItem(Items.BLAZE_POWDER))
            .offerTo(exporter)
    }
}
