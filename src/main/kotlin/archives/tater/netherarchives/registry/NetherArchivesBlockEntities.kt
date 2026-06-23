package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.block.entity.BasaltGeyserBlockEntity
import archives.tater.netherarchives.block.entity.BlazeTorchBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.core.BlockPos
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

object NetherArchivesBlockEntities {
    private fun <T: BlockEntity> register(
        path: String,
        blockEntity: (pos: BlockPos, state: BlockState) -> T,
        vararg blocks: Block
    ): BlockEntityType<T> =
        Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            NetherArchives.id(path),
            FabricBlockEntityTypeBuilder.create(blockEntity, *blocks).build()
        )

    @JvmField
    val BLAZE_TORCH_ENTITY = register("blaze_torch", ::BlazeTorchBlockEntity, ModBlocks.BLAZE_TORCH, ModBlocks.WALL_BLAZE_TORCH)

    @JvmField
    val BASALT_GEYSER_ENTITY = register("basalt_geyser", ::BasaltGeyserBlockEntity, ModBlocks.BASALT_GEYSER, ModBlocks.ADJUSTABLE_BASALT_GEYSER)

    fun init() {}
}
