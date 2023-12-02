package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.NetherArchives
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.world.gen.structure.Structure
import net.minecraft.world.gen.structure.StructureKeys
import java.util.concurrent.CompletableFuture

class StructureTagGenerator(
    output: FabricDataOutput,
    registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricTagProvider<Structure>(output, RegistryKeys.STRUCTURE, registriesFuture) {

    companion object {
        val BLAZE_TORCH_LOCATED: TagKey<Structure> =
            TagKey.of(RegistryKeys.STRUCTURE, Identifier(NetherArchives.NAMESPACE, "blaze_torch_located"))
    }

    override fun configure(arg: RegistryWrapper.WrapperLookup?) {
        getOrCreateTagBuilder(BLAZE_TORCH_LOCATED).add(
            StructureKeys.FORTRESS
        )
    }

}
