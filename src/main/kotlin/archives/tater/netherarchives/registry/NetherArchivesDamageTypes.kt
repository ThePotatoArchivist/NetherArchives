package archives.tater.netherarchives.registry

import archives.tater.netherarchives.NetherArchives
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.damage.DamageSources
import net.minecraft.entity.damage.DamageType
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys

object NetherArchivesDamageTypes {
    val PADDLE_BURN: RegistryKey<DamageType> = RegistryKey.of(RegistryKeys.DAMAGE_TYPE,
        NetherArchives.id("paddle_burn")
    )

    val DamageSources.paddleBurn: DamageSource get() = create(PADDLE_BURN)

    fun register() {}
}
