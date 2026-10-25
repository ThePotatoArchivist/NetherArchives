package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesTags
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider
import net.minecraft.core.HolderLookup
import net.minecraft.tags.FluidTags
import java.util.concurrent.CompletableFuture

class FluidTagGenerator(
    output: FabricPackOutput,
    registriesFuture: CompletableFuture<HolderLookup.Provider>
) : FabricTagsProvider.FluidTagsProvider(output, registriesFuture) {
    override fun addTags(wrapperLookup: HolderLookup.Provider) {
        valueLookupBuilder(NetherArchivesTags.SKIS_CAN_WALK_ON).apply {
            forceAddTag(FluidTags.LAVA)
            forceAddTag(FluidTags.WATER)
        }

        valueLookupBuilder(NetherArchivesTags.BURNS_WHEN_PADDLE).apply {
            forceAddTag(FluidTags.LAVA)
        }
    }
}
