package org.Pursar.burnItem.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.Pursar.burnItem.BurnItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AdminCommand implements CommandExecutor {

    private final BurnItem plugin;

    public AdminCommand(BurnItem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (!(cs instanceof Player player)) {
            cs.sendMessage("플레이어만 입력이 가능합니다.");
            return false;
        }

        if (!player.hasPermission("burnitem.admin")) {
            player.sendMessage(Component.text("명령어를 사용할 권한이 없습니다.", NamedTextColor.RED));
            return false;
        }

        ItemStack tool = player.getInventory().getItemInMainHand();
        if (tool.getType().isAir()) {
            player.sendMessage(Component.text("설정할 아이템을 손에 들어주세요.", NamedTextColor.RED));
            return false;
        }

        plugin.getItemManager().setBook(tool);
        player.sendMessage(Component.text("손에 든 아이템을 사용 아이템으로 설정 하였습니다.", NamedTextColor.GOLD));
        return true;
    }
}
