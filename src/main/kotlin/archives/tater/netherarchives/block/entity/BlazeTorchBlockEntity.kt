package archives.tater.netherarchives.block.entity

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.registry.NetherArchivesBlockEntities
import archives.tater.netherarchives.registry.NetherArchivesTags
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.storage.ValueInput
import net.minecraft.world.level.storage.ValueOutput
import kotlin.jvm.optionals.getOrNull
import kotlin.math.sqrt


class BlazeTorchBlockEntity(pos: BlockPos, state: BlockState) :
    BlockEntity(NetherArchivesBlockEntities.BLAZE_TORCH_ENTITY, pos, state) {
    companion object {
        const val TARGET_KEY = "targetPos"
    }

    var targetPos: BlockPos? = null
        private set(value) {
            field = value
            if (value === null) return
            val xDiff = value.x - worldPosition.x
            val zDiff = value.z - worldPosition.z
            val length = sqrt((xDiff * xDiff + zDiff * zDiff).toDouble())
            xVelocityCoef = xDiff / length
            zVelocityCoef = zDiff / length
        }

    var xVelocityCoef: Double? = null
    var zVelocityCoef: Double? = null

    fun locateTarget(): BlockPos? {
        val pos = (level as ServerLevel).run {
            findNearestMapStructure(NetherArchivesTags.BLAZE_TORCH_LOCATED, worldPosition, 50, false)
        } ?: return null
        targetPos = pos
        NetherArchives.logger.info("Located at ${pos.x}, ${pos.y}, ${pos.z}")
        setChanged()
        return pos
    }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener>? {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    override fun getUpdateTag(registryLookup: HolderLookup.Provider): CompoundTag {
        return saveWithoutMetadata(registryLookup)
    }

    override fun saveAdditional(view: ValueOutput) {
        super.saveAdditional(view)
        if (targetPos !== null)
            view.store(TARGET_KEY, BlockPos.CODEC, targetPos!!)
    }

    override fun loadAdditional(view: ValueInput) {
        super.loadAdditional(view)
        targetPos = view.read(TARGET_KEY, BlockPos.CODEC).getOrNull()
    }
}
