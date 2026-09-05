package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.registry.ModSounds
import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder
import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder.RegistrationBuilder.ofEvent
import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder.RegistrationBuilder.ofFile
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricSoundsProvider
import net.minecraft.core.Holder
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.resources.Identifier
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import java.util.concurrent.CompletableFuture

class SoundsGenerator(output: PackOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) :
    FabricSoundsProvider(output, registriesFuture) {

    override fun configure(
        registryLookup: HolderLookup.Provider,
        exporter: SoundExporter
    ) {
        with (exporter) {
            add(ModSounds.BLAZE_FIRE_IGNITE) {
                soundEvent(SoundEvents.FIRECHARGE_USE)
            }

            add(ModSounds.BLAZE_LANTERN_THROW) {
                soundFile(Identifier.withDefaultNamespace("random/bow"))
            }

            add(ModSounds.BLAZE_LANTERN_SHATTER) {
                soundEvent(SoundEvents.GLASS_BREAK)
            }

            add(ModSounds.BASALT_OAR_PADDLE) {
                soundEvent(SoundEvents.BOAT_PADDLE_WATER)
            }

            add(ModSounds.BASALT_OAR_PADDLE_LAVA, SoundTypeBuilder.of(ModSounds.BASALT_OAR_PADDLE).apply {
                sound(ofEvent(SoundEvents.BUCKET_EMPTY_LAVA))
            })
        }
    }

    private fun SoundExporter.add(event: SoundEvent, init: SoundTypeBuilder.() -> Unit) = add(event, SoundTypeBuilder.of(event).apply(init))
    private fun SoundExporter.add(event: Holder<SoundEvent>, init: SoundTypeBuilder.() -> Unit) = add(event, SoundTypeBuilder.of(event.value()).apply(init))

    private fun SoundTypeBuilder.soundEvent(event: SoundEvent, init: SoundTypeBuilder.RegistrationBuilder.() -> Unit = {}) = sound(ofEvent(event).apply(init))
    private fun SoundTypeBuilder.soundEvent(event: Holder<SoundEvent>, init: SoundTypeBuilder.RegistrationBuilder.() -> Unit = {}) = sound(ofEvent(event).apply(init))
    private fun SoundTypeBuilder.soundFile(file: Identifier, init: SoundTypeBuilder.RegistrationBuilder.() -> Unit = {}) = sound(ofFile(file).apply(init))

    override fun getName(): String = "Sounds"
}