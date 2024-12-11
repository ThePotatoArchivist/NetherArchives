package archives.tater.netherarchives.block

import archives.tater.netherarchives.item.SkisItem
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.particle.ParticleTypes
import net.minecraft.registry.tag.FluidTags
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.world.World

class SmolderingMagnetiteBlock(settings: Settings) : Block(settings.ticksRandomly()) {
    override fun hasRandomTicks(state: BlockState?) = true

    @Suppress("OVERRIDE_DEPRECATION")
    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        if (Direction.entries.none {
                world.getFluidState(pos.offset(it)).isIn(FluidTags.LAVA)
            }) {
            world.setBlockState(pos, NetherArchivesBlocks.MAGNETITE.defaultState)
        }

    }

    // Copied from Magma Block
    override fun onSteppedOn(world: World, pos: BlockPos?, state: BlockState?, entity: Entity) {
        if (!entity.bypassesSteppingEffects() && entity is LivingEntity && !EnchantmentHelper.hasFrostWalker(entity) && !SkisItem.wearsSkis(entity)) {
            entity.damage(world.damageSources.hotFloor(), 1.0f)
        }
        super.onSteppedOn(world, pos, state, entity)
    }

    // Copied from Crying Obsidian
    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        if (random.nextInt(4) != 0) {
            return
        }
        val direction = Direction.random(random)
        if (direction == Direction.UP) {
            return
        }
        val blockPos = pos.offset(direction)
        val blockState = world.getBlockState(blockPos)
        if (state.isOpaque && blockState.isSideSolidFullSquare(world, blockPos, direction.opposite)) {
            return
        }
        val d = if (direction.offsetX == 0) random.nextDouble() else 0.5 + direction.offsetX.toDouble() * 0.6
        val e = if (direction.offsetY == 0) random.nextDouble() else 0.5 + direction.offsetY.toDouble() * 0.6
        val f = if (direction.offsetZ == 0) random.nextDouble() else 0.5 + direction.offsetZ.toDouble() * 0.6
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
