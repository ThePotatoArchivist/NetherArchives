package archives.tater.netherarchives.registry

import archives.tater.netherarchives.network.NoDamageEffectsPayload
import archives.tater.netherarchives.network.register
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry

internal fun initNetworking() {
    with (PayloadTypeRegistry.playS2C()) {
        register(NoDamageEffectsPayload)
    }
}
