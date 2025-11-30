package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.entity.BlazeLanternEntity
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EntityType.EntityFactory
import net.minecraft.world.entity.MobCategory

object NetherArchivesEntities {
    private fun <T: Entity> register(id: Identifier, type: EntityType.Builder<T>): EntityType<T> {
        val key = ResourceKey.create(Registries.ENTITY_TYPE, id)
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, type.build(key))
    }

    private fun <T: Entity> register(path: String, type: EntityType.Builder<T>) =
        register(NetherArchives.id(path), type)

    private fun <T: Entity> register(path: String, factory: EntityFactory<T>, spawnGroup: MobCategory = MobCategory.MISC, init: EntityType.Builder<T>.() -> Unit) =
        register(path, EntityType.Builder.of(factory, spawnGroup).apply(init))

    private fun <T: Entity> registerProjectile(path: String, factory: EntityFactory<T>, init: EntityType.Builder<T>.() -> Unit = {}) =
        register(path, factory) {
            sized(0.25f, 0.25f)
            clientTrackingRange(4)
            updateInterval(10)
            init()
        }

    val BLAZE_LANTERN: EntityType<BlazeLanternEntity> = registerProjectile("blaze_lantern", ::BlazeLanternEntity)

    fun register() {}
}
