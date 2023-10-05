package ua.limefu.teamfight.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.FileManager;
import ua.limefu.teamfight.TeamFight;
import ua.limefu.teamfight.arena.Arena;
import ua.limefu.teamfight.arena.ArenaList;

public class CreateArenaCMD implements CommandExecutor {
    private FileManager fileManager;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                String name = args[0].toLowerCase();
                Arena arena = new Arena(name);
                ArenaList.getArenas().add(arena);
                sender.sendMessage("Арена создана");
                TeamFight.getInstance().getConfig().set("arena.", arena.getName());

            } else sender.sendMessage("Используй команду /createarena <name>");

        }
        return false;
    }
}
