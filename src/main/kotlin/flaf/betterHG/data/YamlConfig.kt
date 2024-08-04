package flaf.betterHG.data

import flaf.betterHG.util.errLog
import flaf.betterHG.util.infoLog
import flaf.betterHG.util.successLog
import flaf.betterHG.util.warnLog
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException

class YamlConfig(private val path: String, private val plugin: Plugin) {
    private lateinit var configFile: File;
    private lateinit var configuration: FileConfiguration;

    fun load(): Boolean{
        infoLog("Loading $path...");
        configFile = File(plugin.dataFolder, path);

        if(!configFile.exists()){
            warnLog("$path does not exist, creating a new one");
            if(plugin.getResource(path) != null) {
                plugin.saveResource(path, false);
            } else {
                warnLog("There is no resource for $path, creating blank file...")

                val parentDir = configFile.parentFile
                try {
                    if (!parentDir.mkdirs() && !parentDir.exists()) {
                        errLog("Failed to create directories for $path");
                        return false;
                    }

                    if(!configFile.createNewFile()){
                        errLog("Failed to create new file for $path");
                        return false;
                    }

                }catch (e: IOException){
                    e.printStackTrace()
                    return false;
                }
            }
        }
        configuration = YamlConfiguration.loadConfiguration(configFile);

        successLog("$path created successfully");
        return true;
    }

    fun <T> get(key: String, default: T? = null): T {
        return configuration.get(key) as T? ?: default ?: throw NoSuchElementException("no default value");
    }

    fun <T> set(key: String, value: T) {
        configuration.set(key, value);
    }

    fun save() {
        try {
            configuration.save(configFile);
        } catch (e: IOException) {
            errLog("Error saving $path:\n e.printStackTrace()");
        }
    }
}