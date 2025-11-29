package archives.tater.netherarchives.client.render.particle

import net.minecraft.client.particle.SingleQuadParticle
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.SpriteSet
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.particles.SimpleParticleType
import net.minecraft.util.RandomSource

class BlazeSparkParticle(clientWorld: ClientLevel, x: Double, y: Double, z: Double, scale: Float, private val spriteProvider: SpriteSet) :
    SingleQuadParticle(clientWorld, x, y, z, spriteProvider.first()) {
        init {
            this.quadSize = scale
            lifetime = 30 + random.nextInt(10)
        }

    private val velocityStep = scale * (0.02 + 0.02 * Math.random())

    override fun getLayer(): Layer = Layer.TRANSLUCENT

    override fun tick() {
        xo = x
        yo = y
        zo = z

        if (age++ >= lifetime) {
            remove()
            return
        }

        move(xd, yd, zd)
        setSpriteFromAge(spriteProvider)
        if (lifetime - age < 10)
            alpha = (lifetime - age) / 10f
        yd += velocityStep
    }

    class Factory(private val spriteProvider: SpriteSet) : ParticleProvider<SimpleParticleType> {
        override fun createParticle(
            parameters: SimpleParticleType,
            world: ClientLevel,
            x: Double,
            y: Double,
            z: Double,
            velocityX: Double,
            velocityY: Double,
            velocityZ: Double,
            random: RandomSource
        ): Particle {
            return BlazeSparkParticle(world, x, y, z, 0.25f, spriteProvider)
        }
    }

    class SmallFactory(private val spriteProvider: SpriteSet) : ParticleProvider<SimpleParticleType> {
        override fun createParticle(
            parameters: SimpleParticleType,
            world: ClientLevel,
            x: Double,
            y: Double,
            z: Double,
            velocityX: Double,
            velocityY: Double,
            velocityZ: Double,
            random: RandomSource
        ): Particle {
            return BlazeSparkParticle(world, x, y, z, 0.125f, spriteProvider)
        }
    }
}
