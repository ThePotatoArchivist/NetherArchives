package archives.tater.netherarchives

import archives.tater.netherarchives.datagen.BlockLootTableGenerator
import archives.tater.netherarchives.datagen.BlockTagGenerator
import archives.tater.netherarchives.datagen.ModelGenerator
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object NetherArchivesDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        fabricDataGenerator.createPack().apply {
            addProvider(::ModelGenerator)
            addProvider(::BlockTagGenerator)
            addProvider(::BlockLootTableGenerator)
        }
    }
}
