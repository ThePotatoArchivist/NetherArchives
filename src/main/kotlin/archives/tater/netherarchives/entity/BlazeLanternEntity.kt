package archives.tater.netherarchives.entity

import archives.tater.netherarchives.block.BlazePowderBlock
import archives.tater.netherarchives.registry.NetherArchivesBlocks
import archives.tater.netherarchives.registry.NetherArchivesEntities
import archives.tater.netherarchives.registry.NetherArchivesItems
import archives.tater.netherarchives.util.draw
import archives.tater.netherarchives.util.listCopy
import archives.tater.netherarchives.util.world
import net.fabricmc.fabric.api.networking.v1.PlayerLookup.world
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.item.FallingBlockEntity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.sounds.SoundSource
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.phys.HitResult
import net.minecraft.core.BlockPos
import net.minecraft.world.phys.AABB
import net.minecraft.core.Direction
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile
import net.minecraft.world.level.Level

class BlazeLanternEntity : ThrowableItemProjectile {
    constructor(type: EntityType<BlazeLanternEntity>, world: Level) : super(type, world)
    constructor(world: Level, owner: LivingEntity, stack: ItemStack) : super(NetherArchivesEntities.BLAZE_LANTERN, owner, world, stack)

    override fun getDefaultItem(): Item = NetherArchivesItems.BLAZE_LANTERN

    override fun onHit(hitResult: HitResult) {
        super.onHit(hitResult)
        if (world.isClientSide) return
        val pos = hitResult.location
        val blockPos = BlockPos(pos.x.toInt(), pos.y.toInt(), pos.z.toInt())

        world.playSound(null, blockPos, SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, 0.5f, 1.0f)

        world.getEntities(this, AABB.ofSize(blockPos.center, 1.5, 1.5, 1.5)).forEach {
            it.igniteForTicks(20 * 5)
        }

        BlockPos.spiralAround(blockPos, 1, Direction.NORTH, Direction.EAST).listCopy()
            .filter {
                val blockState = world.getBlockState(it)

                if (blockState.block is BlazePowderBlock) return@filter true

                // Must be either air or a replaceable, burnable block
                if (!blockState.isAir && !(blockState.canBeReplaced() && blockState.ignitedByLava())) return@filter false

                // Must be able to place blaze fire here or let it fall
                if (!world.getBlockState(it.below()).isAir && !NetherArchivesBlocks.BLAZE_FIRE.defaultBlockState()
                        .canSurvive(world, it)) return@filter false

                true
            }
            .let {
                val centerFlammable: Boolean

                val returnedList = it.toMutableList().apply {
                    centerFlammable = remove(blockPos)
                }.draw(world.random, 4)

                if (centerFlammable) returnedList + listOf(blockPos) else returnedList
            }
            .forEach {
                if (world.getBlockState(it.below()).isAir) {
                    FallingBlockEntity.fall(world, it, NetherArchivesBlocks.BLAZE_FIRE.defaultBlockState())
                } else {
                    world.setBlockAndUpdate(it, NetherArchivesBlocks.BLAZE_FIRE.defaultBlockState())
                }
            }
        discard()
    }
}
