package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesDamageTypes
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.tags.DamageTypeTags
import net.minecraft.world.damagesource.DamageType
import java.util.concurrent.CompletableFuture

class DamageTypeTagGenerator(output: FabricPackOutput, registryLookupFuture: CompletableFuture<HolderLookup.Provider>)
    : FabricTagsProvider<DamageType>(output, Registries.DAMAGE_TYPE, registryLookupFuture) {

    override fun addTags(registries: HolderLookup.Provider) {
        builder(DamageTypeTags.BYPASSES_ARMOR).add(NetherArchivesDamageTypes.PADDLE_BURN)
        builder(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(NetherArchivesDamageTypes.PADDLE_BURN)
        builder(DamageTypeTags.IS_FIRE).add(NetherArchivesDamageTypes.PADDLE_BURN)
        builder(DamageTypeTags.NO_IMPACT).add(NetherArchivesDamageTypes.PADDLE_BURN)
        builder(DamageTypeTags.NO_KNOCKBACK).add(NetherArchivesDamageTypes.PADDLE_BURN)
    }
}