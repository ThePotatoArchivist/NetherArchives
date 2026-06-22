package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.datagen.builder.*
import archives.tater.netherarchives.registry.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.CookingBookCategory
import java.util.concurrent.CompletableFuture

class NARecipeGenerator(registries: HolderLookup.Provider, exporter: RecipeOutput) :
    RecipeProvider(registries, exporter) {

    override fun buildRecipes() {
        output.recipes()
    }

    private fun RecipeOutput.recipes() {
        oreSmelting(RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.IRON_SLAG, Items.IRON_INGOT, experience = 0.5F)

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
                'X' to ModItems.MAGNETITE
            }
            itemCriterion(ModItems.MAGNETITE)
        }

        shapeless(RecipeCategory.MISC, ModItems.ROTTEN_FLESH_BLOCK) {
            inputs {
                9 of Items.ROTTEN_FLESH
            }
            itemCriterion(Items.ROTTEN_FLESH)
        }

        shapeless(RecipeCategory.COMBAT, ModItems.BLAZE_DUST, 4) {
            inputs {
                +Items.BLAZE_POWDER
            }
            itemCriterion(Items.BLAZE_POWDER)
        }

        shaped(RecipeCategory.COMBAT, ModItems.BLAZE_LANTERN) {
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

        shaped(RecipeCategory.DECORATIONS, ModItems.BLAZE_TORCH, 2) {
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

        shaped(RecipeCategory.MISC, ModItems.BASALT_ROD, 2) {
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

        shaped(RecipeCategory.TOOLS, ModItems.BASALT_SKIS) {
            patterns("""
                # #
                # #
                % %
            """.trimIndent())
            inputs {
                '#' to Items.POLISHED_BASALT
                '%' to ModItems.BASALT_ROD
            }
            itemCriterion(ModItems.BASALT_ROD)
        }

        shaped(RecipeCategory.TOOLS, ModItems.BASALT_OAR) {
            patterns("""
                %
                %
                #
            """)
            inputs {
                '#' to Items.POLISHED_BASALT
                '%' to ModItems.BASALT_ROD
            }
            itemCriterion(ModItems.BASALT_ROD)
        }

        shaped(RecipeCategory.MISC, Items.LODESTONE, recipeId = NetherArchives.id("lodestone_from_magnetite")) {
            patterns("""
                ###
                #%#
                ###
            """)
            inputs {
                '#' to Items.CHISELED_STONE_BRICKS
                '%' to ModItems.MAGNETITE
            }
            itemCriterion(ModItems.MAGNETITE)
        }

        shaped(RecipeCategory.REDSTONE, ModItems.ADJUSTABLE_BASALT_GEYSER) {
            patterns("""
                 & 
                #$#
                 % 
            """)
            inputs {
                '&' to Items.IRON_TRAPDOOR
                '#' to ConventionalItemTags.IRON_INGOTS
                '$' to ModItems.BASALT_GEYSER
                '%' to ConventionalItemTags.REDSTONE_DUSTS
            }
            itemCriterion(ModItems.BASALT_GEYSER)
        }

        smelting(
            RecipeCategory.DECORATIONS,
            CookingBookCategory.MISC,
            Items.SOUL_SAND,
            ModItems.SPECTREGLASS_SHARD,
            experience = 0.1f,
        )

        twoByTwoPacker(RecipeCategory.DECORATIONS, ModItems.SHATTERED_SPECTREGLASS, ModItems.SPECTREGLASS_SHARD)

        stainedGlassPaneFromStainedGlass(ModItems.SPECTREGLASS_PANE, ModItems.SPECTREGLASS)
        stainedGlassPaneFromStainedGlass(ModItems.SHATTERED_SPECTREGLASS_PANE, ModItems.SHATTERED_SPECTREGLASS)

        smelting(
            RecipeCategory.DECORATIONS,
            CookingBookCategory.BLOCKS,
            ModItems.SHATTERED_SPECTREGLASS,
            ModItems.SPECTREGLASS,
        )
        smelting(
            RecipeCategory.DECORATIONS,
            CookingBookCategory.BLOCKS,
            ModItems.SHATTERED_SPECTREGLASS_PANE,
            ModItems.SPECTREGLASS_PANE,
        )

        shaped(RecipeCategory.COMBAT, ModItems.SPECTREGLASS_KNIFE) {
            patterns("""
                #
                #
                %
            """)
            inputs {
                '#' to ModItems.SPECTREGLASS_SHARD
                '%' to ConventionalItemTags.BONES
            }
            itemCriterion(ModItems.SPECTREGLASS_SHARD)
        }
    }

    class Provider(output: FabricPackOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) :
        FabricRecipeProvider(output, registriesFuture) {
        override fun createRecipeProvider(
            registryLookup: HolderLookup.Provider,
            exporter: RecipeOutput
        ): RecipeProvider = NARecipeGenerator(registryLookup, exporter)

        override fun getName(): String = "Recipes"

    }
}
