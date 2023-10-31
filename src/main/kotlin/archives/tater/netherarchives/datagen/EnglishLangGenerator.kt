package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.block.NetherArchivesBlocks
import archives.tater.netherarchives.item.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider

class EnglishLangGenerator(output: FabricDataOutput) : FabricLanguageProvider(output) {
    override fun generateTranslations(translationBuilder: TranslationBuilder) {
        translationBuilder.apply {
            add(NetherArchivesBlocks.MAGNETITE, "Magnetite")
            add(NetherArchivesBlocks.SMOLDERING_MAGNETITE, "Smoldering Magnetite")
            add(NetherArchivesItems.IRON_SLAG, "Iron Slag")
        }
    }
}
