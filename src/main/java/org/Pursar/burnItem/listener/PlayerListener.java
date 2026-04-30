package org.Pursar.burnItem.listener;

import org.Pursar.burnItem.BurnItem;
import org.Pursar.burnItem.manager.ItemManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Set;

public class PlayerListener implements Listener {

    private static final Set<Material> ORES = Set.of(
            Material.COPPER_ORE, Material.IRON_ORE, Material.GOLD_ORE,
            Material.DEEPSLATE_COPPER_ORE, Material.DEEPSLATE_IRON_ORE, Material.DEEPSLATE_GOLD_ORE
    );

    private final BurnItem plugin;
    private final ItemManager itemManager;

    public PlayerListener(BurnItem plugin) {
        this.plugin = plugin;
        this.itemManager = plugin.getItemManager();
    }

    @EventHandler(ignoreCancelled = true)
    private void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material type = block.getType();
        Location loc = block.getLocation();

        if (!ORES.contains(type)) {
            return;
        }

        ItemStack tool = player.getInventory().getItemInMainHand();
        if (tool.getType().isAir() || !itemManager.isTool(tool)) {
            return;
        }

        event.setDropItems(false);

        Collection<ItemStack> drops = block.getDrops(tool);
        for (ItemStack drop : drops) {
            Material dropType = drop.getType();
            if (dropType == Material.RAW_COPPER) {
                drop.setType(Material.COPPER_INGOT);
            } else if (dropType == Material.RAW_IRON) {
                drop.setType(Material.IRON_INGOT);
            } else if (dropType == Material.RAW_GOLD) {
                drop.setType(Material.GOLD_INGOT);
            }

            loc.getWorld().dropItemNaturally(loc, drop);
        }
    }
}
