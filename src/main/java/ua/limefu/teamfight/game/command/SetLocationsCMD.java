package ua.limefu.teamfight.game.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.TeamFight;
import ua.limefu.teamfight.game.arena.Arena;
import ua.limefu.teamfight.utils.ArenaUtil;

public class SetLocationsCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(TeamFight.getInstance().NoConsoleAllowed);
        } else {
            Player player = (Player) sender;
            if (!player.hasPermission("teamfight.setup")) {
                player.sendMessage(TeamFight.getInstance().NoPermissions);
            } else {
                if (args.length == 2) {
                    String locationName = args[1].toLowerCase();
                    Arena name = ArenaUtil.get(args[0].toLowerCase());
                    if (name != null) {
                        if (locationName.equalsIgnoreCase("red") || locationName.equalsIgnoreCase("blue") || locationName.equalsIgnoreCase("spectator") || locationName.equalsIgnoreCase("lobby") || locationName.equalsIgnoreCase("red2") || locationName.equalsIgnoreCase("blue2")) {
                            TeamFight.getInstance().getFilemanager().saveLocation(name, locationName, player.getLocation(), player);
                            ;
                        } else {
                            player.sendMessage("§cИспользуй: §7/§csetlocation <arenaname> §7<§cRed§7/§cBlue§7/§cRed2§7/§cBlue2§7/§cSpectator§7/§cLobby§7>");
                        }
                    }
                } else {
                    player.sendMessage("§cИспользуй: §7/§csetlocation <arenaname> §7<§cRed§7/§cBlue§7/§cRed2§7/§cBlue2§7/§cSpectator§7/§cLobby§7>");
                }
            }
        }

        return false;
    }
}
