package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesTags
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider
import net.minecraft.core.HolderLookup
import net.minecraft.world.entity.EntityType
import java.util.concurrent.CompletableFuture

class EntityTagGenerator(
    output: FabricPackOutput,
    registriesFuture: CompletableFuture<HolderLookup.Provider>
) : FabricTagsProvider.EntityTypeTagsProvider(output, registriesFuture) {

    override fun addTags(wrapperLookup: HolderLookup.Provider) {
        valueLookupBuilder(NetherArchivesTags.NON_CHAIN_SHATTER_PROJECTILES).add(
            EntityType.EGG,
            EntityType.SNOWBALL,
            EntityType.SMALL_FIREBALL,
            EntityType.WIND_CHARGE,
        )
        valueLookupBuilder(NetherArchivesTags.NON_SHATTER_PROJECTILES).add(
            EntityType.FIREBALL,
            EntityType.WITHER_SKULL,
        )
        valueLookupBuilder(NetherArchivesTags.BLAZE_COLORED_FIRE).add(
            EntityType.BLAZE,
        )
    }
}
