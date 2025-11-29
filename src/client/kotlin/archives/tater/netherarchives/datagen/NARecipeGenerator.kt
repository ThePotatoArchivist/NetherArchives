package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.datagen.builder.*
import archives.tater.netherarchives.registry.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.world.item.Items
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.core.HolderLookup
import net.minecraft.tags.ItemTags
import java.util.concurrent.CompletableFuture

class NARecipeGenerator(registries: HolderLookup.Provider, exporter: RecipeOutput) :
    RecipeProvider(registries, exporter) {

    override fun buildRecipes() {
        output.recipes()
    }

    private fun RecipeOutput.recipes() {
        oreSmelting(RecipeCategory.MISC, NetherArchivesItems.IRON_SLAG, Items.IRON_NUGGET, 25, 0.08F)

        shaped(RecipeCategory.TOOLS, Items.COMPASS, recipeId = NetherArchives.id("compass_from_magnetite")) {
            patterns(
                """
                 # 
                #X#
                 # 
            """
            )
            inputs {
                '#' to ConventionalItemTags.IRON_INGOTS
                'X' to NetherArchivesItems.MAGNETITE
            }
            itemCriterion(NetherArchivesItems.MAGNETITE)
        }

        shapeless(RecipeCategory.MISC, NetherArchivesItems.ROTTEN_FLESH_BLOCK) {
            inputs {
                9 of Items.ROTTEN_FLESH
            }
            itemCriterion(Items.ROTTEN_FLESH)
        }

        shapeless(RecipeCategory.COMBAT, NetherArchivesItems.BLAZE_DUST, 4) {
            inputs {
                +Items.BLAZE_POWDER
            }
            itemCriterion(Items.BLAZE_POWDER)
        }

        shaped(RecipeCategory.COMBAT, NetherArchivesItems.BLAZE_LANTERN) {
            patterns(
                """
                ###
                #X#
                ###
            """
            )
            inputs {
                '#' to ConventionalItemTags.NETHER_BRICKS
                'X' to Items.BLAZE_POWDER
            }
            itemCriterion(Items.BLAZE_POWDER)
        }

        shaped(RecipeCategory.DECORATIONS, NetherArchivesItems.BLAZE_TORCH, 2) {
            patterns(
                """
                X
                #
                B
            """
            )
            inputs {
                'X' to Items.BLAZE_POWDER
                '#' to ItemTags.COALS
                'B' to ConventionalItemTags.BONES
            }
            itemCriterion(Items.BLAZE_POWDER)
        }

        shaped(RecipeCategory.MISC, NetherArchivesItems.BASALT_ROD, 2) {
            patterns("""
                  #
                 # 
                #  
            """.trimIndent())
            inputs {
                '#' to Items.POLISHED_BASALT
            }
            itemCriterion(Items.BASALT)
        }

        shaped(RecipeCategory.TOOLS, NetherArchivesItems.BASALT_SKIS) {
            patterns("""
                # #
                # #
                % %
            """.trimIndent())
            inputs {
                '#' to Items.POLISHED_BASALT
                '%' to NetherArchivesItems.BASALT_ROD
            }
            itemCriterion(NetherArchivesItems.BASALT_ROD)
        }

        shaped(RecipeCategory.TOOLS, NetherArchivesItems.BASALT_OAR) {
            patterns("""
                %
                %
                #
            """)
            inputs {
                '#' to Items.POLISHED_BASALT
                '%' to NetherArchivesItems.BASALT_ROD
            }
            itemCriterion(NetherArchivesItems.BASALT_ROD)
        }

        shaped(RecipeCategory.MISC, Items.LODESTONE, recipeId = NetherArchives.id("lodestone_from_magnetite")) {
            patterns("""
                ###
                #%#
                ###
            """)
            inputs {
                '#' to Items.CHISELED_STONE_BRICKS
                '%' to NetherArchivesItems.MAGNETITE
            }
            itemCriterion(NetherArchivesItems.MAGNETITE)
        }

        shaped(RecipeCategory.REDSTONE, NetherArchivesItems.ADJUSTABLE_BASALT_GEYSER) {
            patterns("""
                 & 
                #$#
                 % 
            """)
            inputs {
                '&' to Items.IRON_TRAPDOOR
                '#' to ConventionalItemTags.IRON_INGOTS
                '$' to NetherArchivesItems.BASALT_GEYSER
                '%' to ConventionalItemTags.REDSTONE_DUSTS
            }
            itemCriterion(NetherArchivesItems.BASALT_GEYSER)
        }

        smelting(RecipeCategory.DECORATIONS, Items.SOUL_SAND, NetherArchivesItems.SPECTREGLASS_SHARD, experience = 0.1f)

        twoByTwoPacker(RecipeCategory.DECORATIONS, NetherArchivesItems.SHATTERED_SPECTREGLASS, NetherArchivesItems.SPECTREGLASS_SHARD)

        stainedGlassPaneFromStainedGlass(NetherArchivesItems.SPECTREGLASS_PANE, NetherArchivesItems.SPECTREGLASS)
        stainedGlassPaneFromStainedGlass(NetherArchivesItems.SHATTERED_SPECTREGLASS_PANE, NetherArchivesItems.SHATTERED_SPECTREGLASS)

        smelting(RecipeCategory.DECORATIONS, NetherArchivesItems.SHATTERED_SPECTREGLASS, NetherArchivesItems.SPECTREGLASS)
        smelting(RecipeCategory.DECORATIONS, NetherArchivesItems.SHATTERED_SPECTREGLASS_PANE, NetherArchivesItems.SPECTREGLASS_PANE)

        shaped(RecipeCategory.COMBAT, NetherArchivesItems.SPECTREGLASS_KNIFE) {
            patterns("""
                #
                #
                %
            """)
            inputs {
                '#' to NetherArchivesItems.SPECTREGLASS_SHARD
                '%' to ConventionalItemTags.BONES
            }
            itemCriterion(NetherArchivesItems.SPECTREGLASS_SHARD)
        }
    }

    class Provider(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) :
        FabricRecipeProvider(output, registriesFuture) {
        override fun createRecipeProvider(
            registryLookup: HolderLookup.Provider,
            exporter: RecipeOutput
        ): RecipeProvider = NARecipeGenerator(registryLookup, exporter)

        override fun getName(): String = "Recipes"

    }
}
