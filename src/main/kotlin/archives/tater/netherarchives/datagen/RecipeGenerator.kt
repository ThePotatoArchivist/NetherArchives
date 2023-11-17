package archives.tater.netherarchives.datagen

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
    override fun generate(exporter: RecipeExporter?) {
        RecipeProvider.offerSmelting(exporter, listOf(NetherArchivesItems.IRON_SLAG), RecipeCategory.MISC, Items.IRON_NUGGET, 0F, 25, null)
        RecipeProvider.offerBlasting(exporter, listOf(NetherArchivesItems.IRON_SLAG), RecipeCategory.MISC, Items.IRON_NUGGET, 0F, 12, null)

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.COMPASS)
            .pattern(" # ")
            .pattern("#X#")
            .pattern(" # ")
            .input('#', Items.IRON_INGOT)
            .input('X', NetherArchivesItems.MAGNETITE)
            .criterion(hasItem(NetherArchivesItems.MAGNETITE), conditionsFromItem(NetherArchivesItems.MAGNETITE))
            .offerTo(exporter)

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, NetherArchivesItems.ROTTEN_FLESH).input(Items.ROTTEN_FLESH, 9)
            .criterion(hasItem(Items.ROTTEN_FLESH), conditionsFromItem(Items.ROTTEN_FLESH))
            .criterion(hasItem(NetherArchivesItems.ROTTEN_FLESH), conditionsFromItem(NetherArchivesItems.ROTTEN_FLESH))
            .offerTo(exporter);

        RecipeProvider.offerShapelessRecipe(exporter, NetherArchivesItems.BLAZE_DUST, Items.BLAZE_POWDER, null, 4)
    }
}
