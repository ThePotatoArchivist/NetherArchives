package archives.tater.netherarchives.item

import net.minecraft.resources.Identifier
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

class FakeVanillaItem(properties: Properties) : Item(properties) {
    override fun getCreatorNamespace(stack: ItemStack): String = Identifier.DEFAULT_NAMESPACE
}