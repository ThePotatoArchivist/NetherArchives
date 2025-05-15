package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.datagen.builder.*
import archives.tater.netherarchives.registry.NetherArchivesBlocks
import archives.tater.netherarchives.registry.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.loot.LootTable
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class BlockLootTableGenerator(output: FabricDataOutput, registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>) :
    FabricBlockLootTableProvider(output, registriesFuture) {

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
        val fortune = registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE)

        addDrop(NetherArchivesBlocks.MAGNETITE)

        NetherArchivesBlocks.SMOLDERING_MAGNETITE drops {
            pool {
                item(NetherArchivesItems.IRON_SLAG) {
                    count(uniform(4, 12))
                    oreDrops(fortune)
                }
                conditions { survivesExplosion() }
            }
        }

        addDrop(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK)

        NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK drops {
            pool {
                alternatives {
                    item(NetherArchivesItems.FERMENTED_ROTTEN_FLESH_BLOCK) {
                        conditionally(createSilkTouchCondition())
                    }
                    item(Items.LEATHER) {
                        count(uniform(1, 3))
                        oreDrops(fortune)
                    }
                }
                conditions { survivesExplosion() }
            }
        }

        addDrop(NetherArchivesBlocks.BLAZE_DUST)

        addDrop(NetherArchivesBlocks.BLAZE_TORCH)

        NetherArchivesBlocks.BASALT_GEYSER drops {
            pool {
                alternatives {
                    item(NetherArchivesItems.BASALT_GEYSER) {
                        conditionally(createSilkTouchCondition())
                    }
                    item(Items.BASALT)
                }
                conditions {
                    survivesExplosion()
                }
            }
        }

        addDrop(NetherArchivesBlocks.POLISHED_BASALT_GEYSER)

        NetherArchivesBlocks.SPECTREGLASS drops drops(NetherArchivesBlocks.SPECTREGLASS, NetherArchivesBlocks.SHATTERED_SPECTREGLASS)

        addDrop(NetherArchivesBlocks.SHATTERED_SPECTREGLASS)
    }
}
