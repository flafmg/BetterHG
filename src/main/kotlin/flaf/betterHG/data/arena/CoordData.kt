package flaf.betterHG.data.arena

import org.bukkit.configuration.ConfigurationSection

data class CoordData(
    val x: Double,
    val y: Double,
    val z: Double
) {
    fun serialize(section: ConfigurationSection) {
        section.set("x", x)
        section.set("y", y)
        section.set("z", z)
    }

    companion object {
        fun deserialize(section: ConfigurationSection): CoordData{
            val x = section.getDouble("x")
            val y = section.getDouble("y")
            val z = section.getDouble("z")
            return CoordData(x, y, z)
        }
    }
}