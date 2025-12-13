package archives.tater.netherarchives

import archives.tater.netherarchives.modification.modifyLootTables
import archives.tater.netherarchives.modification.modifyWorldGen
import archives.tater.netherarchives.registry.*
import archives.tater.netherarchives.registry.NetherArchivesItems
import net.fabricmc.api.ModInitializer
import net.minecraft.resources.Identifier
import net.fabricmc.fabric.api.event.player.UseEntityCallback
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags
import net.fabricmc.loader.api.FabricLoader
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
    fun id(path: String): Identifier = Identifier.fromNamespaceAndPath(MOD_ID, path)

    @JvmField
    val logger: Logger = LoggerFactory.getLogger(MOD_ID)

    val EXPOSURE_INSTALLED = FabricLoader.getInstance().isModLoaded("exposure")

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
    }

}
