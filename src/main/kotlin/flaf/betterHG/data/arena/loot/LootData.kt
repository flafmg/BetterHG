package flaf.betterHG.data.arena.loot

import flaf.betterHG.data.YamlConfig

class LootData(private val config: YamlConfig) {

    private val lootTable: MutableList<LootItemData> = mutableListOf()
    var minSlots: Int = 0
    var maxSlots: Int = 27

    init {
        loadFromConfig()
    }

    private fun loadFromConfig() {
        val itemsSection = config.getConfigurationSection("items")
        if (itemsSection != null) {
            for (key in itemsSection.getKeys(false)) {
                val section = itemsSection.getConfigurationSection(key) ?: continue
                val lootItemData = LootItemData.deserialize(key, section)
                lootTable.add(lootItemData)
            }
        }
        minSlots = config.getInt("min-slots", 0)
        maxSlots = config.getInt("max-slots", 27)
    }

    fun addLootItem(lootItemData: LootItemData) {
        lootTable.add(lootItemData)
    }

    fun removeLootItem(lootItemData: LootItemData) {
        lootTable.remove(lootItemData)
    }

    fun getLootItems(): List<LootItemData> {
        return lootTable
    }

    fun saveToConfig() {
        config.getConfiguration().set("min-slots", minSlots)
        config.getConfiguration().set("max-slots", maxSlots)

        val itemsSection = config.getConfiguration().createSection("items")
        lootTable.forEachIndexed { index, lootItemData ->
            val section = itemsSection.createSection("item$index")
            lootItemData.serialize(section)
        }
        config.save()
    }
}
