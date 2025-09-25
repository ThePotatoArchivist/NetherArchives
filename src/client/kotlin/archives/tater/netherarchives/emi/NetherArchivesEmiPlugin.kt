package archives.tater.netherarchives.emi

//object NetherArchivesEmiPlugin : EmiPlugin {
//    override fun register(registry: EmiRegistry) {
//        registry.apply {
//            addWorldRecipe("unique/fermented_rotten_flesh") {
//                leftInput(EmiStack.of(NetherArchivesItems.ROTTEN_FLESH_BLOCK))
//                rightInput(EmiIngredient.of(Registries.BLOCK
//                    .filter { it.defaultState.isIn(NetherArchivesTags.ROTTEN_FLESH_FERMENTER) }
//                    .map { if (it == Blocks.SOUL_FIRE) NetherArchivesItems.DUMMY_SOUL_FIRE else it }
//                    .map(EmiStack::of)), true)
//                output(EmiStack.of(NetherArchivesItems.FERMENTED_ROTTEN_FLESH_BLOCK))
//            }
//
//            addWorldRecipe("fluid_interaction/smoldering_magnetite") {
//                leftInput(EmiStack.of(NetherArchivesItems.MAGNETITE))
//                rightInput(EmiStack.of(Fluids.LAVA), true)
//                output(EmiStack.of(NetherArchivesItems.SMOLDERING_MAGNETITE))
//            }
//
//            addWorldRecipe("unique/nether_star") {
//                leftInput(EmiStack.of(Items.BEACON))
//                rightInput(EmiIngredient.of(listOf(
//                    Items.TNT,
//                    Items.CREEPER_HEAD,
//                    Items.END_CRYSTAL,
//                    Items.WITHER_SKELETON_SKULL,
//                    Items.RED_BED,
//                    Items.RESPAWN_ANCHOR,
//                ).map {
//                    EmiStack.of(ItemStack(it).apply {
//                        this[DataComponentTypes.ITEM_NAME] = Text.translatable("netherarchives.emi.explosion")
//                        this[DataComponentTypes.RARITY] = Rarity.COMMON
//                    })
//                }), true)
//                output(EmiStack.of(Items.NETHER_STAR))
//            }
//
//            for ((normal, shattered) in listOf(
//                NetherArchivesItems.SPECTREGLASS to NetherArchivesItems.SHATTERED_SPECTREGLASS,
//                NetherArchivesItems.SPECTREGLASS_PANE to NetherArchivesItems.SHATTERED_SPECTREGLASS_PANE,
//            ))
//                addWorldRecipe("unique/${shattered.id.path}") {
//                    leftInput(EmiStack.of(normal))
//                    rightInput(EmiIngredient.of(listOf(
//                        Items.ARROW,
//                        Items.SNOWBALL,
//                        Items.EGG,
//                        Items.TRIDENT,
//                        Items.FIREWORK_ROCKET,
//                        Items.FIRE_CHARGE,
//                        Items.WIND_CHARGE,
//                        Items.WITHER_SKELETON_SKULL,
//                    ).map {
//                        EmiStack.of(ItemStack(it).apply {
//                            this[DataComponentTypes.ITEM_NAME] = Text.translatable("netherarchives.emi.projectile")
//                            this[DataComponentTypes.RARITY] = Rarity.COMMON
//                        })
//                    }), true)
//                    output(EmiStack.of(shattered))
//                }
//        }
//    }
//
//    private fun EmiRegistry.addWorldRecipe(id: Identifier, init: EmiWorldInteractionRecipe.Builder.() -> Unit) {
//        addRecipe(EmiWorldInteractionRecipe.builder().id(id).apply(init).build())
//    }
//
//    private fun EmiRegistry.addWorldRecipe(postpath: String, init: EmiWorldInteractionRecipe.Builder.() -> Unit) {
//        addWorldRecipe(NetherArchives.id("/world/$postpath"), init)
//    }
//}
