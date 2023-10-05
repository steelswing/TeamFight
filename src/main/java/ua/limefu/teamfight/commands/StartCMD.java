package ua.limefu.teamfight.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.TeamFight;
import ua.limefu.teamfight.arena.Arena;
import ua.limefu.teamfight.arena.ArenaList;

public class StartCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args)
    {
        if(!(sender instanceof Player))
        {
            sender.sendMessage(TeamFight.getInstance().NoConsoleAllowed);
            return false;
        } else {
            Player player = (Player)sender;
            Arena arena = ArenaList.getArenaForPlayer(player);
            if(!player.hasPermission("teamfight.command.start"))
            {
                player.sendMessage(TeamFight.getInstance().NoPermissions);
            } else {
                if (arena!=null) {
                    int onlinePlayers = arena.getPlayers().size();
                    if (onlinePlayers < arena.getMinimumPlayers()) {
                        player.sendMessage("§cНедостаточно игроков для старта игры!");
                    } else if (arena.getLobbyTime() <= 10) {
                        player.sendMessage("§cИгра скоро начнется и так.");
                    } else {
                        arena.setLobbyTime(10);
                        Bukkit.broadcastMessage(TeamFight.getInstance().prefix + "§7Игра сейчас начнется.");
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                    }
                }
            }
        }
        return false;
    }
}
