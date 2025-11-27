package archives.tater.netherarchives.client.render.particle

import net.minecraft.client.particle.*
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.particles.SimpleParticleType

class BlazeSparkParticle(clientWorld: ClientLevel, x: Double, y: Double, z: Double, scale: Float, private val spriteProvider: SpriteSet) :
    TextureSheetParticle(clientWorld, x, y, z) {
        init {
            this.quadSize = scale
            setSpriteFromAge(spriteProvider)
            lifetime = 30 + random.nextInt(10)
        }

    private val velocityStep = scale * (0.02 + 0.02 * Math.random())

    override fun getRenderType(): ParticleRenderType = ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT

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
            velocityZ: Double
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
            velocityZ: Double
        ): Particle {
            return BlazeSparkParticle(world, x, y, z, 0.125f, spriteProvider)
        }
    }
}
