package by.thm.inventory;

import by.thm.AnimeServerSelector;
import by.thm.item.ItemCreateUtil;
import by.thm.listeners.BungeeMessageListener;
import by.thm.multiversion.XMaterial;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class SelectorInventory implements InventoryProvider {

    private String filterForServer;
    private Integer k = 0;

    public static final SmartInventory selector = SmartInventory.builder()
            .id("serverSelector")
            .provider(new SelectorInventory("none"))
            .size(6, 9)
            .title(ChatColor.translateAlternateColorCodes('&', AnimeServerSelector.cfg.getString("selector-gui-title")))
            .build();

    public static final SmartInventory selectorUS = SmartInventory.builder()
            .id("serverSelector")
            .provider(new SelectorInventory("us"))
            .size(6, 9)
            .title(ChatColor.translateAlternateColorCodes('&', AnimeServerSelector.cfg.getString("selector-gui-title")))
            .build();

    public static final SmartInventory selectorFR = SmartInventory.builder()
            .id("serverSelector")
            .provider(new SelectorInventory("fr"))
            .size(6, 9)
            .title(ChatColor.translateAlternateColorCodes('&', AnimeServerSelector.cfg.getString("selector-gui-title")))
            .build();

    public SelectorInventory(String filterForServer) {
        this.filterForServer = filterForServer;
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        for (int i = 0; i < 8; i++)
            contents.set(4, i, ClickableItem.empty(ItemCreateUtil.createItem(Material.BLACK_STAINED_GLASS_PANE, " ")));
        for (int i = 0; i < 6; i++)
            contents.set(i, 7, ClickableItem.empty(ItemCreateUtil.createItem(Material.BLACK_STAINED_GLASS_PANE, " ")));
        contents.set(2, 8, ClickableItem.empty(ItemCreateUtil.createItem(Material.BLACK_STAINED_GLASS_PANE, " ")));
        contents.set(3, 8, ClickableItem.of(ItemCreateUtil.createItem(XMaterial.EMERALD.parseMaterial(), ChatColor.translateAlternateColorCodes('&', AnimeServerSelector.cfg.getString("us-servers-name")), "", "§7Right-click on the §2Emerald §7to", "§7if you live near §2America§7 to filter", "§7already existing servers."), e -> selectorUS.open(player)));
        contents.set(4, 8, ClickableItem.of(ItemCreateUtil.createItem(XMaterial.DIAMOND.parseMaterial(), ChatColor.translateAlternateColorCodes('&', AnimeServerSelector.cfg.getString("fr-servers-name")), "", "§7Right-click on the §bDiamond §7to", "§7if you live near §bFrance§7 to filter", "§7already existing servers."), e -> selectorFR.open(player)));
        AtomicInteger i = new AtomicInteger();
        AnimeServerSelector.cfg.getConfigurationSection("servers").getKeys(false).forEach(str -> {
            switch (this.filterForServer) {
                case "us":
                    if (!AnimeServerSelector.cfg.getString("servers." + str + ".location").equalsIgnoreCase("US")) break;
                    int count1 = BungeeMessageListener.getServerCount(player, AnimeServerSelector.cfg.getString("servers." + str + ".bungee-redirect"));
                    ItemStack item1 = ItemCreateUtil.createItem(Material.GREEN_TERRACOTTA, "§a§l" + str, "§7Click to join!", "", "§2Players: §a" + count1  + "/50", "", "§2Lag: §a0%");
                    item1.setAmount(Integer.parseInt(i.toString()) + 1);
                    if (count1 >= 40) item1.setType(Material.RED_TERRACOTTA);
                    contents.set(i.get() /7, i.get() %7, ClickableItem.of(item1, e -> {
                        try {
                            BungeeMessageListener.teleportServer(player, AnimeServerSelector.cfg.getString("servers." + str + ".bungee-redirect"));
                        } catch (Exception ex) {
                            System.out.println("[FATAL] Attempted to send player " + player.getName() + " to server " + str + " but something went wrong. Probably the specified server does not exist.");
                        }
                    }));
                    i.getAndIncrement();
                    break;
                case "fr":
                    if (!AnimeServerSelector.cfg.getString("servers." + str + ".location").equalsIgnoreCase("FR")) break;
                    int count2 = BungeeMessageListener.getServerCount(player, AnimeServerSelector.cfg.getString("servers." + str + ".bungee-redirect"));
                    ItemStack item2 = ItemCreateUtil.createItem(Material.GREEN_TERRACOTTA, "§a§l" + str, "§7Click to join!", "", "§2Players: §a" + count2  + "/50", "", "§2Lag: §a0%");
                    item2.setAmount(Integer.parseInt(i.toString()) + 1);
                    if (count2 >= 40) item2.setType(Material.RED_TERRACOTTA);
                    contents.set(i.get() /7, i.get() %7, ClickableItem.of(item2, e -> {
                        try {
                            BungeeMessageListener.teleportServer(player, AnimeServerSelector.cfg.getString("servers." + str + ".bungee-redirect"));
                        } catch (Exception ex) {
                            System.out.println("[FATAL] Attempted to send player " + player.getName() + " to server " + str + " but something went wrong. Probably the specified server does not exist.");
                        }
                    }));
                    i.getAndIncrement();
                    break;
                case "none":
                default:
                    int count = BungeeMessageListener.getServerCount(player, AnimeServerSelector.cfg.getString("servers." + str + ".bungee-redirect"));
                    ItemStack item = ItemCreateUtil.createItem(Material.GREEN_TERRACOTTA, "§a§l" + str, "§7Click to join!", "", "§2Players: §a" + count  + "/50", "", "§2Lag: §a0%");
                    item.setAmount(Integer.parseInt(i.toString()) + 1);
                    if (count >= 40) item.setType(Material.RED_TERRACOTTA);
                    contents.set(i.get() /7, i.get() %7, ClickableItem.of(item, e -> {
                        try {
                            BungeeMessageListener.teleportServer(player, AnimeServerSelector.cfg.getString("servers." + str + ".bungee-redirect"));
                        } catch (Exception ex) {
                            System.out.println("[FATAL] Attempted to send player " + player.getName() + " to server " + str + " but something went wrong. Probably the specified server does not exist.");
                        }
                    }));
                    i.getAndIncrement();
                    break;
            }
        });
        i.set(0);
        AnimeServerSelector.cfg.getConfigurationSection("lobbies").getKeys(false).forEach(str -> {
            ItemStack lobby = ItemCreateUtil.createItem(XMaterial.CLOCK.parseMaterial(), "§e§l" + str, "§7§oClick to join!");
            lobby.setAmount(Integer.parseInt(i.toString()) + 1);
            contents.set(5, i.get(), ClickableItem.of(lobby, e -> {
                try {
                    BungeeMessageListener.teleportServer(player, AnimeServerSelector.cfg.getString("lobbies." + str + ".bungee-redirect"));
                } catch (Exception ex) {
                    System.out.println("[FATAL] Attempted to send player " + player.getName() + " to server " + str + " but something went wrong. Probably the specified server does not exist.");
                }
            }));
            i.getAndIncrement();
        });
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        if (k < 20) {
            k++;
            return;
        }
        k = 0;
        init(player, contents);
    }
}
