package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.NetherArchivesTags
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.FluidTags
import java.util.concurrent.CompletableFuture

class FluidTagGenerator(
    output: FabricDataOutput,
    registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricTagProvider.FluidTagProvider(output, registriesFuture) {
    override fun configure(wrapperLookup: RegistryWrapper.WrapperLookup) {
        getOrCreateTagBuilder(NetherArchivesTags.SKIS_CAN_WALK_ON).apply {
            forceAddTag(FluidTags.LAVA)
            forceAddTag(FluidTags.WATER)
        }

        getOrCreateTagBuilder(NetherArchivesTags.BURNS_WHEN_PADDLE).apply {
            forceAddTag(FluidTags.LAVA)
        }
    }
}
