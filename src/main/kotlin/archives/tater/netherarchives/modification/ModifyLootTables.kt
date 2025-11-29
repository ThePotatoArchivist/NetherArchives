package archives.tater.netherarchives.modification

import archives.tater.netherarchives.datagen.builder.item
import archives.tater.netherarchives.registry.NetherArchivesItems
import net.fabricmc.fabric.api.loot.v3.LootTableEvents
import net.minecraft.world.level.storage.loot.BuiltInLootTables

private val BARTERING = BuiltInLootTables.PIGLIN_BARTERING.location()

internal fun modifyLootTables() {
    LootTableEvents.MODIFY.register { key, tableBuilder, source, _ ->
        if (!(source.isBuiltin)) return@register

        when (key.location()) {
            BARTERING -> tableBuilder.modifyPools {
                it.item(NetherArchivesItems.BLAZE_TORCH) {
                    setWeight(20)
                }
            }
        }
    }
}
