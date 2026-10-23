package archives.tater.netherarchives.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.Registries

object NetherArchivesDataGenerator : DataGeneratorEntrypoint {
    override fun buildRegistry(registryBuilder: RegistrySetBuilder) {
        registryBuilder.add(FeatureGenerator)
        registryBuilder.add(Registries.DAMAGE_TYPE, DamageTypeGenerator)
    }

    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        fabricDataGenerator.createPack().apply {
            addProvider(::ModelGenerator)
            addProvider(::BlockTagGenerator)
            addProvider(::ItemTagGenerator)
            addProvider(::FluidTagGenerator)
            addProvider(::EntityTagGenerator)
            addProvider(::StructureTagGenerator)
            addProvider(::BlockLootTableGenerator)
            addProvider(NARecipeGenerator::Provider)
            addProvider(::AdvancementGenerator)
            addProvider(::EnglishLangGenerator)
            addProvider(::SoundsGenerator)
            addProvider(::FeatureGenerator)
            addProvider(::DamageTypeGenerator)
            addProvider(::DamageTypeTagGenerator)
            addProvider(::EquipmentGenerator)
        }
    }
}
