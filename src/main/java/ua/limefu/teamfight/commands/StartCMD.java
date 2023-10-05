package ua.limefu.teamfight.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.TeamFight;

public class StartCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args)
    {
        if(!(sender instanceof Player))
        {
            sender.sendMessage(TeamFight.main.NoConsoleAllowed);
            return false;
        }
        else
        {
            Player player = (Player)sender;
            if(!player.hasPermission("teamfight.command.start"))
            {
                player.sendMessage(TeamFight.main.NoPermissions);
            }
            else
            {
                int onlinePlayers = Bukkit.getOnlinePlayers().size();
                if(onlinePlayers < TeamFight.main.minimumPlayers)
                {
                    player.sendMessage("§cНедостаточно игроков для старта игры!");
                }
                else if(TeamFight.main.lobbyTime <= 10)
                {
                    player.sendMessage("§cИгра скоро начнется и так.");
                }
                else
                {
                    TeamFight.main.lobbyTime = 10;
                    Bukkit.broadcastMessage(TeamFight.main.prefix + "§7Игра сейчас начнется.");
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                }
            }
        }
        return false;
    }
}
