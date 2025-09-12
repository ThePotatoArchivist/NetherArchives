package archives.tater.netherarchives.block

import archives.tater.netherarchives.mixin.FireworkRocketEntityAccessor
import archives.tater.netherarchives.registry.NetherArchivesTags
import archives.tater.netherarchives.util.get
import archives.tater.netherarchives.util.isIn
import archives.tater.netherarchives.util.set
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.projectile.FireworkRocketEntity
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.item.ItemStack
import net.minecraft.particle.BlockStateParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.world.World
import net.minecraft.world.explosion.Explosion
import net.minecraft.world.explosion.Explosion.DestructionType
import java.util.function.BiConsumer

interface Shatterable {
    val shattersTo: Block

    val shatterSound: SoundEvent

    fun onExploded(
        state: BlockState,
        world: World,
        pos: BlockPos,
        explosion: Explosion,
        stackMerger: BiConsumer<ItemStack, BlockPos>
    ) {
        if (explosion.destructionType == DestructionType.TRIGGER_BLOCK) return
        shatter(world, pos, state)
    }

    fun onProjectileHit(
        world: World,
        state: BlockState,
        hit: BlockHitResult,
        projectile: ProjectileEntity
    ) {
        val pos = hit.blockPos
        if (world.isClient || !projectile.canModifyAt(world, pos) || !projectile.canBreakBlocks(world)) return

        if (projectile.type isIn NetherArchivesTags.NON_CHAIN_SHATTER_PROJECTILES ||
            (projectile is FireworkRocketEntity && !(projectile as FireworkRocketEntityAccessor).invokeHasExplosionEffects())) {

            shatter(world, pos, state)
            return
        }
        shatterChain(world, pos, state, 0.5f)
    }

    fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random?) {
        shatterChain(world, pos, world[pos], 0.1f)
    }

    fun shatterChain(world: World, pos: BlockPos, state: BlockState, chance: Float) {
        shatter(world, pos, state)
        for (otherPos in BlockPos.iterateOutwards(pos, 1, 1, 1)) {
            val block = world[otherPos].block
            if (block is Shatterable && world.random.nextFloat() < chance)
                world.scheduleBlockTick(otherPos, block, world.random.nextBetween(3, 8))
        }
    }

    fun shatter(world: World, pos: BlockPos, state: BlockState) {
        world[pos] = shattersTo.getStateWithProperties(state)
        world.playSound(null, pos, shatterSound, SoundCategory.BLOCKS, 1f, 1f)
        (world as? ServerWorld)?.spawnParticles(
            BlockStateParticleEffect(ParticleTypes.BLOCK, state),
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