package net.ttdev.rinecore.command;

import net.ttdev.rinecore.player.RPlayer;
import net.ttdev.rinecore.util.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class BalanceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        final Player player = (Player) sender;

        if (args.length == 0) {

            final RPlayer rPlayer = new RPlayer(player.getUniqueId());
            player.sendMessage(ChatColor.GREEN + "Balance: " + rPlayer.getBalance());

        } else if (args[0].equalsIgnoreCase("add")) {

            if (!player.hasPermission(Permissions.ECO_GIVE)) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }

            final int amount = Integer.parseInt(args[1]);

            final RPlayer rPlayer = new RPlayer(player.getUniqueId());
            rPlayer.changeBalance(amount);

            player.sendMessage(ChatColor.GREEN + "Added " + amount + " to your balance.");
        }

        return true;
    }
}
