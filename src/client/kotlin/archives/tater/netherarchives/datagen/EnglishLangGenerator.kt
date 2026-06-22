package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.ModBlocks
import archives.tater.netherarchives.registry.ModItems
import archives.tater.netherarchives.registry.ModTags
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

class EnglishLangGenerator(output: FabricPackOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) :
    FabricLanguageProvider(output, registriesFuture) {

    override fun generateTranslations(registryLookup: HolderLookup.Provider, translationBuilder: TranslationBuilder) {
        with(translationBuilder) {
            add(ModBlocks.MAGNETITE, "Magnetite")
            add(ModBlocks.SMOLDERING_MAGNETITE, "Smoldering Magnetite")
            add(ModBlocks.BLAZE_DUST, "Blaze Dust")
            add(ModBlocks.BLAZE_FIRE, "Blaze Fire")
            add(ModBlocks.ROTTEN_FLESH_BLOCK, "Rotten Flesh Block")
            add(ModBlocks.FERMENTED_ROTTEN_FLESH_BLOCK, "Fermented Rotten Flesh Block")
            add(ModBlocks.BLAZE_TORCH, "Blaze Torch")
            add(ModBlocks.BASALT_GEYSER, "Basalt Geyser")
            add(ModBlocks.ADJUSTABLE_BASALT_GEYSER, "Adjustable Basalt Geyser")
            add(ModBlocks.SPECTREGLASS, "Spectreglass")
            add(ModBlocks.SHATTERED_SPECTREGLASS, "Shattered Spectreglass")
            add(ModBlocks.SPECTREGLASS_PANE, "Spectreglass Pane")
            add(ModBlocks.SHATTERED_SPECTREGLASS_PANE, "Shattered Spectreglass Pane")
            add(ModItems.BLAZE_LANTERN, "Volatile Blaze Lantern")
            add(ModItems.IRON_SLAG, "Iron Slag")
            add(ModItems.DUMMY_SOUL_FIRE, "Soul Fire")
            add(ModItems.BASALT_SKIS, "Basalt Skis")
            add(ModItems.BASALT_OAR, "Basalt Oar")
            add(ModItems.BASALT_ROD, "Basalt Rod")
            add(ModItems.SPECTREGLASS_SHARD, "Spectreglass Shard")
            add(ModItems.SPECTREGLASS_KNIFE, "Spectreglass Knife")
            add(ModTags.BASALT_EQUIPMENT_REPAIR, "Repairs Basalt Equipment")
            add(ModTags.SKIS, "Skis")
            add(ModTags.ROTTEN_FLESH_FERMENTER_ITEM, "Rotten Flesh Fermenter")
            add("death.attack.netherarchives.paddleBurn", "%s wiped out into lava")
            add("death.attack.netherarchives.paddleBurn.player", "%s wiped out into lava while trying to escape %s")
            add("netherarchives.emi.explosion", "Any Explosion")
            add("netherarchives.emi.projectile", "Impact Projectiles")
            add(ModItems.CREATIVE_TAB_TRANSLATION, "Nether Archives")
            addAdvancement(AdvancementGenerator.IRON_SLAG, "Budget Forge", "Melt magnetite into iron by placing it near lava")
            addAdvancement(AdvancementGenerator.LIGHT_BLAZE_DUST, "Firewall\u2122", "Light blaze dust to create blaze fire")
            addAdvancement(AdvancementGenerator.FOLLOW_BLAZE_TORCH, "Traitorous Torch", "Follow a blaze torch to a nether fortress")
            addAdvancement(AdvancementGenerator.PADDLE_SKIS, "Hazardous Propulsion", "Propel yourself while skiing on lava")
            addAdvancement(AdvancementGenerator.BOOST_SKIS, "Airskiing", "Get a boost from a basalt geyser")
            addAdvancement(AdvancementGenerator.FERMENT, "All-Organic Vegan Leather", "Ferment rotten flesh into leather over soul fire")
        }
    }
}
