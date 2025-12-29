package archives.tater.netherarchives.datagen

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.registry.NetherArchivesBlocks
import archives.tater.netherarchives.registry.NetherArchivesItems
import archives.tater.netherarchives.registry.NetherArchivesTags
import archives.tater.netherarchives.registry.NetherArchivesTriggers
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.advancements.criterion.*
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.level.storage.loot.predicates.LocationCheck
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

class AdvancementGenerator(output: FabricDataOutput, registryLookup: CompletableFuture<HolderLookup.Provider>) :
    FabricAdvancementProvider(output, registryLookup) {

    override fun generateAdvancement(
        registryLookup: HolderLookup.Provider,
        consumer: Consumer<AdvancementHolder>
    ) {
        val items = registryLookup.lookupOrThrow(Registries.ITEM)

        val nether = createRef(Identifier.withDefaultNamespace("nether/root"))
        val intoFire = createRef(Identifier.withDefaultNamespace("nether/obtain_blaze_rod"))
        val ohShiny = createRef(Identifier.withDefaultNamespace("nether/distract_piglin"))

        val ironSlag = consumer.advancement(IRON_SLAG, NetherArchivesItems.SMOLDERING_MAGNETITE) {
            parent(nether)
            addCriterion("iron_slag", playerPickedUpItemTrigger(ItemPredicate {
                of(items, NetherArchivesItems.IRON_SLAG)
            }))
        }

        val lightBlazeDust = consumer.advancement(LIGHT_BLAZE_DUST, NetherArchivesItems.BLAZE_DUST) {
            parent(intoFire)
            addCriterion("light_blaze_fire", CriteriaTriggers.ANY_BLOCK_USE.createCriterion(
                AnyBlockInteractionTrigger.TriggerInstance(Optional.empty(), Optional.of(ContextAwarePredicate.create(
                    LootItemBlockStatePropertyCondition.hasBlockStateProperties(NetherArchivesBlocks.BLAZE_FIRE).build()
                )))
            ))
        }

        val followBlazeTorch = consumer.advancement(FOLLOW_BLAZE_TORCH, NetherArchivesItems.BLAZE_TORCH) {
            parent(ohShiny)
            addCriterion("place_blaze_torch", CriteriaTriggers.PLACED_BLOCK.createCriterion(
                ItemUsedOnLocationTrigger.TriggerInstance(
                Optional.empty(),
                Optional.of(
                    ContextAwarePredicate.create(
                    LootItemBlockStatePropertyCondition.hasBlockStateProperties(NetherArchivesBlocks.BLAZE_TORCH).build(),
                    LocationCheck.checkLocation(locationPredicate {
                        setStructures(registryLookup.lookupOrThrow(Registries.STRUCTURE).getOrThrow(NetherArchivesTags.BLAZE_TORCH_LOCATED))
                    }).build()
                ))
            )))
        }

        val paddleSkis = consumer.advancement(PADDLE_SKIS, NetherArchivesItems.BASALT_SKIS) {
            parent(nether)
            addCriterion("paddle_skis", NetherArchivesTriggers.SKIS_PADDLE.createCriterion(
                ConsumeItemTrigger.TriggerInstance(
                Optional.of(EntityPredicate.wrap(EntityPredicate {
                    steppingOn(locationPredicate {
                        setFluid(fluidPredicate {
                            of(Fluids.LAVA)
                        })
                    })
                })),
                Optional.empty()
            )))
        }

        val boostSkis = consumer.advancement(BOOST_SKIS, NetherArchivesItems.BASALT_GEYSER) {
            parent(paddleSkis)
            addCriterion("boost_skis", NetherArchivesTriggers.AIRSKI.createCriterion(DefaultBlockInteractionTrigger.TriggerInstance(Optional.empty(), Optional.empty())))
        }

        val ferment = consumer.advancement(FERMENT, NetherArchivesItems.ROTTEN_FLESH_BLOCK) {
            parent(nether)
            addCriterion("ferment", NetherArchivesTriggers.FERMENT.createCriterion(DefaultBlockInteractionTrigger.TriggerInstance(
                Optional.empty(),
                Optional.of(ContextAwarePredicate.create(
                    LootItemBlockStatePropertyCondition.hasBlockStateProperties(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK).build()
                ))
            )))
        }
    }

    fun createRef(id: Identifier) = Advancement.Builder.advancement().build(id)

    companion object {
        val IRON_SLAG = NetherArchives.id("iron_slag")
        val LIGHT_BLAZE_DUST = NetherArchives.id("light_blaze_dust")
        val FOLLOW_BLAZE_TORCH = NetherArchives.id("follow_blaze_torch")
        val PADDLE_SKIS = NetherArchives.id("paddle_skis")
        val BOOST_SKIS = NetherArchives.id("boost_skis")
        val FERMENT = NetherArchives.id("ferment")
    }
}