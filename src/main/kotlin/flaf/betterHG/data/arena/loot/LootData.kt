package flaf.betterHG.data.arena.loot

import flaf.betterHG.data.YamlConfig

class LootData(private val config: YamlConfig) {

    private val lootTable: MutableList<LootItemData> = mutableListOf()

    init {
        loadFromConfig()
    }

    private fun loadFromConfig() {
        for (key in config.getConfiguration().getKeys(false)) {
            val section = config.getConfigurationSection(key) ?: continue
            val lootItemData = LootItemData.deserialize(key, section)
            lootTable.add(lootItemData)
        }
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
        config.getConfiguration().getKeys(false).forEach { config.getConfiguration().set(it, null) }

        lootTable.forEachIndexed { index, lootItemData ->
            val section = config.getConfiguration().createSection("item$index")
            lootItemData.serialize(section)
        }
        config.save()
    }
}
