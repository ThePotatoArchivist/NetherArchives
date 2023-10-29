package archives.tater.netherarchives

import archives.tater.netherarchives.block.NetherArchivesBlocks
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object NetherArchives : ModInitializer {
    const val NAMESPACE = "netherarchives"

    private val logger = LoggerFactory.getLogger(NAMESPACE)

    override fun onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        logger.info("Hello Fabric world!")
        NetherArchivesBlocks.registerItems()
    }

}
