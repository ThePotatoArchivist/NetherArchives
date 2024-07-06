package archives.tater.netherarchives.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object NetherArchivesDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        fabricDataGenerator.createPack().apply {
            addProvider(::ModelGenerator)
            addProvider(::BlockTagGenerator)
            addProvider(::ItemTagGenerator)
            addProvider(::StructureTagGenerator)
            addProvider(::BlockLootTableGenerator)
            addProvider(::RecipeGenerator)
            addProvider(::EnglishLangGenerator)
        }
    }
}
