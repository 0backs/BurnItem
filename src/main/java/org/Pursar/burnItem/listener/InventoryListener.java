package org.Pursar.burnItem.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.Pursar.burnItem.BurnItem;
import org.Pursar.burnItem.manager.ConfigManager;
import org.Pursar.burnItem.manager.ItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    private final BurnItem plugin;
    private final ConfigManager configManager;
    private final ItemManager itemManager;

    public InventoryListener(BurnItem plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.itemManager = plugin.getItemManager();
    }

    @EventHandler(ignoreCancelled = true)
    private void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        ItemStack currentItem = event.getCurrentItem();
        if (currentItem == null || currentItem.getType().isAir()) {
            return;
        }

        ItemStack cursorItem = event.getCursor();
        if (cursorItem.getType().isAir() || !itemManager.isBook(cursorItem)) {
            return;
        }

        event.setCancelled(true);

        if (itemManager.isTool(currentItem)) {
            player.sendMessage(Component.text("이미 용광로 효과가 있는 도구입니다.", NamedTextColor.RED));
            return;
        }

        int amount = cursorItem.getAmount();
        if (amount == 1) {
            event.setCursor(null);
        } else {
            cursorItem.setAmount((amount - 1));
        }

        itemManager.setTool(currentItem);
        player.sendMessage(Component.text("용광로 효과를 적용 하였습니다.", NamedTextColor.GOLD));
    }
}
