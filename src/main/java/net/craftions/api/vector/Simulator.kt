package net.craftions.api.vector

import org.bukkit.Location

class Simulator constructor(
    val location: Location
) {
    private val loc = location.clone()

    fun moveForward(range: Double): Simulator {
        loc.add(loc.direction.normalize().multiply(range))
        return this
    }

    fun moveBackward(range: Double): Simulator {
        return moveForward(-range)
    }

    fun moveRight(range: Double): Simulator {
        loc.yaw += 90
        loc.add(loc.direction.normalize().multiply(range))
        loc.yaw -= 90
        return this
    }

    fun moveLeft(range: Double): Simulator {
        return moveRight(-range)
    }

    fun moveUp(range: Double): Simulator {
        loc.pitch -= 90
        loc.add(loc.direction.normalize().multiply(range))
        loc.pitch += 90
        return this
    }

    fun moveDown(range: Double): Simulator {
        return moveUp(-range)
    }

    fun setPitch(value: Float): Simulator {
        loc.pitch = value
        return this;
    }

    fun setYaw(value: Float): Simulator {
        loc.yaw = value
        return this
    }

    fun turnRight(range: Float): Simulator {
        loc.yaw += range
        return this
    }

    fun turnLeft(range: Float): Simulator {
        return turnRight(-range)
    }

    fun turnUp(range: Float): Simulator {
        loc.pitch -= range
        return this
    }

    fun turnDown(range: Float): Simulator {
        return turnUp(-range)
    }

    fun simulate(): Location {
        return loc.clone().apply { direction = location.direction }
    }
}