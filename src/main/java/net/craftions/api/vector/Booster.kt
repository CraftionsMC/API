package net.craftions.api.vector

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.util.Vector

class Booster constructor(
    val location: Location
) {
    private val loc = location.clone()

    fun turnRight(range: Float): Booster {
        loc.yaw += range
        return this
    }

    fun turnLeft(range: Float): Booster {
        return turnRight(-range)
    }

    fun turnUp(range: Float): Booster {
        loc.pitch -= range
        return this
    }

    fun turnDown(range: Float): Booster {
        return turnUp(-range)
    }

    fun setYaw(value: Float): Booster {
        loc.yaw = value
        return this
    }

    fun setPitch(value: Float): Booster {
        loc.pitch = value
        return this
    }

    fun boost(entity: Entity, power: Double) {
        entity.velocity = loc.direction.normalize().multiply(power)
    }

    fun getVector(power: Double): Vector {
        return loc.direction.normalize().multiply(power)
    }

    companion object {
        fun boostTo(entity: Entity, location: Location, power: Double) {
            entity.velocity = entity.location.toVector().subtract(location.toVector()).multiply(-power).normalize()
        }

        fun boostAwayFrom(entity: Entity, location: Location, power: Double) {
            boostTo(entity, location, -power)
        }
    }
}