package archives.tater.netherarchives.block

import archives.tater.netherarchives.block.entity.SoulGlassBlockEntity
import archives.tater.netherarchives.get
import archives.tater.netherarchives.set
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.TransparentBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.item.ItemStack
import net.minecraft.particle.BlockStateParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.explosion.Explosion
import net.minecraft.world.explosion.Explosion.DestructionType
import java.util.function.BiConsumer

class ActiveSoulGlassBlock(private val shattersTo: Block, settings: Settings) : SoulGlassBlock(settings), BlockEntityProvider {

    override fun onExploded(
        state: BlockState,
        world: World,
        pos: BlockPos,
        explosion: Explosion,
        stackMerger: BiConsumer<ItemStack, BlockPos>
    ) {
        if (explosion.destructionType == DestructionType.TRIGGER_BLOCK) return
        shatter(world, pos, state)
    }

    override fun onProjectileHit(
        world: World,
        state: BlockState,
        hit: BlockHitResult,
        projectile: ProjectileEntity
    ) {
        val pos = hit.blockPos
        if (world.isClient || !projectile.canModifyAt(world, pos) || !projectile.canBreakBlocks(world)) return
        shatter(world, pos, world[pos])
    }

    private fun shatter(world: World, pos: BlockPos, state: BlockState) {
        world[pos] = shattersTo.defaultState
        world.playSound(null, pos, soundGroup.breakSound, SoundCategory.BLOCKS, 1f, 1f)
        (world as? ServerWorld)?.spawnParticles(
            BlockStateParticleEffect(ParticleTypes.BLOCK, state),
            pos.x + 0.5,
            pos.y + 0.5,
            pos.z + 0.5,
            32,
            0.5,
            0.5,
            0.5,
            0.0
        )
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = SoulGlassBlockEntity(pos, state)
}
