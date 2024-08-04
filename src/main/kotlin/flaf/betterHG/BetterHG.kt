package flaf.betterHG

import flaf.betterHG.data.YamlConfig
import flaf.betterHG.util.infoLog
import org.bukkit.plugin.java.JavaPlugin

class BetterHG : JavaPlugin() {

    override fun onEnable() {
        var familyfriendlyvariavel = YamlConfig("config.yml", this);
        familyfriendlyvariavel.load();

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
