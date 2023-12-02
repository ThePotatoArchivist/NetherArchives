package archives.tater.netherarchives.block.entity

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.block.NetherArchivesBlocks
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos

object NetherArchivesBlockEntities {
    private fun register(path: String, blockEntity: (pos: BlockPos, state: BlockState) -> BlockEntity, block: Block): BlockEntityType<BlockEntity> =
        Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier(NetherArchives.NAMESPACE, path),
            FabricBlockEntityTypeBuilder.create(blockEntity, block).build()
        )

    val BLAZE_TORCH_ENTITY = register("blaze_torch", ::BlazeTorchBlockEntity, NetherArchivesBlocks.BLAZE_TORCH )

    fun register() {}
}
