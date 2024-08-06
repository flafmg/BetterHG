package flaf.betterHG.listeners

import flaf.betterHG.data.arena.loot.LootData
import flaf.betterHG.managers.arena.world.generateLoot
import org.bukkit.Material
import org.bukkit.block.Barrel
import org.bukkit.block.Chest
import org.bukkit.block.ShulkerBox
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.InventoryHolder


class LootEventListener(private val lootData: LootData) : Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.action == Action.RIGHT_CLICK_BLOCK) {
            val block = event.clickedBlock ?: return
            val container: InventoryHolder = when (block.type) {
                Material.CHEST -> block.state as Chest
                Material.SHULKER_BOX -> block.state as ShulkerBox
                Material.BARREL -> block.state as Barrel
                else -> return
            }
            generateLoot(container, lootData)
        }
    }
}