package archives.tater.netherarchives.item

import archives.tater.netherarchives.entity.BlazeLanternEntity
import archives.tater.netherarchives.util.get
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.world.World

class BlazeLanternItem(settings: Settings) : Item(settings) {
    // TODO this crashes
//    init {
//        DispenserBlock.registerBehavior(NetherArchivesItems.BLAZE_LANTERN, object: ProjectileDispenserBehavior() {
//            override fun createProjectile(world: World, position: Position, stack: ItemStack): ProjectileEntity {
//                return (Util.make(
//                    BlazeLanternEntity(world, position.x, position.y, position.z)
//                ) { entity: BlazeLanternEntity -> entity.setItem(stack) } as ProjectileEntity)
//            }
//        })
//    }

    override fun use(world: World, user: PlayerEntity, hand: Hand): ActionResult {
        val itemStack = user[hand]
        world.playSound(
            null as PlayerEntity?,
            user.x,
            user.y,
            user.z,
            SoundEvents.ENTITY_EGG_THROW,
            SoundCategory.NEUTRAL,
            0.5f,
            0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f)
        )
        if (!world.isClient) {
            val blazeLanternEntity = BlazeLanternEntity(world, user, itemStack)
            blazeLanternEntity.setVelocity(user, user.pitch, user.yaw, 0.2f, 1f, 1.0f)
            world.spawnEntity(blazeLanternEntity)
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this))
        if (!user.abilities.creativeMode) {
            itemStack.decrement(1)
            user.itemCooldownManager.set(itemStack, 40)
        }
        return ActionResult.SUCCESS.withNewHandStack(itemStack)
    }
}
