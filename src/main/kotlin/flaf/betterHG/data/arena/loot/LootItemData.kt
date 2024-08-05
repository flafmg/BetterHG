package flaf.betterHG.data.arena.loot


import de.tr7zw.changeme.nbtapi.NBT // kotlin is broken as hell
import de.tr7zw.changeme.nbtapi.NBTItem
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemStack

data class LootItemData(
    val itemType: String,
    val nbt: String,
    val rarity: Double,
    val minAmount: Int,
    val maxAmount: Int
) {
    fun serialize(section: ConfigurationSection) {
        section.set("itemType", itemType)
        section.set("nbt", nbt)
        section.set("rarity", rarity)
        section.set("minAmount", minAmount)
        section.set("maxAmount", maxAmount)
    }

    companion object {
        fun deserialize(key: String, section: ConfigurationSection): LootItemData {
            val itemType = section.getString("itemType") ?: key
            val nbt = section.getString("nbt") ?: ""
            val rarity = section.getDouble("rarity")
            val minAmount = section.getInt("minAmount")
            val maxAmount = section.getInt("maxAmount")
            return LootItemData(itemType, nbt, rarity, minAmount, maxAmount)
        }
    }

    fun getAsItem(): ItemStack {
        val material = Material.matchMaterial(itemType) ?: throw IllegalArgumentException("Invalid item type: $itemType")
        val itemStack = ItemStack(material, minAmount)
        val nbtItem = NBTItem(itemStack)
        nbtItem.mergeCompound(NBT.parseNBT(nbt))
        return nbtItem.item
    }
}
