package archives.tater.netherarchives.block

import archives.tater.netherarchives.block.entity.BlazeTorchBlockEntity
import net.minecraft.block.Block.NOTIFY_LISTENERS
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.world.World


interface AbstractBlazeTorchBlock : BlockEntityProvider {
    override fun createBlockEntity(pos: BlockPos, state: BlockState) = BlazeTorchBlockEntity(pos, state)

    fun onBlockAdded(
        state: BlockState,
        world: World,
        pos: BlockPos,
    ) {
        if (world.isClient) return
        (world.getBlockEntity(pos) as BlazeTorchBlockEntity).locateTarget()
            .thenAccept {
                if (it === null) return@thenAccept
                world.updateListeners(pos, state, state, NOTIFY_LISTENERS)
            }
    }

    fun randomDisplayTick(
        state: BlockState,
        world: World,
        pos: BlockPos,
        random: Random,
        originX: Double,
        originY: Double,
        originZ: Double
    ) {
        val blockEntity = world.getBlockEntity(pos) as BlazeTorchBlockEntity

        world.addImportantParticle(
            ParticleTypes.FLAME,
            originX,
            originY,
            originZ,
            0.1 * (blockEntity.xVelocityCoef ?: 0.0),
            0.02,
            0.1 * (blockEntity.zVelocityCoef ?: 0.0)
        )
    }
}
