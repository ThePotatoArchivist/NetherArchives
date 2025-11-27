package archives.tater.netherarchives.block

import archives.tater.netherarchives.registry.NetherArchivesBlocks
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.FluidTags
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

class SmolderingMagnetiteBlock(settings: Properties) : Block(settings.randomTicks()) {
    override fun isRandomlyTicking(state: BlockState) = true

    override fun randomTick(state: BlockState, world: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (Direction.entries.none {
                world.getFluidState(pos.relative(it)).`is`(FluidTags.LAVA)
            }) {
            world.setBlockAndUpdate(pos, NetherArchivesBlocks.MAGNETITE.defaultBlockState())
        }

    }

    // Copied from Magma Block
    override fun stepOn(world: Level, pos: BlockPos, state: BlockState, entity: Entity) {
        if (!entity.isSteppingCarefully && entity is LivingEntity) {
            entity.hurt(world.damageSources().hotFloor(), 1.0f)
        }
        super.stepOn(world, pos, state, entity)
    }

    // Copied from Crying Obsidian
    override fun animateTick(state: BlockState, world: Level, pos: BlockPos, random: RandomSource) {
        if (random.nextInt(4) != 0) {
            return
        }
        val direction = Direction.getRandom(random)
        if (direction == Direction.UP) {
            return
        }
        val blockPos = pos.relative(direction)
        val blockState = world.getBlockState(blockPos)
        if (state.canOcclude() && blockState.isFaceSturdy(world, blockPos, direction.opposite)) {
            return
        }
        val d = if (direction.stepX == 0) random.nextDouble() else 0.5 + direction.stepX.toDouble() * 0.6
        val e = if (direction.stepY == 0) random.nextDouble() else 0.5 + direction.stepY.toDouble() * 0.6
        val f = if (direction.stepZ == 0) random.nextDouble() else 0.5 + direction.stepZ.toDouble() * 0.6
        world.addParticle(
            ParticleTypes.DRIPPING_LAVA,
            pos.x.toDouble() + d,
            pos.y.toDouble() + e,
            pos.z.toDouble() + f,
            0.0,
            0.0,
            0.0
        )
    }
}
