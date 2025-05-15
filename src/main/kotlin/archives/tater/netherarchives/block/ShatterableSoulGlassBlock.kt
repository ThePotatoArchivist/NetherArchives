package archives.tater.netherarchives.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvent
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.world.World
import net.minecraft.world.explosion.Explosion
import java.util.function.BiConsumer

class ShatterableSoulGlassBlock(override val shattersTo: Block, settings: Settings) : SoulGlassBlock(settings), Shatterable {

    override val shatterSound: SoundEvent
        get() = soundGroup.breakSound

    override fun onExploded(
        state: BlockState,
        world: World,
        pos: BlockPos,
        explosion: Explosion,
        stackMerger: BiConsumer<ItemStack, BlockPos>
    ) {
        super<Shatterable>.onExploded(state, world, pos, explosion, stackMerger)
    }

    override fun onProjectileHit(
        world: World,
        state: BlockState,
        hit: BlockHitResult,
        projectile: ProjectileEntity
    ) {
        super<Shatterable>.onProjectileHit(world, state, hit, projectile)
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random?) {
        super<Shatterable>.scheduledTick(state, world, pos, random)
    }
}
