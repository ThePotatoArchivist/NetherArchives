package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.registry.ModEquipmentAssets
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider
import net.minecraft.client.resources.model.EquipmentClientInfo
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.equipment.EquipmentAsset
import java.util.concurrent.CompletableFuture
import java.util.function.BiConsumer

class EquipmentGenerator(
    packOutput: FabricPackOutput,
    registriesFuture: CompletableFuture<HolderLookup.Provider>,
) : FabricCodecDataProvider<EquipmentClientInfo>(packOutput, registriesFuture, PackOutput.Target.RESOURCE_PACK, "equipment", EquipmentClientInfo.CODEC) {
    override fun configure(
        provider: BiConsumer<Identifier, EquipmentClientInfo>,
        registryLookup: HolderLookup.Provider
    ) {

        fun registerSkis(key: ResourceKey<EquipmentAsset>, name: String) {
            provider.accept(key.identifier(), EquipmentClientInfo(mapOf(
                EquipmentClientInfo.LayerType.HUMANOID to [EquipmentClientInfo.Layer(NetherArchives.id("basalt_skis/$name"))],
                EquipmentClientInfo.LayerType.NETHERARCHIVES_SKIS to [EquipmentClientInfo.Layer(NetherArchives.id(name))]
            )))
        }

        registerSkis(ModEquipmentAssets.BASALT_SKIS, "base")

        ModEquipmentAssets.DYED_BASALT_SKIS.forEach { (color, item) ->
            registerSkis(item, color.serializedName)
        }
    }

    override fun getName(): String = "Equipment Assets"
}