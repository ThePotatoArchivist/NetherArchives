package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.component.AshAmount
import net.minecraft.core.Registry
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.BuiltInRegistries

object NetherArchivesComponents {
    private fun <T: Any> register(path: String, init: DataComponentType.Builder<T>.() -> Unit) = Registry.register(
        BuiltInRegistries.DATA_COMPONENT_TYPE,
        NetherArchives.id(path),
        DataComponentType.builder<T>().apply(init).build()
    )

    @JvmField
    val ASH_AMOUNT = register("ash_amount") {
        persistent(AshAmount.CODEC)
        networkSynchronized(AshAmount.STREAM_CODEC)
    }

    fun init() {}
}

internal typealias ModComponents = NetherArchivesComponents