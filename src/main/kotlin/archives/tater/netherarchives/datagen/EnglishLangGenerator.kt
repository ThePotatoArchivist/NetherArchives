package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.NetherArchivesTags
import archives.tater.netherarchives.NetherArchivesTags.translationKey
import archives.tater.netherarchives.block.NetherArchivesBlocks
import archives.tater.netherarchives.item.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider

class EnglishLangGenerator(output: FabricDataOutput) : FabricLanguageProvider(output) {
    override fun generateTranslations(translationBuilder: TranslationBuilder) {
        translationBuilder.apply {
            add(NetherArchivesBlocks.MAGNETITE, "Magnetite")
            add(NetherArchivesBlocks.SMOLDERING_MAGNETITE, "Smoldering Magnetite")
            add(NetherArchivesBlocks.BLAZE_DUST, "Blaze Dust")
            add(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK, "Rotten Flesh Block")
            add(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK, "Fermented Rotten Flesh Block")
            add(NetherArchivesBlocks.BLAZE_TORCH, "Blaze Torch")
            add(NetherArchivesItems.BLAZE_LANTERN, "Volatile Blaze Lantern")
            add(NetherArchivesItems.IRON_SLAG, "Iron Slag")
            add(NetherArchivesItems.DUMMY_SOUL_FIRE, "Soul Fire")
            add(NetherArchivesTags.EMI_ROTTEN_FLESH_FERMENTER.translationKey, "Rotten Flesh Fermenter")
        }
    }
}
