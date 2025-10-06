package archives.tater.netherarchives.client.render.particle

import net.minecraft.client.particle.BillboardParticle
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleFactory
import net.minecraft.client.particle.SpriteProvider
import net.minecraft.client.world.ClientWorld
import net.minecraft.particle.SimpleParticleType
import net.minecraft.util.math.random.Random

class BlazeSparkParticle(clientWorld: ClientWorld, x: Double, y: Double, z: Double, scale: Float, private val spriteProvider: SpriteProvider) :
    BillboardParticle(clientWorld, x, y, z, spriteProvider.first) {
        init {
            this.scale = scale
            maxAge = 30 + random.nextInt(10)
        }

    private val velocityStep = scale * (0.02 + 0.02 * Math.random())

    override fun getRenderType(): RenderType = RenderType.PARTICLE_ATLAS_TRANSLUCENT

    override fun tick() {
        lastX = x
        lastY = y
        lastZ = z

        if (age++ >= maxAge) {
            markDead()
            return
        }

        move(velocityX, velocityY, velocityZ)
        updateSprite(spriteProvider)
        if (maxAge - age < 10)
            alpha = (maxAge - age) / 10f
        velocityY += velocityStep
    }

    class Factory(private val spriteProvider: SpriteProvider) : ParticleFactory<SimpleParticleType> {
        override fun createParticle(
            parameters: SimpleParticleType,
            world: ClientWorld,
            x: Double,
            y: Double,
            z: Double,
            velocityX: Double,
            velocityY: Double,
            velocityZ: Double,
            random: Random
        ): Particle {
            return BlazeSparkParticle(world, x, y, z, 0.25f, spriteProvider)
        }
    }

    class SmallFactory(private val spriteProvider: SpriteProvider) : ParticleFactory<SimpleParticleType> {
        override fun createParticle(
            parameters: SimpleParticleType,
            world: ClientWorld,
            x: Double,
            y: Double,
            z: Double,
            velocityX: Double,
            velocityY: Double,
            velocityZ: Double,
            random: Random
        ): Particle {
            return BlazeSparkParticle(world, x, y, z, 0.125f, spriteProvider)
        }
    }
}
