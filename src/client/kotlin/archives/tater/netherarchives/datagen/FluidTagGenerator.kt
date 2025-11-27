package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesTags
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.tags.FluidTags
import java.util.concurrent.CompletableFuture

class FluidTagGenerator(
    output: FabricDataOutput,
    registriesFuture: CompletableFuture<HolderLookup.Provider>
) : FabricTagProvider.FluidTagProvider(output, registriesFuture) {
    override fun addTags(wrapperLookup: HolderLookup.Provider) {
        getOrCreateTagBuilder(NetherArchivesTags.SKIS_CAN_WALK_ON).apply {
            forceAddTag(FluidTags.LAVA)
            forceAddTag(FluidTags.WATER)
        }

        getOrCreateTagBuilder(NetherArchivesTags.BURNS_WHEN_PADDLE).apply {
            forceAddTag(FluidTags.LAVA)
        }
    }
}
