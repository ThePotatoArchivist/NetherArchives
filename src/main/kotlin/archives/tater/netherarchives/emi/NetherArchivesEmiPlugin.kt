package archives.tater.netherarchives.emi

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.NetherArchivesTags
import archives.tater.netherarchives.item.NetherArchivesItems
import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.block.Blocks
import net.minecraft.fluid.Fluids
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier

object NetherArchivesEmiPlugin : EmiPlugin {
    @Suppress("DEPRECATION")
    override fun register(registry: EmiRegistry) {
        registry.apply {
            addRecipe(EmiWorldInteractionRecipe.builder().apply {
                id(Identifier(NetherArchives.MOD_ID, "/world/unique/fermented_rotten_flesh"))
                leftInput(EmiStack.of(NetherArchivesItems.ROTTEN_FLESH_BLOCK))
                rightInput(EmiIngredient.of(Registries.BLOCK
                    .filter { it.registryEntry.isIn(NetherArchivesTags.ROTTEN_FLESH_FERMENTER) }
                    .map { if (it == Blocks.SOUL_FIRE) NetherArchivesItems.DUMMY_SOUL_FIRE else it }
                    .map(EmiStack::of)), true)
                output(EmiStack.of(NetherArchivesItems.FERMENTED_ROTTEN_FLESH_BLOCK))
            }.build())

            addRecipe(EmiWorldInteractionRecipe.builder().apply {
                id(Identifier(NetherArchives.MOD_ID, "/world/fluid_interaction/smoldering_magnetite"))
                leftInput(EmiStack.of(NetherArchivesItems.MAGNETITE))
                rightInput(EmiStack.of(Fluids.LAVA), true)
                output(EmiStack.of(NetherArchivesItems.SMOLDERING_MAGNETITE))
            }.build())

            addRecipe(EmiWorldInteractionRecipe.builder().apply {
                id(Identifier(NetherArchives.MOD_ID, "/world/unique/netherite_ingot_from_lodestone"))
                leftInput(EmiStack.of(Items.LODESTONE))
                rightInput(EmiStack.of(Fluids.LAVA), true)
                output(EmiStack.of(Items.NETHERITE_INGOT))
            }.build())
        }
    }
}
