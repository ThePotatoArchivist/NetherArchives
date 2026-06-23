package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import archives.tater.netherarchives.item.BlazeLanternItem
import archives.tater.netherarchives.item.FakeVanillaItem
import archives.tater.netherarchives.item.OarItem
import archives.tater.netherarchives.item.SoulGlassKnifeItem
import archives.tater.netherarchives.util.ItemProperties
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab
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
import net.minecraft.world.item.CreativeModeTabs.*
import net.minecraft.world.item.equipment.*
import net.minecraft.world.level.block.Block
import net.minecraft.world.item.Item.Properties as ItemProperties


object NetherArchivesItems {
    private fun register(id: Identifier, item: (ItemProperties) -> Item = ::Item, settings: ItemProperties = ItemProperties()): Item {
        val key = ResourceKey.create(Registries.ITEM, id)
        return Registry.register(BuiltInRegistries.ITEM, key, item(settings.setId(key)))
    }

    private fun register(path: String, item: (ItemProperties) -> Item = ::Item, settings: ItemProperties = ItemProperties()): Item =
        register(NetherArchives.id(path), item, settings)

    private fun register(path: String, item: (ItemProperties) -> Item = ::Item, settingsInit: ItemProperties.() -> Unit): Item =
        register(NetherArchives.id(path), item, ItemProperties(settingsInit))

    private fun register(block: Block, item: (Block, ItemProperties) -> Item = ::BlockItem, properties: ItemProperties = ItemProperties()): Item =
        register(
            BuiltInRegistries.BLOCK.getKey(block),
            { item(block, it) },
            properties.useBlockDescriptionPrefix().requiredFeatures(block.requiredFeatures())
        )

    private fun register(block: Block, item: (Block, ItemProperties) -> Item) =
        register(block, item, ItemProperties())

    private fun register(block: Block, properties: ItemProperties = ItemProperties()): Item =
        register(block, ::BlockItem, properties)

    @JvmField
    val MAGNETITE = register(ModBlocks.MAGNETITE)

    @JvmField
    val SMOLDERING_MAGNETITE = register(ModBlocks.SMOLDERING_MAGNETITE)

    @JvmField
    val ROTTEN_FLESH_BLOCK = register(ModBlocks.ROTTEN_FLESH_BLOCK)

    @JvmField
    val FERMENTED_ROTTEN_FLESH_BLOCK = register(ModBlocks.FERMENTED_ROTTEN_FLESH_BLOCK)

    @JvmField
    val IRON_SLAG = register("iron_slag")

    @JvmField
    val BLAZE_DUST = register(ModBlocks.BLAZE_DUST)

    @JvmField
    val BLAZE_LANTERN = register("blaze_lantern", ::BlazeLanternItem) {
        stacksTo(16)
    }

    @JvmField
    val BLAZE_TORCH = register(ModBlocks.BLAZE_TORCH) { block, settings ->
        StandingAndWallBlockItem(
            block,
            ModBlocks.WALL_BLAZE_TORCH,
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
        ModTags.BASALT_EQUIPMENT_REPAIR,
        BASALT_EQUIPMENT
    )

    @JvmField
    val BASALT_SKIS = register("basalt_skis", ::Item) {
        humanoidArmor(BASALT_ARMOR_MATERIAL, ArmorType.BOOTS)
    }

    @JvmField
    val BASALT_OAR = register("basalt_oar", ::OarItem) {
        stacksTo(1)
        durability(ToolMaterial.STONE.durability)
    }

    @JvmField
    val BASALT_ROD = register("basalt_rod")

    @JvmField
    val BASALT_GEYSER = register(ModBlocks.BASALT_GEYSER)

    @JvmField
    val ADJUSTABLE_BASALT_GEYSER = register(ModBlocks.ADJUSTABLE_BASALT_GEYSER)

    @JvmField
    val SPECTREGLASS_SHARD = register("spectreglass_shard")

    @JvmField
    val SPECTREGLASS_KNIFE = register("spectreglass_knife", ::SoulGlassKnifeItem) {
        durability(16)
        attributes(SoulGlassKnifeItem.attributeModifiers)
        component(DataComponents.TOOL, SoulGlassKnifeItem.toolComponent)
        component(DataComponents.WEAPON, SoulGlassKnifeItem.weaponComponent)
    }

    @JvmField
    val SPECTREGLASS = register(ModBlocks.SPECTREGLASS)

    @JvmField
    val SHATTERED_SPECTREGLASS = register(ModBlocks.SHATTERED_SPECTREGLASS)

    @JvmField
    val SPECTREGLASS_PANE = register(ModBlocks.SPECTREGLASS_PANE)

    @JvmField
    val SHATTERED_SPECTREGLASS_PANE = register(ModBlocks.SHATTERED_SPECTREGLASS_PANE)

    @JvmField
    val DUMMY_SOUL_FIRE = register("dummy_soul_fire", ::FakeVanillaItem)

    const val CREATIVE_TAB_TRANSLATION = "itemGroup.netherarchives.nether_archives"

    val CREATIVE_TAB: CreativeModeTab = Registry.register(
        BuiltInRegistries.CREATIVE_MODE_TAB,
        NetherArchives.id("nether_archives"),
        FabricCreativeModeTab.builder().apply {
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

    fun init() {
        mapOf(
            INGREDIENTS to setOf(IRON_SLAG, BASALT_ROD, SPECTREGLASS_SHARD),
            NATURAL_BLOCKS to setOf(MAGNETITE, SMOLDERING_MAGNETITE, ROTTEN_FLESH_BLOCK, FERMENTED_ROTTEN_FLESH_BLOCK, BASALT_GEYSER),
            COMBAT to setOf(BLAZE_DUST, BLAZE_LANTERN, SPECTREGLASS_KNIFE),
            TOOLS_AND_UTILITIES to setOf(BASALT_SKIS, BASALT_OAR),
            FUNCTIONAL_BLOCKS to setOf(BLAZE_TORCH, SPECTREGLASS, SHATTERED_SPECTREGLASS, SPECTREGLASS_PANE, SHATTERED_SPECTREGLASS_PANE),
            REDSTONE_BLOCKS to setOf(ADJUSTABLE_BASALT_GEYSER)
        ).forEach { (group, items) ->
            CreativeModeTabEvents.modifyOutputEvent(group).register {
                items.forEach(it::accept)
            }
        }
    }
}

internal typealias ModItems = NetherArchivesItems