package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.block.NetherArchivesBlocks
import archives.tater.netherarchives.datagen.builder.lootTableBuilder
import archives.tater.netherarchives.datagen.builder.not
import archives.tater.netherarchives.datagen.builder.uniform
import archives.tater.netherarchives.item.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.item.Items

class BlockLootTableGenerator(output: FabricDataOutput) : FabricBlockLootTableProvider(output) {
    override fun generate() {
        addDrop(NetherArchivesBlocks.MAGNETITE, drops(NetherArchivesItems.MAGNETITE))

        addDrop(NetherArchivesBlocks.SMOLDERING_MAGNETITE,
            lootTableBuilder {
                pool(1) {
                    entry(NetherArchivesItems.IRON_SLAG) {
                        count(uniform(4, 12))
                    }
                    condition { survivesExplosion }
                }
            }
        );

        addDrop(NetherArchivesBlocks.ROTTEN_FLESH, drops(NetherArchivesItems.ROTTEN_FLESH))

        addDrop(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH, lootTableBuilder {
            pool(1) {
                entry(Items.LEATHER) {
                    count(uniform(1, 3))
                    condition {
                        !tool { silkTouch }
                    }
                }
                entry(NetherArchivesItems.FERMENTED_ROTTEN_FLESH) {
                    condition {
                        tool { silkTouch }
                    }
                }
                condition { survivesExplosion }
            }
        })

        addDrop(NetherArchivesBlocks.BLAZE_DUST, drops(Items.BLAZE_POWDER))

    }
}
