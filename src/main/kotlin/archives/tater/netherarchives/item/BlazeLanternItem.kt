package archives.tater.netherarchives.item

import archives.tater.netherarchives.entity.BlazeLanternEntity
import archives.tater.netherarchives.util.get
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class BlazeLanternItem(settings: Properties) : Item(settings) {
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

    override fun use(world: Level, user: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val itemStack = user[hand]
        world.playSound(
            null as Player?,
            user.x,
            user.y,
            user.z,
            SoundEvents.EGG_THROW,
            SoundSource.NEUTRAL,
            0.5f,
            0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f)
        )
        if (!world.isClientSide) {
            val blazeLanternEntity = BlazeLanternEntity(world, user)
            blazeLanternEntity.item = itemStack
            blazeLanternEntity.shootFromRotation(user, user.xRot, user.yRot, 0.2f, 1f, 1.0f)
            world.addFreshEntity(blazeLanternEntity)
        }
        user.awardStat(Stats.ITEM_USED.get(this))
        if (!user.abilities.instabuild) {
            itemStack.shrink(1)
            user.cooldowns.addCooldown(this, 40)
        }
        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide)
    }
}
