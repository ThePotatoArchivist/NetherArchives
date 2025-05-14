package archives.tater.netherarchives.block

import archives.tater.netherarchives.*
import archives.tater.netherarchives.block.entity.BasaltGeyserBlockEntity
import archives.tater.netherarchives.registry.NetherArchivesBlockEntities
import archives.tater.netherarchives.duck.isAirSkiing
import archives.tater.netherarchives.item.SkisItem
import com.mojang.serialization.MapCodec
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.FacingBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.passive.StriderEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.state.StateManager
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.random.Random
import net.minecraft.world.World
import kotlin.math.abs

class BasaltGeyserBlock(settings: Settings) : FacingBlock(settings), BlockEntityProvider {
    init {
        defaultState = stateManager.defaultState.with(FACING, Direction.UP)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState = defaultState.with(FACING, ctx.side)

    override fun getCodec(): MapCodec<out FacingBlock> = CODEC

    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        val facing = state[FACING]
        val coveringPos = pos.offset(facing)
        if (world[coveringPos].isSideSolidFullSquare(world, coveringPos, facing.opposite)) return;
        repeat(4) {
            world.addFaceParticle(
                ParticleTypes.LARGE_SMOKE,
                facing,
                pos,
                0.15 * random.nextDouble() + 0.15,
                posSpread = 0.5,
            )
        }
        world.addFaceParticle(
            ParticleTypes.LAVA,
            facing,
            pos,
            random.nextDouble() + 0.5,
            velocityDev = 0.5,
        )
        world.addFaceParticle(
            ParticleTypes.CAMPFIRE_COSY_SMOKE,
            facing,
            pos,
            0.05 * world.random.nextDouble() + 0.05,
            0.25,
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
        val CODEC: MapCodec<BasaltGeyserBlock> = createCodec(::BasaltGeyserBlock)

        private const val BOOST_RANGE = 8
        private const val MAX_BOOST_VELOCITY = 0.5
        private const val SNEAKING_MAX_BOOST_VELOCITY = 0.12

        override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: BasaltGeyserBlockEntity) {
            val facing = state[FACING]

            val distance = iterateLinearBlockPos(pos, facing, BOOST_RANGE)
                .indexOfFirst { world[it].run { isSideSolidFullSquare(world, it, facing) or isSideSolidFullSquare(world, it, facing.opposite) } }
                .let { if (it == -1) BOOST_RANGE else it }

            if (distance == 0) return

            world.getOtherEntities(null, Box.enclosing(pos, pos.offset(facing, distance))) { it !is StriderEntity && (it !is PlayerEntity || !it.abilities.flying) }.forEach {
                val closeness = (1 - abs((it.pos - pos.toCenterPos().offset(facing, 0.5)).getComponentAlongAxis(facing.axis)) / BOOST_RANGE.toDouble()).coerceAtLeast(0.0)

                it.velocity += Vec3d.ZERO.offset(facing, (if (it.isSneaking) SNEAKING_MAX_BOOST_VELOCITY else MAX_BOOST_VELOCITY) * closeness)
                if (it is LivingEntity && SkisItem.wearsSkis(it)) {
                    it.isAirSkiing = true
                }
                // Cancel fall damage
                it.onLanding()
            }
            if (world.isClient && world.random.nextFloat() < 0.04) {
                world.addFaceParticle(
                    ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    facing,
                    pos,
                    0.05 * world.random.nextDouble() + 0.05,
                    posSpread = 0.25,
                    alwaysSpawn = true,
                )
            }
        }

        private fun World.addFaceParticle(
            parameters: ParticleEffect,
            face: Direction,
            pos: BlockPos,
            forwardVelocity: Double,
            posSpread: Double = 0.0,
            velocityDev: Double = 0.0,
            alwaysSpawn: Boolean = false
        ) {
            val (px, py, pz) = pos.toCenterPos() + face.rotate(Vec3d(
                if (posSpread == 0.0) 0.0 else random.nextTriangular(0.0, posSpread),
                0.5,
                if (posSpread == 0.0) 0.0 else random.nextTriangular(0.0, posSpread),
            ))
            val (vx, vy, vz) = face.rotate(Vec3d(
                if (velocityDev == 0.0) 0.0 else random.nextTriangular(0.0, velocityDev),
                forwardVelocity,
                if (velocityDev == 0.0) 0.0 else random.nextTriangular(0.0, velocityDev),
            ))
            this.addParticle(parameters, alwaysSpawn, px, py, pz, vx, vy, vz)
        }

        private fun Direction.rotate(vec3d: Vec3d) = when (this) {
            Direction.UP -> vec3d
            else -> {
                val (x, y, z) = vec3d
                when (this) {
                    Direction.DOWN -> Vec3d(x, -y, -z)
                    Direction.NORTH -> Vec3d(x, z, -y)
                    Direction.SOUTH -> Vec3d(-z, -x, y)
                    Direction.EAST -> Vec3d(y, z, x)
                    Direction.WEST -> Vec3d(-y, z, -x)
                    else -> throw AssertionError()
                }
            }
        }
    }
}
