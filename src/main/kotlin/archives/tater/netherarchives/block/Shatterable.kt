package archives.tater.netherarchives.block

import archives.tater.netherarchives.mixin.FireworkRocketEntityAccessor
import archives.tater.netherarchives.registry.NetherArchivesTags
import archives.tater.netherarchives.util.get
import archives.tater.netherarchives.util.isIn
import archives.tater.netherarchives.util.set
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.BlockParticleOption
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.projectile.FireworkRocketEntity
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.Explosion.BlockInteraction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import java.util.function.BiConsumer

interface Shatterable {
    val shattersTo: Block

    val shatterSound: SoundEvent

    fun onExploded(
        state: BlockState,
        world: Level,
        pos: BlockPos,
        explosion: Explosion,
        stackMerger: BiConsumer<ItemStack, BlockPos>
    ) {
        if (explosion.blockInteraction == BlockInteraction.TRIGGER_BLOCK) return
        shatter(world, pos, state)
    }

    fun onProjectileHit(
        world: Level,
        state: BlockState,
        hit: BlockHitResult,
        projectile: Projectile
    ) {
        val pos = hit.blockPos
        if (world.isClientSide || !projectile.mayInteract(world, pos) || !projectile.mayBreak(world)) return

        if (projectile.type isIn NetherArchivesTags.NON_CHAIN_SHATTER_PROJECTILES ||
            (projectile is FireworkRocketEntity && !(projectile as FireworkRocketEntityAccessor).invokeHasExplosion())) {

            shatter(world, pos, state)
            return
        }
        shatterChain(world, pos, state, 0.5f)
    }

    fun scheduledTick(state: BlockState, world: ServerLevel, pos: BlockPos, random: RandomSource?) {
        shatterChain(world, pos, world[pos], 0.1f)
    }

    fun shatterChain(world: Level, pos: BlockPos, state: BlockState, chance: Float) {
        shatter(world, pos, state)
        for (otherPos in BlockPos.withinManhattan(pos, 1, 1, 1)) {
            val block = world[otherPos].block
            if (block is Shatterable && world.random.nextFloat() < chance)
                world.scheduleTick(otherPos, block, world.random.nextIntBetweenInclusive(3, 8))
        }
    }

    fun shatter(world: Level, pos: BlockPos, state: BlockState) {
        world[pos] = shattersTo.withPropertiesOf(state)
        world.playSound(null, pos, shatterSound, SoundSource.BLOCKS, 1f, 1f)
        (world as? ServerLevel)?.sendParticles(
            BlockParticleOption(ParticleTypes.BLOCK, state),
            pos.x + 0.5,
            pos.y + 0.5,
            pos.z + 0.5,
            32,
            0.3,
            0.3,
            0.3,
            0.0
        )
    }
}
