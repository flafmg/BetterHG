package flaf.betterHG.managers.arena.world

import flaf.betterHG.data.arena.loot.LootData
import flaf.betterHG.data.arena.loot.LootItemData
import org.bukkit.block.Chest
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import java.util.*

fun generateLoot(container: InventoryHolder, lootData: LootData) {
    val random = Random()
    val inventory = container.inventory
    val maxSlots = inventory.size
    val slotsToFill = random.nextInt(lootData.minSlots, lootData.maxSlots)

    inventory.clear()

    val lootItems = lootData.getLootItems().sortedByDescending { it.rarity }

    for (i in 0 until slotsToFill) {
        val item = selectRandomItem(lootItems, random)
        if (item != null) {
            val itemStack: ItemStack = item.getAsItemWithAmount()
            val randomSlot = random.nextInt(maxSlots)
            inventory.setItem(randomSlot, itemStack)
        }
    }
}

fun selectRandomItem(lootItems: List<LootItemData>, random: Random): LootItemData? {
    val totalRarity = lootItems.sumOf { it.rarity }
    if (totalRarity == 0.0) return null

    var randValue = random.nextDouble() * totalRarity

    for (item in lootItems) {
        randValue -= item.rarity
        if (randValue <= 0) {
            return item
        }
    }

    return null
}
