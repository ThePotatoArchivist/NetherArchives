package archives.tater.netherarchives.emi

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.NetherArchivesTags
import archives.tater.netherarchives.item.NetherArchivesItems
import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.fluid.Fluids
import net.minecraft.item.Items
import net.minecraft.util.Identifier

object NetherArchivesEMIPlugin : EmiPlugin {
    override fun register(registry: EmiRegistry) {
        registry.apply {
            addRecipe(EmiWorldInteractionRecipe.builder().apply {
                id(Identifier(NetherArchives.NAMESPACE, "world/unique/fermented_rotten_flesh"))
                leftInput(EmiStack.of(NetherArchivesItems.ROTTEN_FLESH_BLOCK))
                rightInput(EmiIngredient.of(NetherArchivesTags.EMI_ROTTEN_FLESH_FERMENTER), true)
                output(EmiStack.of(NetherArchivesItems.FERMENTED_ROTTEN_FLESH_BLOCK))
            }.build())

            addRecipe(EmiWorldInteractionRecipe.builder().apply {
                id(Identifier(NetherArchives.NAMESPACE, "world/fluid_interaction/smoldering_magnetite"))
                leftInput(EmiStack.of(NetherArchivesItems.MAGNETITE))
                rightInput(EmiStack.of(Fluids.LAVA), true)
                output(EmiStack.of(NetherArchivesItems.SMOLDERING_MAGNETITE))
            }.build())

            addRecipe(EmiWorldInteractionRecipe.builder().apply {
                id(Identifier(NetherArchives.NAMESPACE, "world/unique/netherite_ingot_from_lodestone"))
                leftInput(EmiStack.of(Items.LODESTONE))
                rightInput(EmiStack.of(Fluids.LAVA), true)
                output(EmiStack.of(Items.NETHERITE_INGOT))
            }.build())
        }
    }
}
