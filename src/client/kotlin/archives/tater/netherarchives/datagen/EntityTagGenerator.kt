package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesTags
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.entity.EntityType
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class EntityTagGenerator(
    output: FabricDataOutput,
    registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricTagProvider.EntityTypeTagProvider(output, registriesFuture) {

    override fun configure(wrapperLookup: RegistryWrapper.WrapperLookup) {
        valueLookupBuilder(NetherArchivesTags.NON_CHAIN_SHATTER_PROJECTILES).add(
            EntityType.EGG,
            EntityType.SNOWBALL,
            EntityType.SMALL_FIREBALL,
            EntityType.WIND_CHARGE,
        )
        valueLookupBuilder(NetherArchivesTags.BLAZE_COLORED_FIRE).add(
            EntityType.BLAZE,
        )
    }
}