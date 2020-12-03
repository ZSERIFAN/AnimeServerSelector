package by.thm;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class AnimeServerSelector extends JavaPlugin {

    private static AnimeServerSelector instance;
    public static AnimeServerSelector getInstance() {
        return instance;
    }

    public static File config = new File("plugins/AnimeServerSelector/config.yml");
    public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(config);

    @Override
    public void onEnable() {
        instance = this;
        File dir = new File("plugins", "AnimeServerSelector");
        if (!dir.exists()) dir.mkdir();
        if (!config.exists()) saveDefaultConfig();
        try {
            cfg.load(config);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        Util.registerEvents();
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.INFO, "Disabling plugin...");
    }
}
