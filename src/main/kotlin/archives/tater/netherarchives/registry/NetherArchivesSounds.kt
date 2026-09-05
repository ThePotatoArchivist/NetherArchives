package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.Identifier
import net.minecraft.sounds.SoundEvent

object NetherArchivesSounds {
    val BLAZE_FIRE_IGNITE = register("block.blaze_fire.ignite")
    val BLAZE_LANTERN_THROW = register("entity.blaze_lantern.throw")
    val BLAZE_LANTERN_SHATTER = register("entity.blaze_lantern.shatter")
    val BASALT_OAR_PADDLE = register("item.basalt_oar.paddle")
    val BASALT_OAR_PADDLE_LAVA = register("item.basalt_oar.paddle.lava")

    private fun register(path: String) = register(NetherArchives.id(path))

    private fun register(id: Identifier) = Registry.register(
        BuiltInRegistries.SOUND_EVENT,
        id,
        SoundEvent.createVariableRangeEvent(id)
    )

    fun init() {}
}

internal typealias ModSounds = NetherArchivesSounds