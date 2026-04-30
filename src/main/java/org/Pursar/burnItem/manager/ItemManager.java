package org.Pursar.burnItem.manager;

import net.kyori.adventure.text.Component;
import org.Pursar.burnItem.BurnItem;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    private final BurnItem plugin;
    private final ConfigManager configManager;
    private final NamespacedKey key1;
    private final NamespacedKey key2;

    public ItemManager(BurnItem plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.key1 = new NamespacedKey(plugin, "burnitem-tool");
        this.key2 = new NamespacedKey(plugin, "burnitem-book");
    }

    public boolean isTool(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }

        return meta.getPersistentDataContainer().getOrDefault(key1, PersistentDataType.BOOLEAN, false);
    }

    public void setTool(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }

        meta.getPersistentDataContainer().set(key1, PersistentDataType.BOOLEAN, true);

        if (configManager.isStatus()) {
            List<Component> lores = (meta.lore() != null) ? meta.lore() : new ArrayList<>();
            lores.addAll(configManager.getLores());
            meta.lore(lores);
        }

        item.setItemMeta(meta);
    }

    public boolean isBook(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }

        return meta.getPersistentDataContainer().getOrDefault(key2, PersistentDataType.BOOLEAN, false);
    }

    public void setBook(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }

        meta.getPersistentDataContainer().set(key2, PersistentDataType.BOOLEAN, true);
        item.setItemMeta(meta);
    }
}
