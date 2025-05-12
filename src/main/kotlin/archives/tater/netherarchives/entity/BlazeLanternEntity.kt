package archives.tater.netherarchives.entity

import archives.tater.netherarchives.block.BlazePowderBlock
import archives.tater.netherarchives.block.NetherArchivesBlocks
import archives.tater.netherarchives.draw
import archives.tater.netherarchives.item.NetherArchivesItems
import archives.tater.netherarchives.listCopy
import net.minecraft.entity.EntityType
import net.minecraft.entity.FallingBlockEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.projectile.thrown.ThrownItemEntity
import net.minecraft.item.Item
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class BlazeLanternEntity : ThrownItemEntity {
    constructor(type: EntityType<BlazeLanternEntity>, world: World) : super(type, world)
    constructor(world: World, owner: LivingEntity) : super(NetherArchivesEntities.BLAZE_LANTERN, owner, world)

    override fun getDefaultItem(): Item = NetherArchivesItems.BLAZE_LANTERN

    override fun onCollision(hitResult: HitResult) {
        super.onCollision(hitResult)
        if (world.isClient) return
        val pos = hitResult.pos
        val blockPos = BlockPos(pos.x.toInt(), pos.y.toInt(), pos.z.toInt())

        world.playSound(null, blockPos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 0.5f, 1.0f)

        world.getOtherEntities(this, Box.of(blockPos.toCenterPos(), 1.5, 1.5, 1.5)).forEach {
            it.setOnFireForTicks(20 * 5)
        }

        BlockPos.iterateInSquare(blockPos, 1, Direction.NORTH, Direction.EAST).listCopy()
            .filter {
                val blockState = world.getBlockState(it)

                if (blockState.block is BlazePowderBlock) return@filter true

                // Must be either air or a replaceable, burnable block
                if (!blockState.isAir && !(blockState.isReplaceable && blockState.isBurnable)) return@filter false

                // Must be able to place blaze fire here or let it fall
                if (!world.getBlockState(it.down()).isAir && !NetherArchivesBlocks.BLAZE_FIRE.defaultState.canPlaceAt(world, it)) return@filter false

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
                if (world.getBlockState(it.down()).isAir) {
                    FallingBlockEntity.spawnFromBlock(world, it, NetherArchivesBlocks.BLAZE_FIRE.defaultState)
                } else {
                    world.setBlockState(it, NetherArchivesBlocks.BLAZE_FIRE.defaultState)
                }
            }
        discard()
    }
}
