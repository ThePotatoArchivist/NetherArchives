package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.entity.BlazeLanternEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.EntityType.EntityFactory
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier

object NetherArchivesEntities {
    private fun <T: Entity> register(id: Identifier, type: EntityType.Builder<T>): EntityType<T> {
        val key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, id)
        return Registry.register(Registries.ENTITY_TYPE, key, type.build(key))
    }

    private fun <T: Entity> register(path: String, type: EntityType.Builder<T>) =
        register(NetherArchives.id(path), type)

    private fun <T: Entity> register(path: String, factory: EntityFactory<T>, spawnGroup: SpawnGroup = SpawnGroup.MISC, init: EntityType.Builder<T>.() -> Unit) =
        register(path, EntityType.Builder.create(factory, spawnGroup).apply(init))

    private fun <T: Entity> registerProjectile(path: String, factory: EntityFactory<T>, init: EntityType.Builder<T>.() -> Unit = {}) =
        register(path, factory) {
            dimensions(0.25f, 0.25f)
            maxTrackingRange(4)
            trackingTickInterval(10)
            init()
        }

    val BLAZE_LANTERN: EntityType<BlazeLanternEntity> = registerProjectile("blaze_lantern", ::BlazeLanternEntity)

    fun register() {}
}
