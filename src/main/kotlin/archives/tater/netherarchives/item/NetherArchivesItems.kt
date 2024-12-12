package archives.tater.netherarchives.item

import archives.tater.netherarchives.FabricItemSettings
import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.block.NetherArchivesBlocks
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.block.Block
import net.minecraft.item.*
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction


object NetherArchivesItems {
    private fun register(identifier: Identifier, item: Item = Item(FabricItemSettings())): Item =
        Registry.register(Registries.ITEM, identifier, item)

    private fun register(path: String, item: Item = Item(FabricItemSettings())): Item =
        register(Identifier(NetherArchives.MOD_ID, path), item)

    private fun register(block: Block, settings: FabricItemSettings = FabricItemSettings()): Item =
        register(Registries.BLOCK.getId(block).path, BlockItem(block, settings))

    val MAGNETITE = register(NetherArchivesBlocks.MAGNETITE)

    val SMOLDERING_MAGNETITE = register(NetherArchivesBlocks.SMOLDERING_MAGNETITE)

    val ROTTEN_FLESH_BLOCK = register(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK)

    val FERMENTED_ROTTEN_FLESH_BLOCK = register(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK)

    val IRON_SLAG = register("iron_slag")

    val BLAZE_DUST = register(NetherArchivesBlocks.BLAZE_DUST)

    val BLAZE_LANTERN = register("blaze_lantern", BlazeLanternItem(FabricItemSettings {
        maxCount(16)
    }))

    val BLAZE_TORCH = register(
        "blaze_torch",
        VerticallyAttachableBlockItem(
            NetherArchivesBlocks.BLAZE_TORCH,
            NetherArchivesBlocks.WALL_BLAZE_TORCH,
            FabricItemSettings(),
            Direction.DOWN
        )
    )

    val BASALT_SKIS = register("basalt_skis", SkisItem(SkisItem.BASALT_ARMOR_MATERIAL, FabricItemSettings()))
    val BASALT_OAR = register("basalt_oar", OarItem(FabricItemSettings {
        maxCount(1)
        maxDamage(ToolMaterials.STONE.durability)
    }))
    val BASALT_ROD = register("basalt_rod")

    val BASALT_GEYSER = register(NetherArchivesBlocks.BASALT_GEYSER)

    // Registered under minecraft namespace so that in the tooltip it is labeled as coming from minecraft
    val DUMMY_SOUL_FIRE = register(Identifier("netherarchives/dummy/soul_fire"))

    private val itemGroups = mapOf(
        ItemGroups.INGREDIENTS to setOf(IRON_SLAG, BASALT_ROD),
        ItemGroups.NATURAL to setOf(MAGNETITE, SMOLDERING_MAGNETITE, ROTTEN_FLESH_BLOCK, FERMENTED_ROTTEN_FLESH_BLOCK, BASALT_GEYSER),
        ItemGroups.COMBAT to setOf(BLAZE_DUST, BLAZE_LANTERN),
        ItemGroups.TOOLS to setOf(BASALT_SKIS, BASALT_OAR),
        ItemGroups.FUNCTIONAL to setOf(BLAZE_TORCH),
    )

    fun registerItemGroups() {
        itemGroups.forEach { (group, items) ->
            ItemGroupEvents.modifyEntriesEvent(group).register {
                items.forEach(it::add)
            }
        }
    }
}
