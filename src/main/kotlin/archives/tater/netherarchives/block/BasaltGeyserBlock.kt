package archives.tater.netherarchives.block

import archives.tater.netherarchives.block.entity.BasaltGeyserBlockEntity
import archives.tater.netherarchives.block.entity.NetherArchivesBlockEntities
import archives.tater.netherarchives.duck.isAirSkiing
import archives.tater.netherarchives.item.SkisItem
import archives.tater.netherarchives.plus
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.passive.StriderEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.random.Random
import net.minecraft.world.World

class BasaltGeyserBlock(settings: Settings) : Block(settings), BlockEntityProvider {
    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        repeat(4) {
            world.addParticle(
                ParticleTypes.LARGE_SMOKE,
                random.nextTriangular(pos.x + 0.5, 0.5),
                pos.y + 1.0,
                random.nextTriangular(pos.z + 0.5, 0.5),
                0.0,
                0.15 * random.nextDouble() + 0.15,
                0.0
            )
        }
        world.addParticle(
            ParticleTypes.LAVA,
            pos.x + 0.5,
            pos.y + 1.0,
            pos.z + 0.5,
            random.nextTriangular(0.0, 0.5),
            random.nextDouble() + 0.5,
            random.nextTriangular(0.0, 0.5)
        )
        world.addParticle(
            ParticleTypes.CAMPFIRE_COSY_SMOKE,
            world.random.nextTriangular(pos.x + 0.5, 0.25),
            pos.y + 1.0,
            world.random.nextTriangular(pos.z + 0.5, 0.25),
            0.0,
            0.05 * world.random.nextDouble() + 0.05,
            0.0
        )
    }

    override fun onSteppedOn(world: World, pos: BlockPos, state: BlockState, entity: Entity) {
//        if (entity is LivingEntity && entity.getEquippedStack(EquipmentSlot.FEET).isEmpty) {
//            entity.damage(world.damageSources.hotFloor(), 1f)
//        }
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return BasaltGeyserBlockEntity(pos, state)
    }

    override fun <T : BlockEntity> getTicker(
        world: World?,
        state: BlockState?,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        @Suppress("UNCHECKED_CAST")
        return if (type == NetherArchivesBlockEntities.BASALT_GEYSER_ENTITY) Companion as BlockEntityTicker<T> else null
    }

    companion object : BlockEntityTicker<BasaltGeyserBlockEntity> {
        private const val BOOST_RANGE = 10
        private const val MAX_BOOST_VELOCITY = 0.5
        private const val SNEAKING_MAX_BOOST_VELOCITY = 0.12

        override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: BasaltGeyserBlockEntity) {
            world.getOtherEntities(null, Box.enclosing(pos, pos.add(0, BOOST_RANGE, 0))) { it !is StriderEntity && (it !is PlayerEntity || !it.abilities.flying) }.forEach {
                it.velocity += Vec3d(0.0, (if (it.isSneaking) SNEAKING_MAX_BOOST_VELOCITY else MAX_BOOST_VELOCITY) * (1 - (it.y - pos.y + 1) / BOOST_RANGE.toDouble()), 0.0)
                if (it is LivingEntity && SkisItem.wearsSkis(it)) {
                    it.isAirSkiing = true
                }
                // Cancel fall damage
                it.onLanding()
            }
            if (world.isClient && world.random.nextFloat() < 0.04)
                world.addParticle(
                    ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    true,
                    world.random.nextTriangular(pos.x + 0.5, 0.25),
                    pos.y + 1.0,
                    world.random.nextTriangular(pos.z + 0.5, 0.25),
                    0.0,
                    0.05 * world.random.nextDouble() + 0.05,
                    0.0
                )
        }
    }
}
