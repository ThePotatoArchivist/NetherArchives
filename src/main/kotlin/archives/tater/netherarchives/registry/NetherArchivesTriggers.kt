package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.advancements.CriterionTrigger
import net.minecraft.advancements.criterion.ConsumeItemTrigger
import net.minecraft.advancements.criterion.DefaultBlockInteractionTrigger
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries

object NetherArchivesTriggers {
    private fun <T: CriterionTrigger<*>> register(path: String, trigger: T): T = Registry.register(
        BuiltInRegistries.TRIGGER_TYPES,
        NetherArchives.id(path),
        trigger
    )

    val SKIS_PADDLE = register("skis_paddle", ConsumeItemTrigger())
    val AIRSKI = register("airski", DefaultBlockInteractionTrigger())
    val FERMENT = register("ferment_rotten_flesh", DefaultBlockInteractionTrigger())

    fun register() {}
}