package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.Registry
import net.minecraft.resources.Identifier

object NetherArchivesParticles {
    private fun <T: ParticleType<*>> register(id: Identifier, particleType: T): T =
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, id, particleType)

    private fun <T: ParticleType<*>> register(path: String, particleType: T): T =
        register(NetherArchives.id(path), particleType)

    val BLAZE_FLAME: SimpleParticleType = register("blaze_flame", FabricParticleTypes.simple(true))
    val BLAZE_SPARK: SimpleParticleType = register("blaze_spark", FabricParticleTypes.simple())
    val SMALL_BLAZE_SPARK: SimpleParticleType = register("small_blaze_spark", FabricParticleTypes.simple())

    fun register() {}
}
