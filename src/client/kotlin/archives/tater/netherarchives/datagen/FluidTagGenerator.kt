package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.ModFluidTags
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
        builder(ModFluidTags.SKIS_CAN_WALK_ON).apply {
            forceAddTag(FluidTags.LAVA)
            forceAddTag(FluidTags.WATER)
        }

        builder(ModFluidTags.BURNS_WHEN_PADDLE).apply {
            forceAddTag(FluidTags.LAVA)
        }
    }
}
