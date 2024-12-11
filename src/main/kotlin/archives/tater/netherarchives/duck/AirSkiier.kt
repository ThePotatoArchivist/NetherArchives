package archives.tater.netherarchives.duck

import net.minecraft.entity.LivingEntity

internal inline var AirSkiier.isAirSkiing
    get() = `netherarchives$isAirSkiing`()
    set(value) {
        `netherarchives$setAirSkiing`(value)
    }

internal inline var LivingEntity.isAirSkiing
    get() = (this as AirSkiier).isAirSkiing
    set(value) {
        (this as AirSkiier).isAirSkiing = value
    }
