package ua.limefu.teamfight.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.TeamFight;

public class SetupBlockLocationsCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TeamFight.main.NoConsoleAllowed);
            return false;
        }
        else
        {
            Player player = (Player) sender;
            if (!player.hasPermission("teamfight.setup")) {
                player.sendMessage(TeamFight.main.NoPermissions);
                return false;
            }
            else
            {
                if(args.length != 1) {
                    player.sendMessage("§cИспользуй: §7/§csetupblocklocation §7<§c1-9§7>");
                    return false;
                }
                else
                {
                    int count = Integer.parseInt(args[0]);
                    if(count < 0 || count > 9){
                        player.sendMessage("§cИспользуй: §7/§csetupblocklocation §7<§c1-9§7>");
                    }else{
                        TeamFight.filemanager.saveBlockLocation(player.getLocation(), player, count);
                    }
                }
            }
        }
        return false;
    }
}
