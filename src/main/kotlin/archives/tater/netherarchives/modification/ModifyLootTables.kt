package archives.tater.netherarchives.modification

import archives.tater.netherarchives.datagen.builder.item
import archives.tater.netherarchives.item.NetherArchivesItems
import net.fabricmc.fabric.api.loot.v3.LootTableEvents
import net.minecraft.loot.LootTables

object ModifyLootTables {
    operator fun invoke() {
        LootTableEvents.MODIFY.register { key, tableBuilder, source, _ ->
            if (!(source.isBuiltin && key.value == LootTables.PIGLIN_BARTERING_GAMEPLAY.value)) return@register

            tableBuilder.modifyPools {
                it.item(NetherArchivesItems.BLAZE_TORCH) {
                    weight(20)
                }
            }
        }
    }
}
