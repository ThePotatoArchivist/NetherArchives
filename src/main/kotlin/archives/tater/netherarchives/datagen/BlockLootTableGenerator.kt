package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.block.NetherArchivesBlocks
import archives.tater.netherarchives.datagen.builder.*
import archives.tater.netherarchives.item.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.item.Items

class BlockLootTableGenerator(output: FabricDataOutput) : FabricBlockLootTableProvider(output) {
    override fun generate() {
        addDrop(NetherArchivesBlocks.MAGNETITE, drops(NetherArchivesItems.MAGNETITE))

        addDrop(NetherArchivesBlocks.SMOLDERING_MAGNETITE,
            LootTableBuilder {
                pool(1) {
                    item(NetherArchivesItems.IRON_SLAG) {
                        count(uniform(4, 12))
                        fortune
                    }
                    conditions { survivesExplosion }
                }
            }
        )

        addDrop(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK, drops(NetherArchivesItems.ROTTEN_FLESH_BLOCK))

        addDrop(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK, LootTableBuilder {
            pool(1) {
                alternatives {
                    item(NetherArchivesItems.FERMENTED_ROTTEN_FLESH_BLOCK) {
                        conditions {
                            tool {silkTouch}
                        }
                    }
                    item(Items.LEATHER) {
                        count(uniform(1, 3))
                        fortune
                    }
                }
                conditions { survivesExplosion }
            }
        })

        addDrop(NetherArchivesBlocks.BLAZE_DUST, drops(NetherArchivesItems.BLAZE_DUST))

        addDrop(NetherArchivesBlocks.BLAZE_TORCH, drops(NetherArchivesItems.BLAZE_TORCH))

    }
}
