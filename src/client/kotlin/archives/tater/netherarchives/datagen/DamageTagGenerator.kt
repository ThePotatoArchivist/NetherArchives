package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.NetherArchivesDamageTypes
import archives.tater.netherarchives.registry.NetherArchivesTags
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.tags.DamageTypeTags
import net.minecraft.world.damagesource.DamageType
import net.minecraft.world.damagesource.DamageTypes
import java.util.concurrent.CompletableFuture

class DamageTagGenerator(
    output: FabricDataOutput,
    registriesFuture: CompletableFuture<HolderLookup.Provider>
) : FabricTagProvider<DamageType>(output, Registries.DAMAGE_TYPE, registriesFuture) {
    override fun addTags(registries: HolderLookup.Provider) {
        with (builder(NetherArchivesTags.NO_EFFECTS)) {
            add(DamageTypes.WITHER)
            add(DamageTypes.WITHER_SKULL)
        }
        with (builder(DamageTypeTags.NO_KNOCKBACK)) {
            forceAddTag(NetherArchivesTags.NO_EFFECTS)
            add(NetherArchivesDamageTypes.PADDLE_BURN)
        }
        with (builder(DamageTypeTags.NO_IMPACT)) {
            forceAddTag(NetherArchivesTags.NO_EFFECTS)
            add(NetherArchivesDamageTypes.PADDLE_BURN)
        }
        with (builder(DamageTypeTags.IS_FIRE)) {
            add(NetherArchivesDamageTypes.PADDLE_BURN)
        }
        with (builder(DamageTypeTags.BYPASSES_ARMOR)) {
            add(NetherArchivesDamageTypes.PADDLE_BURN)
        }
        with (builder(DamageTypeTags.BYPASSES_ENCHANTMENTS)) {
            add(NetherArchivesDamageTypes.PADDLE_BURN)
        }
    }
}