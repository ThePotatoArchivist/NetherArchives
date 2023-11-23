package archives.tater.netherarchives.item

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.block.NetherArchivesBlocks
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier


object NetherArchivesItems {

    val MAGNETITE: BlockItem = Registry.register(
        Registries.ITEM,
        Identifier(NetherArchives.NAMESPACE, "magnetite"),
        BlockItem(NetherArchivesBlocks.MAGNETITE, FabricItemSettings())
    )

    val SMOLDERING_MAGNETITE: BlockItem = Registry.register(
        Registries.ITEM,
        Identifier(NetherArchives.NAMESPACE, "smoldering_magnetite"),
        BlockItem(NetherArchivesBlocks.SMOLDERING_MAGNETITE, FabricItemSettings())
    )

    val ROTTEN_FLESH_BLOCK: BlockItem = Registry.register(
        Registries.ITEM,
        Identifier(NetherArchives.NAMESPACE, "rotten_flesh_block"),
        BlockItem(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK, FabricItemSettings())
    )

    val FERMENTED_ROTTEN_FLESH_BLOCK: BlockItem = Registry.register(
        Registries.ITEM,
        Identifier(NetherArchives.NAMESPACE, "fermented_rotten_flesh_block"),
        BlockItem(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK, FabricItemSettings())
    )

    val IRON_SLAG: Item = Registry.register(
        Registries.ITEM,
        Identifier(NetherArchives.NAMESPACE, "iron_slag"),
        Item(FabricItemSettings())
    )

    val BLAZE_DUST: Item = Registry.register(
        Registries.ITEM,
        Identifier(NetherArchives.NAMESPACE, "blaze_dust"),
        BlockItem(NetherArchivesBlocks.BLAZE_DUST, FabricItemSettings())
    )

    val BLAZE_LANTERN: Item = Registry.register(
        Registries.ITEM,
        Identifier(NetherArchives.NAMESPACE, "blaze_lantern"),
        BlazeLanternItem(FabricItemSettings().maxCount(16))
    )

    private val itemGroups = mapOf(
        ItemGroups.INGREDIENTS to setOf(IRON_SLAG),
        ItemGroups.NATURAL to setOf(MAGNETITE, SMOLDERING_MAGNETITE, ROTTEN_FLESH_BLOCK, FERMENTED_ROTTEN_FLESH_BLOCK),
        ItemGroups.COMBAT to setOf(BLAZE_DUST, BLAZE_LANTERN)
    )

    fun registerItemGroups() {
        itemGroups.forEach { (group, items) ->
            ItemGroupEvents.modifyEntriesEvent(group).register {
                items.forEach(it::add)
            }
        }
    }
}
