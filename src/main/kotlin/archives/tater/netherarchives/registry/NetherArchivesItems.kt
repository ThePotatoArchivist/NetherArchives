package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.item.BlazeLanternItem
import archives.tater.netherarchives.item.OarItem
import archives.tater.netherarchives.item.SoulGlassKnifeItem
import archives.tater.netherarchives.util.ItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.core.Direction
import net.minecraft.core.Registry
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.item.*
import net.minecraft.world.item.equipment.*
import net.minecraft.world.level.block.Block
import net.minecraft.world.item.Item.Properties as ItemSettings


object NetherArchivesItems {
    private fun register(id: Identifier, item: (ItemSettings) -> Item = ::Item, settings: ItemSettings = ItemSettings()): Item {
        val key = ResourceKey.create(Registries.ITEM, id)
        return Registry.register(BuiltInRegistries.ITEM, key, item(settings.setId(key)))
    }

    private fun register(path: String, item: (ItemSettings) -> Item = ::Item, settings: ItemSettings = ItemSettings()): Item =
        register(NetherArchives.id(path), item, settings)

    private fun register(path: String, item: (ItemSettings) -> Item = ::Item, settingsInit: ItemSettings.() -> Unit): Item =
        register(NetherArchives.id(path), item, ItemSettings(settingsInit))

    private fun register(block: Block, settings: ItemSettings = ItemSettings()): Item =
        Items.registerBlock(block, settings)

    private fun register(block: Block, item: (Block, ItemSettings) -> Item): Item =
        Items.registerBlock(block, item)

    val MAGNETITE = register(NetherArchivesBlocks.MAGNETITE)

    val SMOLDERING_MAGNETITE = register(NetherArchivesBlocks.SMOLDERING_MAGNETITE)

    val ROTTEN_FLESH_BLOCK = register(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK)

    val FERMENTED_ROTTEN_FLESH_BLOCK = register(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK)

    val IRON_SLAG = register("iron_slag")

    val BLAZE_DUST = register(NetherArchivesBlocks.BLAZE_DUST)

    val BLAZE_LANTERN = register("blaze_lantern", ::BlazeLanternItem) {
        stacksTo(16)
    }

    val BLAZE_TORCH = register(NetherArchivesBlocks.BLAZE_TORCH) { block, settings ->
        StandingAndWallBlockItem(
            block,
            NetherArchivesBlocks.WALL_BLAZE_TORCH,
            Direction.DOWN,
            settings
        )
    }

    val BASALT_EQUIPMENT: ResourceKey<EquipmentAsset> = ResourceKey.create(EquipmentAssets.ROOT_ID, NetherArchives.id("basalt"))
    val BASALT_ARMOR_MATERIAL = ArmorMaterial(
        ArmorMaterials.CHAINMAIL.durability,
        ArmorMaterials.CHAINMAIL.defense,
        ArmorMaterials.CHAINMAIL.enchantmentValue,
//            // TODO add custom sound?
        SoundEvents.ARMOR_EQUIP_GENERIC,
        ArmorMaterials.CHAINMAIL.toughness,
        ArmorMaterials.CHAINMAIL.knockbackResistance,
        NetherArchivesTags.BASALT_EQUIPMENT_REPAIR,
        BASALT_EQUIPMENT
    )

    val BASALT_SKIS = register("basalt_skis", ::Item) {
        humanoidArmor(BASALT_ARMOR_MATERIAL, ArmorType.BOOTS)
    }
    @JvmField
    val BASALT_OAR = register("basalt_oar", ::OarItem) {
        stacksTo(1)
        durability(ToolMaterial.STONE.durability)
    }
    val BASALT_ROD = register("basalt_rod")

    val BASALT_GEYSER = register(NetherArchivesBlocks.BASALT_GEYSER)

    val ADJUSTABLE_BASALT_GEYSER = register(NetherArchivesBlocks.ADJUSTABLE_BASALT_GEYSER)

    val SPECTREGLASS_SHARD = register("spectreglass_shard")

    val SPECTREGLASS_KNIFE = register("spectreglass_knife", ::SoulGlassKnifeItem) {
        durability(16)
        attributes(SoulGlassKnifeItem.attributeModifiers)
        component(DataComponents.TOOL, SoulGlassKnifeItem.toolComponent)
        component(DataComponents.WEAPON, SoulGlassKnifeItem.weaponComponent)
    }

    val SPECTREGLASS = register(NetherArchivesBlocks.SPECTREGLASS)

    val SHATTERED_SPECTREGLASS = register(NetherArchivesBlocks.SHATTERED_SPECTREGLASS)

    val SPECTREGLASS_PANE = register(NetherArchivesBlocks.SPECTREGLASS_PANE)

    val SHATTERED_SPECTREGLASS_PANE = register(NetherArchivesBlocks.SHATTERED_SPECTREGLASS_PANE)

    // Registered under minecraft namespace so that in the tooltip it is labeled as coming from minecraft
    val DUMMY_SOUL_FIRE = register(Identifier.withDefaultNamespace("netherarchives/dummy/soul_fire"))

    const val CREATIVE_TAB_TRANSLATION = "itemGroup.netherarchives.nether_archives"

    val CREATIVE_TAB: CreativeModeTab = Registry.register(
        BuiltInRegistries.CREATIVE_MODE_TAB,
        NetherArchives.id("nether_archives"),
        FabricItemGroup.builder().apply {
            title(Component.translatable(CREATIVE_TAB_TRANSLATION))
            icon { BLAZE_LANTERN.defaultInstance }
            displayItems { _, output ->
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

    private val itemGroups = mapOf(
        CreativeModeTabs.INGREDIENTS to setOf(IRON_SLAG, BASALT_ROD, SPECTREGLASS_SHARD),
        CreativeModeTabs.NATURAL_BLOCKS to setOf(MAGNETITE, SMOLDERING_MAGNETITE, ROTTEN_FLESH_BLOCK, FERMENTED_ROTTEN_FLESH_BLOCK, BASALT_GEYSER),
        CreativeModeTabs.COMBAT to setOf(BLAZE_DUST, BLAZE_LANTERN, SPECTREGLASS_KNIFE),
        CreativeModeTabs.TOOLS_AND_UTILITIES to setOf(BASALT_SKIS, BASALT_OAR),
        CreativeModeTabs.FUNCTIONAL_BLOCKS to setOf(BLAZE_TORCH, SPECTREGLASS, SHATTERED_SPECTREGLASS, SPECTREGLASS_PANE, SHATTERED_SPECTREGLASS_PANE),
        CreativeModeTabs.REDSTONE_BLOCKS to setOf(ADJUSTABLE_BASALT_GEYSER)
    )

    fun register() {
        itemGroups.forEach { (group, items) ->
            ItemGroupEvents.modifyEntriesEvent(group).register {
                items.forEach(it::accept)
            }
        }
    }
}
