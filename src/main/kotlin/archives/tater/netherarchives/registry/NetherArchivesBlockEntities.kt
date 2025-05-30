package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.block.entity.BasaltGeyserBlockEntity
import archives.tater.netherarchives.block.entity.BlazeTorchBlockEntity
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.math.BlockPos

object NetherArchivesBlockEntities {
    private fun <T: BlockEntity> register(
        path: String,
        blockEntity: (pos: BlockPos, state: BlockState) -> T,
        vararg blocks: Block
    ): BlockEntityType<T> =
        Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            NetherArchives.id(path),
            BlockEntityType.Builder.create(blockEntity, *blocks).build()
        )

    val BLAZE_TORCH_ENTITY = register("blaze_torch", ::BlazeTorchBlockEntity, NetherArchivesBlocks.BLAZE_TORCH, NetherArchivesBlocks.WALL_BLAZE_TORCH)

    val BASALT_GEYSER_ENTITY = register("basalt_geyser", ::BasaltGeyserBlockEntity, NetherArchivesBlocks.BASALT_GEYSER, NetherArchivesBlocks.POLISHED_BASALT_GEYSER)

    fun register() {}
}
