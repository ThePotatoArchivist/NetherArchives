package archives.tater.netherarchives.block

import archives.tater.netherarchives.block.entity.BasaltGeyserBlockEntity
import archives.tater.netherarchives.block.entity.NetherArchivesBlockEntities
import archives.tater.netherarchives.duck.isAirSkiing
import archives.tater.netherarchives.item.SkisItem
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.random.Random
import net.minecraft.world.World

class BasaltGeyserBlock(settings: Settings) : Block(settings), BlockEntityProvider {
    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        repeat(4) {
            world.addParticle(ParticleTypes.LARGE_SMOKE, random.nextTriangular(pos.x + 0.5, 0.5), pos.y + 1.0, random.nextTriangular(pos.z + 0.5, 0.5), 0.0, 0.15 * random.nextDouble() + 0.15, 0.0);
        }
        world.addParticle(ParticleTypes.LAVA, pos.x + 0.5, pos.y + 1.0, pos.z + 0.5, random.nextTriangular(0.0, 0.5), random.nextDouble() + 0.5, random.nextTriangular(0.0, 0.5));
        if (random.nextInt(4) < 3)
            world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, random.nextTriangular(pos.x + 0.5, 0.25), pos.y + 1.0, random.nextTriangular(pos.z + 0.5, 0.25), 0.0, 0.05 * random.nextDouble() + 0.05, 0.0);
    }

    override fun onSteppedOn(world: World, pos: BlockPos, state: BlockState, entity: Entity) {
        if (entity is LivingEntity && entity.getEquippedStack(EquipmentSlot.FEET).isEmpty) {
            entity.damage(world.damageSources.hotFloor(), 1f)
        }
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

        override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: BasaltGeyserBlockEntity) {
            world.getOtherEntities(null, Box(pos, pos.add(1, BOOST_RANGE + 1, 1))) { true }.forEach {
                it.addVelocity(0.0, MAX_BOOST_VELOCITY * (1 - (it.y - pos.y + 1) / BOOST_RANGE.toDouble()),0.0)
                if (it is LivingEntity && SkisItem.wearsSkis(it)) {
                    it.isAirSkiing = true
                }
            }
        }
    }
}
