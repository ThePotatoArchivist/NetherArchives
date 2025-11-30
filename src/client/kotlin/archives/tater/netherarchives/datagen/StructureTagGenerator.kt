package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesTags
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.levelgen.structure.BuiltinStructures
import net.minecraft.world.level.levelgen.structure.Structure
import java.util.concurrent.CompletableFuture

class StructureTagGenerator(
    output: FabricDataOutput,
    registriesFuture: CompletableFuture<HolderLookup.Provider>
) : FabricTagProvider<Structure>(output, Registries.STRUCTURE, registriesFuture) {

    override fun addTags(arg: HolderLookup.Provider) {
        getOrCreateRawBuilder(NetherArchivesTags.BLAZE_TORCH_LOCATED).addElement(
            BuiltinStructures.FORTRESS.identifier()
        )
    }

}
