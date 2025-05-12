package archives.tater.netherarchives.block.entity

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.datagen.StructureTagGenerator
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtHelper
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.Packet
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
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
            val xDiff = value.x - pos.x
            val zDiff = value.z - pos.z
            val length = sqrt((xDiff * xDiff + zDiff * zDiff).toDouble())
            xVelocityCoef = xDiff / length
            zVelocityCoef = zDiff / length
        }

    var xVelocityCoef: Double? = null
    var zVelocityCoef: Double? = null

    fun locateTarget(): BlockPos? {
        val pos = (world as ServerWorld).run {
            locateStructure(StructureTagGenerator.BLAZE_TORCH_LOCATED, pos, 50, false)
        } ?: return null
        targetPos = pos
        NetherArchives.logger.info("Located at ${pos.x}, ${pos.y}, ${pos.z}")
        markDirty()
        return pos
    }

    override fun toUpdatePacket(): Packet<ClientPlayPacketListener>? {
        return BlockEntityUpdateS2CPacket.create(this)
    }

    override fun toInitialChunkDataNbt(registryLookup: RegistryWrapper.WrapperLookup): NbtCompound {
        return createNbt(registryLookup)
    }

    override fun writeNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
        // Save the current value of the number to the nbt
        if (targetPos !== null)
            nbt.put(TARGET_KEY, targetPos.let(NbtHelper::fromBlockPos))
    }

    // Deserialize the BlockEntity
    override fun readNbt(nbt: NbtCompound, registryLookup: WrapperLookup) {
        if (!nbt.contains(TARGET_KEY)) return
        targetPos = NbtHelper.toBlockPos(nbt, TARGET_KEY).getOrNull()
    }
}
