package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesTags
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.levelgen.structure.BuiltinStructures
import net.minecraft.world.level.levelgen.structure.Structure
import java.util.concurrent.CompletableFuture

class StructureTagGenerator(
    output: FabricPackOutput,
    registriesFuture: CompletableFuture<HolderLookup.Provider>
) : FabricTagsProvider<Structure>(output, Registries.STRUCTURE, registriesFuture) {

    override fun addTags(arg: HolderLookup.Provider) {
        getOrCreateRawBuilder(NetherArchivesTags.BLAZE_TORCH_LOCATED).addElement(
            BuiltinStructures.FORTRESS.identifier()
        )
    }

}
