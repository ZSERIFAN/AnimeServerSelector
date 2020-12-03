package by.thm.listeners;

import by.thm.AnimeServerSelector;
import by.thm.inventory.SelectorInventory;
import by.thm.item.ItemCreateUtil;
import by.thm.multiversion.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class SelectorListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.getInventory().clear();
        p.getInventory().setItem(0, ItemCreateUtil.createItem(XMaterial.COMPASS.parseMaterial(), ChatColor.translateAlternateColorCodes('&', AnimeServerSelector.cfg.getString("selector-name"))));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.isCancelled()) return;
        final ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == XMaterial.AIR.parseMaterial()) return;
        if (!clickedItem.hasItemMeta()) return;
        if (clickedItem.getType() == XMaterial.COMPASS.parseMaterial()) e.setCancelled(true);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (p.getInventory().getItemInMainHand().getType() == XMaterial.AIR.parseMaterial() || p.getInventory().getItemInMainHand().getType() == null) return;
        if (!p.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) return;
        switch (p.getInventory().getItemInMainHand().getType()) {
            case COMPASS:
                SelectorInventory.selector.open(p);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (e.getItemDrop().getItemStack().getType() == XMaterial.COMPASS.parseMaterial()) e.setCancelled(true);
    }
}
