package archives.tater.netherarchives.item

import archives.tater.netherarchives.registry.NetherArchivesTags
import archives.tater.netherarchives.util.isIn
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.fluid.FluidState
import net.minecraft.item.ArmorItem
import net.minecraft.item.ArmorMaterial
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.util.shape.VoxelShape

class SkisItem(material: RegistryEntry<ArmorMaterial>, settings: Settings) : ArmorItem(material, Type.BOOTS, settings) {

    companion object {
        const val MAX_FLUID_DEPTH = 0.1875 // 3 pixels
        const val MIN_DAMAGE_VELOCITY = 0.1
        const val DAMAGE_FREQUENCY = 80 // 4 seconds

        @JvmField
        val FLUID_SKI_COLLISION_SHAPE: VoxelShape = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);

        @JvmStatic
        fun wearsSkis(entity: Entity?) = if (entity is LivingEntity) wearsSkis(entity) else false
        @JvmStatic
        fun wearsSkis(entity: LivingEntity) = entity.getEquippedStack(EquipmentSlot.FEET).item is SkisItem

        @JvmStatic
        fun canSki(entity: LivingEntity, fluidState: FluidState) =
            wearsSkis(entity) &&
            fluidState isIn NetherArchivesTags.SKIS_CAN_WALK_ON
        @JvmStatic
        fun isSkiing(entity: LivingEntity) = (entity.isOnGround) && canSki(entity, entity.world.getFluidState(entity.blockPos))
    }
}
