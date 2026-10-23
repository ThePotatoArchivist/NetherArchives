package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.item.BlazeLanternItem
import archives.tater.netherarchives.item.FakeVanillaItem
import archives.tater.netherarchives.item.OarItem
import archives.tater.netherarchives.item.SoulGlassKnifeItem
import archives.tater.netherarchives.util.McUnit
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab
import net.minecraft.core.Direction
import net.minecraft.core.Registry
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.references.BlockItemId
import net.minecraft.resources.ResourceKey
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.item.*
import net.minecraft.world.item.CreativeModeTabs.*
import net.minecraft.world.item.equipment.ArmorMaterial
import net.minecraft.world.item.equipment.ArmorMaterials
import net.minecraft.world.item.equipment.ArmorType
import net.minecraft.world.item.equipment.EquipmentAsset
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.ColorCollection


object NetherArchivesItems {
    private fun register(id: ResourceKey<Item>, item: (Item.Properties) -> Item = ::Item, properties: Item.Properties = Item.Properties()) =
        Registry.register(BuiltInRegistries.ITEM, id, item(properties.setId(id)))

    private inline fun register(id: ResourceKey<Item>, noinline item: (Item.Properties) -> Item = ::Item, properties: Item.Properties.() -> Unit) =
        register(id, item, Item.Properties().apply(properties))

    private fun register(id: BlockItemId, block: Block, item: (Block, Item.Properties) -> Item = ::BlockItem, properties: Item.Properties = Item.Properties()) =
        register(
            id.item,
            { item(block, it) },
            properties.useBlockDescriptionPrefix().requiredFeatures(block.requiredFeatures())
        )

    private fun register(id: BlockItemId, item: (Block, Item.Properties) -> Item = ::BlockItem, properties: Item.Properties = Item.Properties()) =
        register(id, BuiltInRegistries.BLOCK.getValueOrThrow(id.block), item, properties)

    private fun register(id: BlockItemId, item: (Block, Item.Properties) -> Item) =
        register(id, item, Item.Properties())

    @JvmField
    val MAGNETITE = register(NetherArchivesBlockItemIds.MAGNETITE)

    @JvmField
    val SMOLDERING_MAGNETITE = register(NetherArchivesBlockItemIds.SMOLDERING_MAGNETITE)

    @JvmField
    val ROTTEN_FLESH_BLOCK = register(NetherArchivesBlockItemIds.ROTTEN_FLESH_BLOCK)

    @JvmField
    val FERMENTED_ROTTEN_FLESH_BLOCK = register(NetherArchivesBlockItemIds.FERMENTED_ROTTEN_FLESH_BLOCK)

    @JvmField
    val IRON_SLAG = register(NetherArchivesItemIds.IRON_SLAG)

    @JvmField
    val BLAZE_DUST = register(NetherArchivesBlockItemIds.BLAZE_DUST)

    @JvmField
    val BLAZE_LANTERN = register(NetherArchivesItemIds.BLAZE_LANTERN, ::BlazeLanternItem) {
        stacksTo(16)
    }

    @JvmField
    val BLAZE_TORCH = register(NetherArchivesBlockItemIds.BLAZE_TORCH) { block, settings ->
        StandingAndWallBlockItem(
            block,
            NetherArchivesBlocks.WALL_BLAZE_TORCH,
            Direction.DOWN,
            settings
        )
    }

    private fun createSkisMaterial(assetId: ResourceKey<EquipmentAsset>): ArmorMaterial = ArmorMaterial(
        ArmorMaterials.CHAINMAIL.durability,
        ArmorMaterials.CHAINMAIL.defense,
        ArmorMaterials.CHAINMAIL.enchantmentValue,
        // TODO add custom sound?
        SoundEvents.ARMOR_EQUIP_GENERIC,
        ArmorMaterials.CHAINMAIL.toughness,
        ArmorMaterials.CHAINMAIL.knockbackResistance,
        NetherArchivesItemTags.BASALT_EQUIPMENT_REPAIR,
        assetId
    )

    @JvmField
    val BASALT_SKIS = register(NetherArchivesItemIds.BASALT_SKIS) {
        humanoidArmor(createSkisMaterial(NetherArchivesEquipmentAssets.BASALT_SKIS), ArmorType.BOOTS)
        component(NetherArchivesComponents.SKIS, McUnit.INSTANCE)
    }

    @JvmField
    val DYED_BASALT_SKIS = ColorCollection.zipMap(
        NetherArchivesItemIds.DYED_BASALT_SKIS,
        NetherArchivesEquipmentAssets.DYED_BASALT_SKIS
    ) { id, assetId ->
        register(id) {
            humanoidArmor(createSkisMaterial(assetId), ArmorType.BOOTS)
            component(NetherArchivesComponents.SKIS, McUnit.INSTANCE)
        }
    }

    @JvmField
    val BASALT_OAR = register(NetherArchivesItemIds.BASALT_OAR, ::OarItem) {
        stacksTo(1)
        durability(ToolMaterial.STONE.durability)
    }

    @JvmField
    val BASALT_ROD = register(NetherArchivesItemIds.BASALT_ROD)

    @JvmField
    val BASALT_GEYSER = register(NetherArchivesBlockItemIds.BASALT_GEYSER)

    @JvmField
    val ADJUSTABLE_BASALT_GEYSER = register(NetherArchivesBlockItemIds.ADJUSTABLE_BASALT_GEYSER)

    @JvmField
    val SPECTREGLASS_SHARD = register(NetherArchivesItemIds.SPECTREGLASS_SHARD)

    @JvmField
    val SPECTREGLASS_KNIFE = register(NetherArchivesItemIds.SPECTREGLASS_KNIFE, ::SoulGlassKnifeItem) {
        durability(16)
        attributes(SoulGlassKnifeItem.attributeModifiers)
        component(DataComponents.TOOL, SoulGlassKnifeItem.toolComponent)
        component(DataComponents.WEAPON, SoulGlassKnifeItem.weaponComponent)
    }

    @JvmField
    val SPECTREGLASS = register(NetherArchivesBlockItemIds.SPECTREGLASS)

    @JvmField
    val SHATTERED_SPECTREGLASS = register(NetherArchivesBlockItemIds.SHATTERED_SPECTREGLASS)

    @JvmField
    val SPECTREGLASS_PANE = register(NetherArchivesBlockItemIds.SPECTREGLASS_PANE)

    @JvmField
    val SHATTERED_SPECTREGLASS_PANE = register(NetherArchivesBlockItemIds.SHATTERED_SPECTREGLASS_PANE)

    @JvmField
    val DUMMY_SOUL_FIRE = register(NetherArchivesItemIds.DUMMY_SOUL_FIRE, ::FakeVanillaItem)

    const val CREATIVE_TAB_TRANSLATION = "itemGroup.netherarchives.nether_archives"

    val CREATIVE_TAB: CreativeModeTab = Registry.register(
        BuiltInRegistries.CREATIVE_MODE_TAB,
        NetherArchives.id("nether_archives"),
        FabricCreativeModeTab.builder().apply {
            title(Component.translatable(CREATIVE_TAB_TRANSLATION))
            icon { BLAZE_LANTERN.defaultInstance }
            displayItems { _, output ->
                val gameplayColorOrder = [
                    DyeColor.WHITE,
                    DyeColor.LIGHT_GRAY,
                    DyeColor.GRAY,
                    DyeColor.BLACK,
                    DyeColor.BROWN,
                    DyeColor.RED,
                    DyeColor.ORANGE,
                    DyeColor.YELLOW,
                    DyeColor.LIME,
                    DyeColor.GREEN,
                    DyeColor.CYAN,
                    DyeColor.LIGHT_BLUE,
                    DyeColor.BLUE,
                    DyeColor.PURPLE,
                    DyeColor.MAGENTA,
                    DyeColor.PINK
                ]

                with (output) {
                    accept(ROTTEN_FLESH_BLOCK)
                    accept(FERMENTED_ROTTEN_FLESH_BLOCK)
                    accept(MAGNETITE)
                    accept(SMOLDERING_MAGNETITE)
                    accept(IRON_SLAG)
                    accept(BLAZE_DUST)
                    accept(BLAZE_LANTERN)
                    accept(BLAZE_TORCH)
                    accept(BASALT_ROD)
                    accept(BASALT_SKIS)
                    for (color in gameplayColorOrder)
                        accept(DYED_BASALT_SKIS.pick(color))
                    accept(BASALT_OAR)
                    accept(BASALT_GEYSER)
                    accept(ADJUSTABLE_BASALT_GEYSER)
                    accept(SPECTREGLASS_SHARD)
                    accept(SPECTREGLASS_KNIFE)
                    accept(SPECTREGLASS)
                    accept(SHATTERED_SPECTREGLASS)
                    accept(SPECTREGLASS_PANE)
                    accept(SHATTERED_SPECTREGLASS_PANE)
                }
            }
        }.build()
    )

    fun init() {
        mapOf(
            INGREDIENTS to [IRON_SLAG, BASALT_ROD, SPECTREGLASS_SHARD],
            NATURAL_BLOCKS to [MAGNETITE, SMOLDERING_MAGNETITE, ROTTEN_FLESH_BLOCK, FERMENTED_ROTTEN_FLESH_BLOCK, BASALT_GEYSER],
            COMBAT to [BLAZE_DUST, BLAZE_LANTERN, SPECTREGLASS_KNIFE],
            TOOLS_AND_UTILITIES to buildList {
                add(BASALT_SKIS)
                DYED_BASALT_SKIS.forEach { add(it) }
                add(BASALT_OAR)
            },
            FUNCTIONAL_BLOCKS to [BLAZE_TORCH, SPECTREGLASS, SHATTERED_SPECTREGLASS, SPECTREGLASS_PANE, SHATTERED_SPECTREGLASS_PANE],
            REDSTONE_BLOCKS to [ADJUSTABLE_BASALT_GEYSER]
        ).forEach { (group, items) ->
            CreativeModeTabEvents.modifyOutputEvent(group).register {
                items.forEach(it::accept)
            }
        }
    }
}
