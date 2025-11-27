package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.damagesource.DamageSources
import net.minecraft.world.damagesource.DamageType
import net.minecraft.resources.ResourceKey
import net.minecraft.core.registries.Registries

object NetherArchivesDamageTypes {
    val PADDLE_BURN: ResourceKey<DamageType> = ResourceKey.create(
        Registries.DAMAGE_TYPE,
        NetherArchives.id("paddle_burn")
    )

    val DamageSources.paddleBurn: DamageSource get() = source(PADDLE_BURN)

    fun register() {}
}
