package archives.tater.netherarchives.block

import archives.tater.netherarchives.NetherArchives
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier

object NetherArchivesBlocks {
    val MAGNETITE: Block = Registry.register(
        Registries.BLOCK, Identifier(NetherArchives.NAMESPACE, "magnetite"), MagnetiteBlock(
            FabricBlockSettings.create()
                .strength(0.8f, 9.0f)
                .sounds(BlockSoundGroup.BASALT)
        )
    )

    val MAGNETITE_ITEM: BlockItem = Registry.register(
        Registries.ITEM,
        Identifier(NetherArchives.NAMESPACE, "magnetite"),
        BlockItem(MAGNETITE, FabricItemSettings())
    )

    val SMOLDERING_MAGNETITE: Block = Registry.register(
        Registries.BLOCK, Identifier(NetherArchives.NAMESPACE, "smoldering_magnetite"), SmolderingMagnetiteBlock(
            FabricBlockSettings.create()
                .strength(0.6f, 1.25f)
                .sounds(BlockSoundGroup.BASALT)
                .luminance { 3 }
                .emissiveLighting(Blocks::always)
        )
    )

    val SMOLDERING_MAGNETITE_ITEM: BlockItem = Registry.register(
        Registries.ITEM,
        Identifier(NetherArchives.NAMESPACE, "smoldering_magnetite"),
        BlockItem(SMOLDERING_MAGNETITE, FabricItemSettings())
    )

    fun registerItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register { content ->
            content.add(MAGNETITE_ITEM)
            content.add(SMOLDERING_MAGNETITE_ITEM)
        }
    }

}
