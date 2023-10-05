package ua.limefu.teamfight.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.TeamFight;

public class SetLocationsCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {

        if(!(sender instanceof Player))
        {
            sender.sendMessage(TeamFight.main.NoConsoleAllowed);
        }
        else
        {
            Player player = (Player)sender;
            if(!player.hasPermission("teamfight.setup"))
            {
                player.sendMessage(TeamFight.main.NoPermissions);
            }
            else
            {
                if(args.length == 1)
                {
                    String locationName = args[0].toLowerCase();
                    if(locationName.equalsIgnoreCase("red") || locationName.equalsIgnoreCase("blue") || locationName.equalsIgnoreCase("spectator") || locationName.equalsIgnoreCase("lobby") || locationName.equalsIgnoreCase("red2") ||locationName.equalsIgnoreCase("blue2"))
                    {
                        TeamFight.filemanager.saveLocation(player.getLocation(), locationName, player);;
                    }
                    else
                    {
                        player.sendMessage("§cИспользуй: §7/§csetlocation §7<§cRed§7/§cBlue§7/§cRed2§7/§cBlue2§7/§cSpectator§7/§cLobby§7>");
                    }
                }
                else
                {
                    player.sendMessage("§cИспользуй: §7/§csetlocation §7<§cRed§7/§cBlue§7/§cRed2§7/§cBlue2§7/§cSpectator§7/§cLobby§7>");
                }
            }
        }

        return false;
    }
}
