package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.item.BlazeLanternItem
import archives.tater.netherarchives.item.OarItem
import archives.tater.netherarchives.item.SoulGlassKnifeItem
import archives.tater.netherarchives.util.ItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.block.Block
import net.minecraft.component.DataComponentTypes
import net.minecraft.item.*
import net.minecraft.item.equipment.*
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction
import net.minecraft.item.Item.Settings as ItemSettings


object NetherArchivesItems {
    private fun register(id: Identifier, item: (ItemSettings) -> Item = ::Item, settings: ItemSettings = ItemSettings()): Item {
        val key = RegistryKey.of(RegistryKeys.ITEM, id)
        return Registry.register(Registries.ITEM, key, item(settings.registryKey(key)))
    }

    private fun register(path: String, item: (ItemSettings) -> Item = ::Item, settings: ItemSettings = ItemSettings()): Item =
        register(NetherArchives.id(path), item, settings)

    private fun register(path: String, item: (ItemSettings) -> Item = ::Item, settingsInit: ItemSettings.() -> Unit): Item =
        register(NetherArchives.id(path), item, ItemSettings(settingsInit))

    private fun register(block: Block, settings: ItemSettings = ItemSettings()): Item =
        Items.register(block, settings)

    private fun register(block: Block, item: (Block, ItemSettings) -> Item): Item =
        Items.register(block, item)

    val MAGNETITE = register(NetherArchivesBlocks.MAGNETITE)

    val SMOLDERING_MAGNETITE = register(NetherArchivesBlocks.SMOLDERING_MAGNETITE)

    val ROTTEN_FLESH_BLOCK = register(NetherArchivesBlocks.ROTTEN_FLESH_BLOCK)

    val FERMENTED_ROTTEN_FLESH_BLOCK = register(NetherArchivesBlocks.FERMENTED_ROTTEN_FLESH_BLOCK)

    val IRON_SLAG = register("iron_slag")

    val BLAZE_DUST = register(NetherArchivesBlocks.BLAZE_DUST)

    val BLAZE_LANTERN = register("blaze_lantern", ::BlazeLanternItem) {
        maxCount(16)
    }

    val BLAZE_TORCH = register(NetherArchivesBlocks.BLAZE_TORCH) { block, settings ->
        VerticallyAttachableBlockItem(
            block,
            NetherArchivesBlocks.WALL_BLAZE_TORCH,
            Direction.DOWN,
            settings
        )
    }

    val BASALT_EQUIPMENT: RegistryKey<EquipmentAsset> = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, NetherArchives.id("basalt"))
    val BASALT_ARMOR_MATERIAL = ArmorMaterial(
        ArmorMaterials.CHAIN.durability,
        ArmorMaterials.CHAIN.defense,
        ArmorMaterials.CHAIN.enchantmentValue,
//            // TODO add custom sound?
        SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
        ArmorMaterials.CHAIN.toughness,
        ArmorMaterials.CHAIN.knockbackResistance,
        NetherArchivesTags.BASALT_EQUIPMENT_REPAIR,
        BASALT_EQUIPMENT
    )

    val BASALT_SKIS = register("basalt_skis", ::Item) {
        armor(BASALT_ARMOR_MATERIAL, EquipmentType.BOOTS)
    }
    @JvmField
    val BASALT_OAR = register("basalt_oar", ::OarItem) {
        maxCount(1)
        maxDamage(ToolMaterial.STONE.durability)
    }
    val BASALT_ROD = register("basalt_rod")

    val BASALT_GEYSER = register(NetherArchivesBlocks.BASALT_GEYSER)

    val ADJUSTABLE_BASALT_GEYSER = register(NetherArchivesBlocks.ADJUSTABLE_BASALT_GEYSER)

    val SPECTREGLASS_SHARD = register("spectreglass_shard")

    val SPECTREGLASS_KNIFE = register("spectreglass_knife", ::SoulGlassKnifeItem) {
        maxDamage(16)
        attributeModifiers(SoulGlassKnifeItem.attributeModifiers)
        component(DataComponentTypes.TOOL, SoulGlassKnifeItem.toolComponent)
        component(DataComponentTypes.WEAPON, SoulGlassKnifeItem.weaponComponent)
    }

    val SPECTREGLASS = register(NetherArchivesBlocks.SPECTREGLASS)

    val SHATTERED_SPECTREGLASS = register(NetherArchivesBlocks.SHATTERED_SPECTREGLASS)

    val SPECTREGLASS_PANE = register(NetherArchivesBlocks.SPECTREGLASS_PANE)

    val SHATTERED_SPECTREGLASS_PANE = register(NetherArchivesBlocks.SHATTERED_SPECTREGLASS_PANE)

    // Registered under minecraft namespace so that in the tooltip it is labeled as coming from minecraft
    val DUMMY_SOUL_FIRE = register(Identifier.ofVanilla("netherarchives/dummy/soul_fire"))

    private val itemGroups = mapOf(
        ItemGroups.INGREDIENTS to setOf(IRON_SLAG, BASALT_ROD, SPECTREGLASS_SHARD),
        ItemGroups.NATURAL to setOf(MAGNETITE, SMOLDERING_MAGNETITE, ROTTEN_FLESH_BLOCK, FERMENTED_ROTTEN_FLESH_BLOCK, BASALT_GEYSER),
        ItemGroups.COMBAT to setOf(BLAZE_DUST, BLAZE_LANTERN, SPECTREGLASS_KNIFE),
        ItemGroups.TOOLS to setOf(BASALT_SKIS, BASALT_OAR),
        ItemGroups.FUNCTIONAL to setOf(BLAZE_TORCH, SPECTREGLASS, SHATTERED_SPECTREGLASS, SPECTREGLASS_PANE, SHATTERED_SPECTREGLASS_PANE),
        ItemGroups.REDSTONE to setOf(ADJUSTABLE_BASALT_GEYSER)
    )

    fun register() {
        itemGroups.forEach { (group, items) ->
            ItemGroupEvents.modifyEntriesEvent(group).register {
                items.forEach(it::add)
            }
        }
    }
}
