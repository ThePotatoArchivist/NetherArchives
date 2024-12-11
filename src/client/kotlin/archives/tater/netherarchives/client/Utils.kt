package archives.tater.netherarchives.client

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer
import net.minecraft.item.Item

fun registerArmorRenderer(vararg items: Item, renderer: ArmorRenderer) {
    ArmorRenderer.register(renderer, *items)
}
