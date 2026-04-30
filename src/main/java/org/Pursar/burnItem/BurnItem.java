package org.Pursar.burnItem;

import org.Pursar.burnItem.command.AdminCommand;
import org.Pursar.burnItem.listener.InventoryListener;
import org.Pursar.burnItem.listener.PlayerListener;
import org.Pursar.burnItem.manager.ConfigManager;
import org.Pursar.burnItem.manager.ItemManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BurnItem extends JavaPlugin {

    private static BurnItem instance;

    private ConfigManager configManager;
    private ItemManager itemManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        instance = this;
        configManager = new ConfigManager(this);
        itemManager = new ItemManager(this);

        getCommand("용광로아이템설정").setExecutor(new AdminCommand(this));

        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        getLogger().info("플러그인이 활성화 되었습니다.");
    }

    @Override
    public void onDisable() {
        getLogger().info("플러그인이 비활성화 되었습니다.");
    }

    public static BurnItem getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }
}
