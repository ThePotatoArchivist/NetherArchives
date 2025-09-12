package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesTags
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import net.minecraft.world.gen.structure.Structure
import net.minecraft.world.gen.structure.StructureKeys
import java.util.concurrent.CompletableFuture

class StructureTagGenerator(
    output: FabricDataOutput,
    registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricTagProvider<Structure>(output, RegistryKeys.STRUCTURE, registriesFuture) {

    override fun configure(arg: RegistryWrapper.WrapperLookup?) {
        getOrCreateTagBuilder(NetherArchivesTags.BLAZE_TORCH_LOCATED).add(
            StructureKeys.FORTRESS
        )
    }

}
