package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesEntityTags
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
        valueLookupBuilder(NetherArchivesEntityTags.NON_CHAIN_SHATTER_PROJECTILES).add(
            EntityType.EGG,
            EntityType.SNOWBALL,
            EntityType.SMALL_FIREBALL,
            EntityType.WIND_CHARGE,
        )
        valueLookupBuilder(NetherArchivesEntityTags.NON_SHATTER_PROJECTILES).add(
            EntityType.FIREBALL,
            EntityType.WITHER_SKULL,
        )
        valueLookupBuilder(NetherArchivesEntityTags.BLAZE_COLORED_FIRE).add(
            EntityType.BLAZE,
        )
    }
}
