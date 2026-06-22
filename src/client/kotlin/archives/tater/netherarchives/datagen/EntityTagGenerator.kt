package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.ModTags
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider
import net.minecraft.core.HolderLookup
import net.minecraft.world.entity.EntityTypeIds
import java.util.concurrent.CompletableFuture

class EntityTagGenerator(
    output: FabricPackOutput,
    registriesFuture: CompletableFuture<HolderLookup.Provider>
) : FabricTagsProvider.EntityTypeTagsProvider(output, registriesFuture) {

    override fun addTags(wrapperLookup: HolderLookup.Provider) {
        builder(ModTags.NON_CHAIN_SHATTER_PROJECTILES).add(
            EntityTypeIds.EGG,
            EntityTypeIds.SNOWBALL,
            EntityTypeIds.SMALL_FIREBALL,
            EntityTypeIds.WIND_CHARGE,
        )
        builder(ModTags.NON_SHATTER_PROJECTILES).add(
            EntityTypeIds.FIREBALL,
            EntityTypeIds.WITHER_SKULL,
        )
        builder(ModTags.BLAZE_COLORED_FIRE).add(
            EntityTypeIds.BLAZE,
        )
    }
}
