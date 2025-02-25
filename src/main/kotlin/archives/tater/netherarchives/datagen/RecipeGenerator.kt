package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.datagen.builder.*
import archives.tater.netherarchives.item.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.item.Items
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.registry.tag.ItemTags

class RecipeGenerator(output: FabricDataOutput) : FabricRecipeProvider(output) {
    override fun generate(exporter: RecipeExporter) {
        exporter.recipes()
    }

    private fun RecipeExporter.recipes() {
        oreSmelting(RecipeCategory.MISC, NetherArchivesItems.IRON_SLAG, Items.IRON_NUGGET, 25, 0.08F)

        shaped(RecipeCategory.TOOLS, Items.COMPASS, recipeName = "compass_from_magnetite") {
            patterns(
                """
                 # 
                #X#
                 # 
            """
            )
            inputs {
                '#' to Items.IRON_INGOT
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
                '#' to Items.NETHER_BRICK
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
                'B' to Items.BONE
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

        shaped(RecipeCategory.MISC, Items.LODESTONE, recipeName = "lodestone_from_magnetite") {
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
    }
}
