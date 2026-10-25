package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.registry.NetherArchivesDamageTypes
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.world.damagesource.DamageEffects
import net.minecraft.world.damagesource.DamageScaling
import net.minecraft.world.damagesource.DamageType
import java.util.concurrent.CompletableFuture

class DamageTypeGenerator(output: FabricPackOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) :
    FabricDynamicRegistryProvider(output, registriesFuture) {

    override fun configure(
        registries: HolderLookup.Provider,
        entries: Entries
    ) {
        entries.addAll(registries.lookupOrThrow(Registries.DAMAGE_TYPE))
    }

    override fun getName(): String = "Damage Types"

    companion object : RegistrySetBuilder.RegistryBootstrap<DamageType> {
        override fun run(registry: BootstrapContext<DamageType>) {
            registry.register(NetherArchivesDamageTypes.PADDLE_BURN, DamageType(
                "${NetherArchives.MOD_ID}.paddleBurn",
                DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,
                0f,
                DamageEffects.BURNING
            ))
        }
    }
}