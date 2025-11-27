package archives.tater.netherarchives.item

import archives.tater.netherarchives.registry.NetherArchivesTags
import archives.tater.netherarchives.util.isIn
import net.minecraft.world.level.block.Block
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.core.Holder
import net.minecraft.world.phys.shapes.VoxelShape

class SkisItem(material: Holder<ArmorMaterial>, settings: Properties) : ArmorItem(material, Type.BOOTS, settings) {

    companion object {
        const val MAX_FLUID_DEPTH = 0.1875 // 3 pixels
        const val MIN_DAMAGE_VELOCITY = 0.1
        const val DAMAGE_FREQUENCY = 80 // 4 seconds

        @JvmField
        val FLUID_SKI_COLLISION_SHAPE: VoxelShape = Block.box(0.0, 0.0, 0.0, 16.0, 15.0, 16.0)

        @JvmField
        val FLUID_SKI_HEIGHT_COLLISION_SHAPE: VoxelShape = Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0) // box entity must be above to not sink

        @JvmStatic
        fun wearsSkis(entity: Entity?) = if (entity is LivingEntity) wearsSkis(entity) else false
        @JvmStatic
        fun wearsSkis(entity: LivingEntity) = entity.getItemBySlot(EquipmentSlot.FEET).item is SkisItem

        @JvmStatic
        fun canSki(entity: LivingEntity, fluidState: FluidState) =
            wearsSkis(entity) &&
            fluidState isIn NetherArchivesTags.SKIS_CAN_WALK_ON
        @JvmStatic
        fun isSkiing(entity: LivingEntity) = (entity.onGround()) && canSki(entity, entity.level()
            .getFluidState(entity.blockPosition()))
    }
}
