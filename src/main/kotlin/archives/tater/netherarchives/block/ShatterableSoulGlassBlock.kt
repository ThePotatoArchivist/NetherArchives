package archives.tater.netherarchives.block

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvent
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import java.util.function.BiConsumer

class ShatterableSoulGlassBlock(override val shattersTo: Block, settings: Properties) : SoulGlassBlock(settings), Shatterable {

    override val shatterSound: SoundEvent
        get() = soundType.breakSound

    override fun onExplosionHit(
        state: BlockState,
        world: Level,
        pos: BlockPos,
        explosion: Explosion,
        stackMerger: BiConsumer<ItemStack, BlockPos>
    ) {
        super<Shatterable>.onExplosionHit(state, world, pos, explosion, stackMerger)
    }

    override fun onProjectileHit(
        world: Level,
        state: BlockState,
        hit: BlockHitResult,
        projectile: Projectile
    ) {
        super<Shatterable>.onProjectileHit(world, state, hit, projectile)
    }

    override fun tick(state: BlockState, world: ServerLevel, pos: BlockPos, random: RandomSource) {
        super<Shatterable>.tick(state, world, pos, random)
    }
}
