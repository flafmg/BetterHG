package flaf.betterHG.data.arena

import org.bukkit.entity.Player

data class PlayerData(
    val playersInGame: MutableList<Player> = mutableListOf(),
    val spectators: MutableList<Player> = mutableListOf()
) {
    fun addPlayer(player: Player) {
        if (!playersInGame.contains(player)) {
            playersInGame.add(player)
        }
    }

    fun removePlayer(player: Player) {
        playersInGame.remove(player)
    }

    fun addSpectator(player: Player) {
        if (!spectators.contains(player)) {
            spectators.add(player)
        }
    }

    fun removeSpectator(player: Player) {
        spectators.remove(player)
    }
}