package archives.tater.netherarchives.emi

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.registry.NetherArchivesItems
import archives.tater.netherarchives.registry.NetherArchivesTags
import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.block.Blocks
import net.minecraft.component.DataComponentTypes
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity

object NetherArchivesEmiPlugin : EmiPlugin {
    override fun register(registry: EmiRegistry) {
        registry.apply {
            addWorldRecipe("unique/fermented_rotten_flesh") {
                leftInput(EmiStack.of(NetherArchivesItems.ROTTEN_FLESH_BLOCK))
                rightInput(EmiIngredient.of(Registries.BLOCK
                    .filter { it.defaultState.isIn(NetherArchivesTags.ROTTEN_FLESH_FERMENTER) }
                    .map { if (it == Blocks.SOUL_FIRE) NetherArchivesItems.DUMMY_SOUL_FIRE else it }
                    .map(EmiStack::of)), true)
                output(EmiStack.of(NetherArchivesItems.FERMENTED_ROTTEN_FLESH_BLOCK))
            }

            addWorldRecipe("fluid_interaction/smoldering_magnetite") {
                leftInput(EmiStack.of(NetherArchivesItems.MAGNETITE))
                rightInput(EmiStack.of(Fluids.LAVA), true)
                output(EmiStack.of(NetherArchivesItems.SMOLDERING_MAGNETITE))
            }

            addWorldRecipe("unique/nether_star") {
                leftInput(EmiStack.of(Items.BEACON))
                rightInput(EmiIngredient.of(listOf(
                    Items.TNT,
                    Items.CREEPER_HEAD,
                    Items.END_CRYSTAL,
                    Items.WITHER_SKELETON_SKULL,
                    Items.RED_BED,
                    Items.RESPAWN_ANCHOR,
                ).map {
                    EmiStack.of(ItemStack(it).apply {
                        this[DataComponentTypes.ITEM_NAME] = Text.translatable("netherarchives.emi.explosion")
                        this[DataComponentTypes.RARITY] = Rarity.COMMON
                    })
                }), true)
                output(EmiStack.of(Items.NETHER_STAR))
            }

            addWorldRecipe("unique/shattered_spectreglass") {
                leftInput(EmiStack.of(NetherArchivesItems.SPECTREGLASS))
                rightInput(EmiIngredient.of(listOf(
                    Items.ARROW,
                    Items.SNOWBALL,
                    Items.EGG,
                    Items.TRIDENT,
                    Items.FIREWORK_ROCKET,
                    Items.FIRE_CHARGE,
                    Items.WIND_CHARGE,
                ).map {
                    EmiStack.of(ItemStack(it).apply {
                        this[DataComponentTypes.ITEM_NAME] = Text.translatable("netherarchives.emi.projectile")
                        this[DataComponentTypes.RARITY] = Rarity.COMMON
                    })
                }), true)
                output(EmiStack.of(NetherArchivesItems.SHATTERED_SPECTREGLASS))
            }
        }
    }

    private fun EmiRegistry.addWorldRecipe(id: Identifier, init: EmiWorldInteractionRecipe.Builder.() -> Unit) {
        addRecipe(EmiWorldInteractionRecipe.builder().id(id).apply(init).build())
    }

    private fun EmiRegistry.addWorldRecipe(postpath: String, init: EmiWorldInteractionRecipe.Builder.() -> Unit) {
        addWorldRecipe(NetherArchives.id("/world/$postpath"), init)
    }
}
