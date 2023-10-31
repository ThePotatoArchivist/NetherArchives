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

    val IRON_SLAG: Item = Registry.register(Registries.ITEM, Identifier(NetherArchives.NAMESPACE, "iron_slag"), Item(FabricItemSettings()))

    fun registerItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register { content ->
            content.add(IRON_SLAG)
            content.add(MAGNETITE)
            content.add(SMOLDERING_MAGNETITE)
        }
    }
}
