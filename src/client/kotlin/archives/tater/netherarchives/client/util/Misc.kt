package archives.tater.netherarchives.client.util

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.world.item.Item

internal fun registerArmorRenderer(vararg items: Item, renderer: ArmorRenderer) {
    ArmorRenderer.register(renderer, *items)
}

fun MeshDefinition(init: MeshDefinition.() -> Unit) = MeshDefinition().apply(init)
