package flaf.betterHG.data.arena

import flaf.betterHG.data.YamlConfig
import flaf.betterHG.data.arena.loot.LootData
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.plugin.Plugin

data class ArenaData(
    var playerData: PlayerData,
    var arenaState: ArenaState,
    var loot: LootData,

    val arenaName: String,

    val maxPlayers: UInt,
    val minimumPlayers: UInt,

    val worldName: String,
    val build: Boolean,

    val lootName: String,

    val spawnPoints: List<CoordData>,
    val arenaCenters: List<CoordData>
) {
    fun serialize(config: YamlConfig) {
        val section = config.getConfiguration().createSection(arenaName)

        section.set("maxPlayers", maxPlayers.toInt())
        section.set("minimumPlayers", minimumPlayers.toInt())
        section.set("worldName", worldName)
        section.set("build", build)
        section.set("lootName", lootName)

        val spawnPointsSection = section.createSection("spawnPoints")
        spawnPoints.forEachIndexed { index, coord ->
            val coordSection = spawnPointsSection.createSection(index.toString())
            coord.serialize(coordSection)
        }

        val arenaCentersSection = section.createSection("arenaCenters")
        arenaCenters.forEachIndexed { index, coord ->
            val coordSection = arenaCentersSection.createSection(index.toString())
            coord.serialize(coordSection)
        }

        config.save()
    }

    companion object {
        fun deserialize(arenaName: String, config: YamlConfig, plugin: Plugin): ArenaData {
            val section = config.getConfiguration().getConfigurationSection(arenaName) ?: throw IllegalArgumentException("Arena not found")

            val maxPlayers = section.getInt("maxPlayers").toUInt()
            val minimumPlayers = section.getInt("minimumPlayers").toUInt()
            val worldName = section.getString("worldName") ?: ""
            val build = section.getBoolean("build")
            val lootName = section.getString("lootName") ?: throw IllegalArgumentException("Loot name not found")

            val lootConfig = YamlConfig("/loot/$lootName.yml", plugin)
            if (!lootConfig.load()) throw IllegalArgumentException("Failed to load loot configuration")
            val loot = LootData(lootConfig)

            val spawnPointsSection = section.getConfigurationSection("spawnPoints") ?: throw IllegalArgumentException("Spawn points not found")
            val spawnPoints = spawnPointsSection.getKeys(false).map { key ->
                CoordData.deserialize(spawnPointsSection.getConfigurationSection(key)!!)
            }

            val arenaCentersSection = section.getConfigurationSection("arenaCenters") ?: throw IllegalArgumentException("Arena centers not found")
            val arenaCenters = arenaCentersSection.getKeys(false).map { key ->
                CoordData.deserialize(arenaCentersSection.getConfigurationSection(key)!!)
            }

            return ArenaData(
                PlayerData(),
                ArenaState.STARTING,
                loot,
                arenaName,
                maxPlayers,
                minimumPlayers,
                worldName,
                build,
                lootName,
                spawnPoints,
                arenaCenters
            )
        }
    }
}
