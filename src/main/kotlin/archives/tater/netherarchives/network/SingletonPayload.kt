package archives.tater.netherarchives.network

import archives.tater.netherarchives.NetherArchives
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.Identifier
import io.netty.buffer.ByteBuf

abstract class SingletonPayload<T: SingletonPayload<T>>(id: Identifier) : CustomPacketPayload {
    @JvmField
    val type = CustomPacketPayload.Type<T>(id)
    @JvmField
    @Suppress("UNCHECKED_CAST")
    val codec: StreamCodec<ByteBuf, T> = StreamCodec.unit(this as T)

    override fun type(): CustomPacketPayload.Type<T> = type
}

fun <T: SingletonPayload<T>> PayloadTypeRegistry<out FriendlyByteBuf>.register(payload: T) {
    register(payload.type, payload.codec)
}

data object NoDamageEffectsPayload : SingletonPayload<NoDamageEffectsPayload>(NetherArchives.id("no_damage_effects"))