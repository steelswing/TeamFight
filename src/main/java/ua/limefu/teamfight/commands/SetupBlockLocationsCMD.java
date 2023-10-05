package ua.limefu.teamfight.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.TeamFight;
import ua.limefu.teamfight.arena.Arena;
import ua.limefu.teamfight.arena.ArenaList;

public class SetupBlockLocationsCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TeamFight.getInstance().NoConsoleAllowed);
            return false;
        }
        else
        {
            Player player = (Player) sender;
            if (!player.hasPermission("teamfight.setup")) {
                player.sendMessage(TeamFight.getInstance().NoPermissions);
                return false;
            }
            else
            {
                if(args.length != 2) {
                    player.sendMessage("§cИспользуй: §7/§csetupblocklocation §7<§c1-9§7>");
                    return false;
                }
                else {
                    Arena name = ArenaList.get(args[0]);
                    if (name != null) {
                        int count = Integer.parseInt(args[1]);
                        if (count < 0 || count > 9) {
                            player.sendMessage("§cИспользуй: §7/§csetupblocklocation §7<§c1-9§7>");
                        } else {
                            TeamFight.getInstance().getFilemanager().saveBlockLocation(name, player.getLocation(), player, count);
                        }
                    }
                }
            }
        }
        return false;
    }
}
