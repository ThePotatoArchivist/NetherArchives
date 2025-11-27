package archives.tater.netherarchives

import archives.tater.netherarchives.mixin.StriderEntityAccessor
import archives.tater.netherarchives.modification.modifyLootTables
import archives.tater.netherarchives.modification.modifyWorldGen
import archives.tater.netherarchives.registry.*
import archives.tater.netherarchives.util.get
import archives.tater.netherarchives.util.isIn
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.player.UseEntityCallback
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.monster.Strider
import net.minecraft.world.item.Items
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object NetherArchives : ModInitializer {
    const val MOD_ID = "netherarchives"

    @JvmStatic
    fun id(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID, path)

    @JvmField
    val logger: Logger = LoggerFactory.getLogger(MOD_ID)

    val config = NetherArchivesConfig()

    override fun onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        NetherArchivesBlocks.register()
        NetherArchivesBlockEntities.register()
        NetherArchivesItems.register()
        NetherArchivesEntities.register()
        NetherArchivesDamageTypes.register()
        NetherArchivesParticles.register()
        modifyWorldGen()
        modifyLootTables()

        UseEntityCallback.EVENT.register { player, world, hand, entity, _ ->
            if (entity !is Strider || !entity.isSaddled || entity.isVehicle || !(player[hand] isIn ConventionalItemTags.SHEAR_TOOLS))
                InteractionResult.PASS
            else if (world.isClientSide)
                InteractionResult.SUCCESS
            else {
                (entity as StriderEntityAccessor).steering.setSaddle(false)
                entity.spawnAtLocation(Items.SADDLE)
                world.playSound(null, entity, SoundEvents.SHEEP_SHEAR, player.soundSource, 1f, 1f) // TODO custom sound
                player[hand].hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand))
                InteractionResult.SUCCESS
            }
        }
    }

}
