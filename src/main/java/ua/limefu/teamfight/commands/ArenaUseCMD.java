package ua.limefu.teamfight.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.arena.ArenaGUI;

public class ArenaUseCMD implements CommandExecutor {
    private ArenaGUI arenaGUI;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Только игрок может использовать");
        } else arenaGUI.openArenaMenu((Player) sender);
        return false;
    }
}
