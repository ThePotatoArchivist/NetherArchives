package archives.tater.netherarchives

import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.damage.DamageSources
import net.minecraft.entity.damage.DamageType
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier

object NetherArchivesDamageTypes {
    val PADDLE_BURN: RegistryKey<DamageType> = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, NetherArchives.id("paddle_burn"))

    val DamageSources.paddleBurn: DamageSource get() = create(PADDLE_BURN)

    fun register() {}
}
