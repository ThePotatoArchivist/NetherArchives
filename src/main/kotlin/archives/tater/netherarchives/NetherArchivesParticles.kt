package archives.tater.netherarchives

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes
import net.minecraft.particle.DefaultParticleType
import net.minecraft.particle.ParticleType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object NetherArchivesParticles {
    private fun <T: ParticleType<*>> register(id: Identifier, particleType: T): T =
        Registry.register(Registries.PARTICLE_TYPE, id, particleType)

    private fun <T: ParticleType<*>> register(path: String, particleType: T): T =
        register(Identifier(NetherArchives.MOD_ID, path), particleType)

    val BLAZE_FLAME: DefaultParticleType = register("blaze_flame", FabricParticleTypes.simple(true))
    val BLAZE_SPARK: DefaultParticleType = register("blaze_spark", FabricParticleTypes.simple())
    val SMALL_BLAZE_SPARK: DefaultParticleType = register("small_blaze_spark", FabricParticleTypes.simple())

    fun register() {}
}
