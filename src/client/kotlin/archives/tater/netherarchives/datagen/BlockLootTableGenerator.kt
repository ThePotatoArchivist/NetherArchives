package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.datagen.builder.*
import archives.tater.netherarchives.registry.ModBlocks
import archives.tater.netherarchives.registry.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider
import net.minecraft.core.HolderLookup
import net.minecraft.world.item.Items
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.NestedLootTable.inlineLootTable
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders.between
import java.util.concurrent.CompletableFuture

class BlockLootTableGenerator(output: FabricPackOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) :
    FabricBlockLootSubProvider(output, registriesFuture) {

    private fun add(block: Block, lootTableInit: LootTable.Builder.() -> Unit) {
        add(block, lootTable(lootTableInit))
    }

    override fun generate() {
        val fortune = enchantments.getOrThrow(Enchantments.FORTUNE)

        dropSelf(ModBlocks.MAGNETITE)

        add(ModBlocks.SMOLDERING_MAGNETITE, createSilkTouchDispatchTable(
            ModBlocks.SMOLDERING_MAGNETITE,
            inlineLootTable(lootTable {
                pool {
                    item(ModItems.IRON_SLAG) {
                        oreDrops(fortune)
                    }
                    conditions { survivesExplosion() }
                }
                pool {
                    item(Items.IRON_NUGGET) {
                        count(between(1, 3))
                        setWeight(1)
                    }
                    empty {
                        setWeight(3)
                    }
                    conditions { survivesExplosion() }
                }
            }.build())
        ))

        dropSelf(ModBlocks.ROTTEN_FLESH_BLOCK)

        add(ModBlocks.FERMENTED_ROTTEN_FLESH_BLOCK) {
            pool {
                alternatives {
                    item(ModItems.FERMENTED_ROTTEN_FLESH_BLOCK) {
                        `when`(hasSilkTouch())
                    }
                    item(Items.LEATHER) {
                        count(between(1, 3))
                        oreDrops(fortune)
                    }
                }
                conditions { survivesExplosion() }
            }
        }

        dropSelf(ModBlocks.BLAZE_DUST)

        dropSelf(ModBlocks.BLAZE_TORCH)

        add(ModBlocks.BASALT_GEYSER) {
            pool {
                alternatives {
                    item(ModItems.BASALT_GEYSER) {
                        `when`(hasSilkTouch())
                    }
                    item(Items.BASALT)
                }
                conditions {
                    survivesExplosion()
                }
            }
        }

        dropSelf(ModBlocks.ADJUSTABLE_BASALT_GEYSER)

        add(ModBlocks.SPECTREGLASS, createSingleItemTableWithSilkTouch(ModBlocks.SPECTREGLASS, ModBlocks.SHATTERED_SPECTREGLASS))

        dropSelf(ModBlocks.SHATTERED_SPECTREGLASS)
    }
}
