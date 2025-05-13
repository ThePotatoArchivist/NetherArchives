package archives.tater.netherarchives

import archives.tater.netherarchives.block.NetherArchivesBlocks
import archives.tater.netherarchives.block.entity.NetherArchivesBlockEntities
import archives.tater.netherarchives.entity.NetherArchivesEntities
import archives.tater.netherarchives.item.NetherArchivesItems
import archives.tater.netherarchives.mixin.StriderEntityAccessor
import archives.tater.netherarchives.modification.ModifyLootTables
import archives.tater.netherarchives.modification.ModifyWorldGen
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.player.UseEntityCallback
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.passive.StriderEntity
import net.minecraft.item.Items
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object NetherArchives : ModInitializer {
    const val MOD_ID = "netherarchives"

    @JvmStatic
    fun id(path: String): Identifier = Identifier.of(MOD_ID, path)

    @JvmField
    val logger: Logger = LoggerFactory.getLogger(MOD_ID)

    val config = NetherArchivesConfig()

    override fun onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        NetherArchivesBlocks.register()
        NetherArchivesBlockEntities.register()
        NetherArchivesItems.registerItemGroups()
        NetherArchivesEntities.register()
        NetherArchivesDamageTypes.register()
        NetherArchivesParticles.register()
        ModifyWorldGen()
        ModifyLootTables()

        UseEntityCallback.EVENT.register { player, world, hand, entity, hit ->
            if (entity !is StriderEntity || !entity.isSaddled || !(player.getStackInHand(hand) isIn ConventionalItemTags.SHEAR_TOOLS))
                ActionResult.PASS
            else if (world.isClient)
                ActionResult.SUCCESS
            else {
                (entity as StriderEntityAccessor).saddledComponent.isSaddled = false
                entity.dropItem(Items.SADDLE)
                world.playSoundFromEntity(null, entity, SoundEvents.ENTITY_SHEEP_SHEAR, player.soundCategory, 1f, 1f) // TODO custom sound
                player.getStackInHand(hand).damage(1, player, LivingEntity.getSlotForHand(hand))
                ActionResult.SUCCESS
            }
        }
    }

}
