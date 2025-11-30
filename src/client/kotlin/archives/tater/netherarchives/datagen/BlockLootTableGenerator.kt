package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.datagen.builder.*
import archives.tater.netherarchives.registry.NetherArchivesBlocks
import archives.tater.netherarchives.registry.NetherArchivesItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.world.item.Item
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Items
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.storage.loot.LootTable
import java.util.concurrent.CompletableFuture

class BlockLootTableGenerator(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) :
    FabricBlockLootTableProvider(output, registriesFuture) {

    private fun add(block: Block, lootTableInit: LootTable.Builder.() -> Unit) {
        add(block, lootTable(lootTableInit))
    }

    override fun generate() {
        val fortune = registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE)

        dropSelf(NetherArchivesBlocks.MAGNETITE)

        add(NetherArchivesBlocks.SMOLDERING_MAGNETITE) {
            pool {
                item(NetherArchivesItems.IRON_SLAG) {
                    oreDrops(fortune)
                }
                conditions { survivesExplosion() }
            }
            pool {
                item(Items.IRON_NUGGET) {
                    count(uniform(1, 3))
                    setWeight(1)
                }
                empty {
                    setWeight(3)
                }
                conditions { survivesExplosion() }
            }
        }

        dropSelf(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK)

        add(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK) {
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

        add(NetherArchivesBlocks.BASALT_GEYSER) {
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

        add(NetherArchivesBlocks.SPECTREGLASS, createSingleItemTableWithSilkTouch(NetherArchivesBlocks.SPECTREGLASS, NetherArchivesBlocks.SHATTERED_SPECTREGLASS))

        dropSelf(NetherArchivesBlocks.SHATTERED_SPECTREGLASS)
    }
}
