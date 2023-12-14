package archives.tater.netherarchives.modification

import archives.tater.netherarchives.datagen.builder.item
import archives.tater.netherarchives.item.NetherArchivesItems
import net.fabricmc.fabric.api.loot.v2.LootTableEvents
import net.minecraft.util.Identifier

object ModifyLootTables {
    private val BARTER_LOOT_TABLE = Identifier("gameplay/piglin_bartering")

    operator fun invoke() {
        LootTableEvents.MODIFY.register { _, _, id, tableBuilder, source ->
            if (!(source.isBuiltin && id == BARTER_LOOT_TABLE)) return@register

            tableBuilder.modifyPools {
                it.item(NetherArchivesItems.BLAZE_TORCH) {
                    weight(20)
                }
            }
        }
    }
}
