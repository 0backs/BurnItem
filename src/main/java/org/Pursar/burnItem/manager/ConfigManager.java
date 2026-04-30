package org.Pursar.burnItem.manager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.Pursar.burnItem.BurnItem;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.builder()
            .character('&')
            .hexColors()
            .build();

    private final BurnItem plugin;
    private final FileConfiguration config;

    private boolean status;
    private List<Component> lores;

    public ConfigManager(BurnItem plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();

        loadConfig();
    }

    private void loadConfig() {
        this.status = config.getBoolean("lore.enable", true);

        lores = new ArrayList<>();

        List<String> configs = config.getStringList("lore.symbols");
        for (String line : configs) {
            lores.add(SERIALIZER.deserialize(line).decoration(TextDecoration.ITALIC, false));
        }
    }

    public boolean isStatus() {
        return status;
    }

    public List<Component> getLores() {
        return lores;
    }
}
