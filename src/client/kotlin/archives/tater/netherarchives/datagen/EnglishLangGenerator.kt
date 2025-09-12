package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesBlocks
import archives.tater.netherarchives.registry.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class EnglishLangGenerator(output: FabricDataOutput, registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>) :
    FabricLanguageProvider(output, registriesFuture) {

    override fun generateTranslations(registryLookup: RegistryWrapper.WrapperLookup, translationBuilder: TranslationBuilder) {
        translationBuilder.apply {
            add(NetherArchivesBlocks.MAGNETITE, "Magnetite")
            add(NetherArchivesBlocks.SMOLDERING_MAGNETITE, "Smoldering Magnetite")
            add(NetherArchivesBlocks.BLAZE_DUST, "Blaze Dust")
            add(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK, "Rotten Flesh Block")
            add(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK, "Fermented Rotten Flesh Block")
            add(NetherArchivesBlocks.BLAZE_TORCH, "Blaze Torch")
            add(NetherArchivesBlocks.BASALT_GEYSER, "Basalt Geyser")
            add(NetherArchivesBlocks.ADJUSTABLE_BASALT_GEYSER, "Adjustable Basalt Geyser")
            add(NetherArchivesBlocks.SPECTREGLASS, "Spectreglass")
            add(NetherArchivesBlocks.SHATTERED_SPECTREGLASS, "Shattered Spectreglass")
            add(NetherArchivesBlocks.SPECTREGLASS_PANE, "Spectreglass Pane")
            add(NetherArchivesBlocks.SHATTERED_SPECTREGLASS_PANE, "Shattered Spectreglass Pane")
            add(NetherArchivesItems.BLAZE_LANTERN, "Volatile Blaze Lantern")
            add(NetherArchivesItems.IRON_SLAG, "Iron Slag")
            add(NetherArchivesItems.DUMMY_SOUL_FIRE, "Soul Fire")
            add(NetherArchivesItems.BASALT_SKIS, "Basalt Skis")
            add(NetherArchivesItems.BASALT_OAR, "Basalt Oar")
            add(NetherArchivesItems.BASALT_ROD, "Basalt Rod")
            add(NetherArchivesItems.SPECTREGLASS_SHARD, "Spectreglass Shard")
            add(NetherArchivesItems.SPECTREGLASS_KNIFE, "Spectreglass Knife")
            add("death.attack.netherarchives.paddleBurn", "%s wiped out into lava")
            add("death.attack.netherarchives.paddleBurn.player", "%s wiped out into lava while trying to escape %s")
            add("netherarchives.emi.explosion", "Any Explosion")
            add("netherarchives.emi.projectile", "Impact Projectiles")
        }
    }
}
