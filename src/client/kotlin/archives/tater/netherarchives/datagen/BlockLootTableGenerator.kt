package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.datagen.builder.*
import archives.tater.netherarchives.registry.NetherArchivesBlocks
import archives.tater.netherarchives.registry.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.world.level.block.Block
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.core.registries.Registries
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

class BlockLootTableGenerator(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) :
    FabricBlockLootTableProvider(output, registriesFuture) {

    private infix fun Block.drops(lootTable: LootTable.Builder) {
        add(this, lootTable)
    }

    private infix fun Block.drops(item: Item) {
        this drops this@BlockLootTableGenerator.createSingleItemTable(item)
    }

    private infix fun Block.drops(lootTableInit: LootTable.Builder.() -> Unit) {
        this drops lootTable(lootTableInit)
    }

    override fun generate() {
        val fortune = registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE)

        dropSelf(NetherArchivesBlocks.MAGNETITE)

        NetherArchivesBlocks.SMOLDERING_MAGNETITE drops {
            pool {
                item(NetherArchivesItems.IRON_SLAG) {
                    count(uniform(4, 12))
                    oreDrops(fortune)
                }
                conditions { survivesExplosion() }
            }
        }

        dropSelf(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK)

        NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK drops {
            pool {
                alternatives {
                    item(NetherArchivesItems.FERMENTED_ROTTEN_FLESH_BLOCK) {
                        `when`(hasSilkTouch())
                    }
                    item(Items.LEATHER) {
                        count(uniform(1, 3))
                        oreDrops(fortune)
                    }
                }
                conditions { survivesExplosion() }
            }
        }

        dropSelf(NetherArchivesBlocks.BLAZE_DUST)

        dropSelf(NetherArchivesBlocks.BLAZE_TORCH)

        NetherArchivesBlocks.BASALT_GEYSER drops {
            pool {
                alternatives {
                    item(NetherArchivesItems.BASALT_GEYSER) {
                        `when`(hasSilkTouch())
                    }
                    item(Items.BASALT)
                }
                conditions {
                    survivesExplosion()
                }
            }
        }

        dropSelf(NetherArchivesBlocks.ADJUSTABLE_BASALT_GEYSER)

        NetherArchivesBlocks.SPECTREGLASS drops createSingleItemTableWithSilkTouch(NetherArchivesBlocks.SPECTREGLASS, NetherArchivesBlocks.SHATTERED_SPECTREGLASS)

        dropSelf(NetherArchivesBlocks.SHATTERED_SPECTREGLASS)
    }
}
