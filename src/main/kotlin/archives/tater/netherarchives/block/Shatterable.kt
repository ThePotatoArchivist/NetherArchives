package archives.tater.netherarchives.block

import archives.tater.netherarchives.mixin.FireworkRocketEntityAccessor
import archives.tater.netherarchives.registry.ModTags
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

    fun onExplosionHit(
        state: BlockState,
        level: ServerLevel,
        pos: BlockPos,
        explosion: Explosion,
        onHit: BiConsumer<ItemStack, BlockPos>
    ) {
        if (explosion.blockInteraction == BlockInteraction.TRIGGER_BLOCK) return
        shatter(level, pos, state)
    }

    fun onProjectileHit(
        level: Level,
        state: BlockState,
        hit: BlockHitResult,
        projectile: Projectile
    ) {
        val pos = hit.blockPos
        if (level !is ServerLevel || !projectile.mayInteract(level, pos) || !projectile.mayBreak(level) || projectile isIn ModTags.NON_SHATTER_PROJECTILES) return

        if (projectile isIn ModTags.NON_CHAIN_SHATTER_PROJECTILES ||
            (projectile is FireworkRocketEntity && !(projectile as FireworkRocketEntityAccessor).invokeHasExplosion())) {

            shatter(level, pos, state)
            return
        }
        shatterChain(level, pos, state, 0.5f)
    }

    fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        shatterChain(level, pos, level[pos], 0.1f)
    }

    fun shatterChain(level: Level, pos: BlockPos, state: BlockState, chance: Float) {
        shatter(level, pos, state)
        for (otherPos in BlockPos.withinManhattan(pos, 1, 1, 1)) {
            val block = level[otherPos].block
            if (block is Shatterable && level.random.nextFloat() < chance)
                level.scheduleTick(otherPos, block, level.random.nextIntBetweenInclusive(3, 8))
        }
    }

    fun shatter(level: Level, pos: BlockPos, state: BlockState) {
        level[pos] = shattersTo.withPropertiesOf(state)
        level.playSound(null, pos, shatterSound, SoundSource.BLOCKS, 1f, 1f)
        (level as? ServerLevel)?.sendParticles(
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
