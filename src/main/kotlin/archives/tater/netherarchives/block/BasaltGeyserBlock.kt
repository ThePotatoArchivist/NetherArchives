package archives.tater.netherarchives.block

import archives.tater.netherarchives.block.entity.BasaltGeyserBlockEntity
import archives.tater.netherarchives.duck.isAirSkiing
import archives.tater.netherarchives.item.SkisItem
import archives.tater.netherarchives.registry.NetherArchivesBlockEntities
import archives.tater.netherarchives.registry.NetherArchivesTriggers
import archives.tater.netherarchives.util.*
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerPlayer
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.monster.Strider
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DirectionalBlock
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import kotlin.math.abs

open class BasaltGeyserBlock(settings: Properties) : DirectionalBlock(settings), EntityBlock {
    init {
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.UP))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun getStateForPlacement(ctx: BlockPlaceContext): BlockState = defaultBlockState().setValue(FACING, ctx.clickedFace)

    override fun codec(): MapCodec<out DirectionalBlock> = CODEC

    override fun animateTick(state: BlockState, world: Level, pos: BlockPos, random: RandomSource) {
        val facing = state.getValue(FACING)
        val coveringPos = pos.relative(facing)
        if (world[coveringPos].isFaceSturdy(world, coveringPos, facing.opposite)) return
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

    protected open fun addImportantParticles(world: Level, pos: BlockPos, facing: Direction) {
        world.addFaceParticle(
            ParticleTypes.CAMPFIRE_COSY_SMOKE,
            facing,
            pos,
            0.05 * world.random.nextDouble() + 0.05,
            posSpread = 0.25,
            alwaysSpawn = true,
        )
    }

    open fun getPushDistance(world: Level, pos: BlockPos, state: BlockState) = BOOST_RANGE

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return BasaltGeyserBlockEntity(pos, state)
    }

    override fun <T : BlockEntity> getTicker(
        world: Level,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        @Suppress("UNCHECKED_CAST")
        return if (type == NetherArchivesBlockEntities.BASALT_GEYSER_ENTITY) Companion as BlockEntityTicker<T> else null
    }

    companion object : BlockEntityTicker<BasaltGeyserBlockEntity> {
        val CODEC: MapCodec<BasaltGeyserBlock> = simpleCodec(::BasaltGeyserBlock)

        private const val BOOST_RANGE = 8
        private const val MAX_BOOST_VELOCITY = 0.5
        private const val SNEAKING_MAX_BOOST_VELOCITY = 0.12

        override fun tick(world: Level, pos: BlockPos, state: BlockState, blockEntity: BasaltGeyserBlockEntity) {
            val geyserBlock = world[pos].block as? BasaltGeyserBlock ?: return

            val facing = state.getValue(FACING)
            blockEntity.updateDistance(world, pos, state)
            val pushDistance = blockEntity.pushDistance
            val distance = blockEntity.distance

            if (distance == 0) return

            world.getEntities(null, AABB.encapsulatingFullBlocks(pos, pos.relative(facing, distance))) { it !is Strider && (it !is Player || !it.abilities.flying) }.forEach {
                val closeness = (1 - abs((it.pos - pos.center.relative(facing, 0.5)).get(facing.axis)) / pushDistance.toDouble()).coerceAtLeast(0.0)

                it.deltaMovement += Vec3.ZERO.relative(facing, (if (it.isShiftKeyDown) SNEAKING_MAX_BOOST_VELOCITY else MAX_BOOST_VELOCITY) * closeness)
                if (it is LivingEntity && SkisItem.wearsSkis(it)) {
                    it.isAirSkiing = true
                    if (it is ServerPlayer)
                        NetherArchivesTriggers.AIRSKI.trigger(it, pos)
                }
                // Cancel fall damage
                if (facing == Direction.UP)
                    it.resetFallDistance()
            }
            if (world.isClientSide && world.random.nextFloat() < 0.04) {
                geyserBlock.addImportantParticles(world, pos, facing)
            }
        }

        @JvmStatic
        protected fun Level.addFaceParticle(
            parameters: ParticleOptions,
            face: Direction,
            pos: BlockPos,
            forwardVelocity: Double,
            posSpread: Double = 0.0,
            velocityDev: Double = 0.0,
            alwaysSpawn: Boolean = false
        ) {
            val (px, py, pz) = pos.center + face.rotate(
                Vec3(
                if (posSpread == 0.0) 0.0 else random.triangle(0.0, posSpread),
                0.5,
                if (posSpread == 0.0) 0.0 else random.triangle(0.0, posSpread),
            ))
            val (vx, vy, vz) = face.rotate(
                Vec3(
                if (velocityDev == 0.0) 0.0 else random.triangle(0.0, velocityDev),
                forwardVelocity,
                if (velocityDev == 0.0) 0.0 else random.triangle(0.0, velocityDev),
            ))
            this.addParticle(parameters, alwaysSpawn, alwaysSpawn, px, py, pz, vx, vy, vz)
        }

        private fun Direction.rotate(vec3d: Vec3) = when (this) {
            Direction.UP -> vec3d
            else -> {
                val (x, y, z) = vec3d
                when (this) {
                    Direction.DOWN -> Vec3(x, -y, -z)
                    Direction.NORTH -> Vec3(x, z, -y)
                    Direction.SOUTH -> Vec3(-z, -x, y)
                    Direction.EAST -> Vec3(y, z, x)
                    Direction.WEST -> Vec3(-y, z, -x)
                    else -> throw AssertionError()
                }
            }
        }
    }
}
