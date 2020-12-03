package by.thm;

import by.thm.listeners.BungeeMessageListener;
import by.thm.listeners.SelectorListener;
import org.bukkit.Bukkit;

public class Util {

    public static void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new SelectorListener(), AnimeServerSelector.getInstance());
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(AnimeServerSelector.getInstance(), "BungeeCord");
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(AnimeServerSelector.getInstance(), "BungeeCord", new BungeeMessageListener());
    }
}
