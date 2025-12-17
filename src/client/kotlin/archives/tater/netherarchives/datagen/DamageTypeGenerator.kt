package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.NetherArchives.MOD_ID
import archives.tater.netherarchives.registry.NetherArchivesDamageTypes
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.world.damagesource.DamageEffects
import net.minecraft.world.damagesource.DamageType
import java.util.concurrent.CompletableFuture

class DamageTypeGenerator(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) :
    FabricDynamicRegistryProvider(output, registriesFuture) {
    override fun configure(
        registries: HolderLookup.Provider,
        entries: Entries
    ) {
        entries.add(registries.lookupOrThrow(Registries.DAMAGE_TYPE), NetherArchivesDamageTypes.PADDLE_BURN)
    }

    override fun getName(): String = "Damage Types"

    companion object : RegistrySetBuilder.RegistryBootstrap<DamageType> {
        override fun run(bootstrapContext: BootstrapContext<DamageType>) {
            bootstrapContext.register(NetherArchivesDamageTypes.PADDLE_BURN, DamageType(
                "$MOD_ID.paddleBurn",
                0f,
                DamageEffects.BURNING,
            ))
        }
    }
}