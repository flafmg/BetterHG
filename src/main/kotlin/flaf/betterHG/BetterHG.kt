package flaf.betterHG

import flaf.betterHG.data.YamlConfig
import flaf.betterHG.data.arena.loot.LootData
import flaf.betterHG.listeners.LootEventListener
import flaf.betterHG.util.infoLog
import org.bukkit.plugin.java.JavaPlugin

class BetterHG : JavaPlugin() {

    override fun onEnable() {
        var lootConfig = YamlConfig("loots/example.yml", this);
        lootConfig.load();

        var loot = LootData(lootConfig);

        server.pluginManager.registerEvents(LootEventListener(loot), this);
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
