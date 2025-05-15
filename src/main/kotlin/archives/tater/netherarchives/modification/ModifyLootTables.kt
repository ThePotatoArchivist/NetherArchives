package archives.tater.netherarchives.modification

import archives.tater.netherarchives.datagen.builder.item
import archives.tater.netherarchives.registry.NetherArchivesItems
import net.fabricmc.fabric.api.loot.v3.LootTableEvents
import net.minecraft.loot.LootTables

private val BARTERING = LootTables.PIGLIN_BARTERING_GAMEPLAY.value

internal fun modifyLootTables() {
    LootTableEvents.MODIFY.register { key, tableBuilder, source, _ ->
        if (!(source.isBuiltin)) return@register

        when (key.value) {
            BARTERING -> tableBuilder.modifyPools {
                it.item(NetherArchivesItems.BLAZE_TORCH) {
                    weight(20)
                }
            }
        }
    }
}
