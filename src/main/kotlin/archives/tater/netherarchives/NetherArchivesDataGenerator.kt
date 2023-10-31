package archives.tater.netherarchives

import archives.tater.netherarchives.datagen.BlockLootTableGenerator
import archives.tater.netherarchives.datagen.BlockTagGenerator
import archives.tater.netherarchives.datagen.ModelGenerator
import archives.tater.netherarchives.datagen.RecipeGenerator
import archives.tater.netherarchives.datagen.EnglishLangGenerator
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object NetherArchivesDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        fabricDataGenerator.createPack().apply {
            addProvider(::ModelGenerator)
            addProvider(::BlockTagGenerator)
            addProvider(::BlockLootTableGenerator)
            addProvider(::RecipeGenerator)
            addProvider(::EnglishLangGenerator)
        }
    }
}
