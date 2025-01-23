package archives.tater.netherarchives.client.render.particle

import net.minecraft.client.particle.*
import net.minecraft.client.world.ClientWorld
import net.minecraft.particle.DefaultParticleType

class BlazeSparkParticle(clientWorld: ClientWorld, x: Double, y: Double, z: Double, scale: Float, private val spriteProvider: SpriteProvider) :
    SpriteBillboardParticle(clientWorld, x, y, z) {
        init {
            this.scale = scale
            setSpriteForAge(spriteProvider)
            maxAge = 30 + random.nextInt(10)
        }

    private val velocityStep = scale * (0.02 + 0.02 * Math.random())

    override fun getType(): ParticleTextureSheet = ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT

    override fun tick() {
        prevPosX = x
        prevPosY = y
        prevPosZ = z
        if (age++ >= maxAge) {
            markDead()
            return
        }

        move(velocityX, velocityY, velocityZ)
        setSpriteForAge(spriteProvider)
        if (maxAge - age < 10)
            alpha = (maxAge - age) / 10f
        velocityY += velocityStep
    }

    class Factory(private val spriteProvider: SpriteProvider) : ParticleFactory<DefaultParticleType> {
        override fun createParticle(
            parameters: DefaultParticleType,
            world: ClientWorld,
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

    class SmallFactory(private val spriteProvider: SpriteProvider) : ParticleFactory<DefaultParticleType> {
        override fun createParticle(
            parameters: DefaultParticleType,
            world: ClientWorld,
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
