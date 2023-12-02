package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.block.NetherArchivesBlocks
import archives.tater.netherarchives.datagen.builder.*
import archives.tater.netherarchives.item.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.loot.LootTable

class BlockLootTableGenerator(output: FabricDataOutput) : FabricBlockLootTableProvider(output) {
    private infix fun Block.drops(lootTable: LootTable.Builder) {
        addDrop(this, lootTable)
    }

    private infix fun Block.drops(item: Item) {
        this drops this@BlockLootTableGenerator.drops(item)
    }

    private infix fun Block.drops(lootTableInit: LootTable.Builder.() -> Unit) {
        this drops lootTable(lootTableInit)
    }

    override fun generate() {
        NetherArchivesBlocks.MAGNETITE drops NetherArchivesItems.MAGNETITE

        NetherArchivesBlocks.SMOLDERING_MAGNETITE drops {
            pool {
                item(NetherArchivesItems.IRON_SLAG) {
                    count(uniform(4, 12))
                    fortune
                }
                conditions { survivesExplosion }
            }
        }

        NetherArchivesBlocks.ROTTEN_FLESH_BLOCK drops NetherArchivesItems.ROTTEN_FLESH_BLOCK

        NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK drops {
            pool {
                alternatives {
                    item(NetherArchivesItems.FERMENTED_ROTTEN_FLESH_BLOCK) {
                        conditions {
                            tool { silkTouch }
                        }
                    }
                    item(Items.LEATHER) {
                        count(uniform(1, 3))
                        fortune
                    }
                }
                conditions { survivesExplosion }
            }
        }

        NetherArchivesBlocks.BLAZE_DUST drops NetherArchivesItems.BLAZE_DUST

        NetherArchivesBlocks.BLAZE_TORCH drops NetherArchivesItems.BLAZE_TORCH
    }
}
