package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.util.McUnit
import net.minecraft.core.Registry
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.BuiltInRegistries

object NetherArchivesComponents {
    private inline fun <T: Any> register(path: String, init: DataComponentType.Builder<T>.() -> Unit) =
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, NetherArchives.id(path), DataComponentType.builder<T>().apply(init).build())

    private fun registerUnit(path: String): DataComponentType<McUnit> = register(path) {
        persistent(McUnit.CODEC)
        networkSynchronized(McUnit.STREAM_CODEC)
    }

    @JvmField val SKIS = registerUnit("skis")

    fun init() {}
}
