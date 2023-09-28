package ua.limefu.teamfight.game.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.util.ChatUtil;
import ua.limefu.teamfight.game.Game;
import ua.limefu.teamfight.game.Team;

import java.util.ArrayList;
import java.util.List;

public class SpawnTeamCMD implements CommandExecutor {
    private Team team;
    private Game game;

    private static List<Location> spawnTeam = new ArrayList<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("teamfight.setspawn")) {
            if (args[1].equalsIgnoreCase("blue")) {
                team.getSpawn().get(0).add(player.getLocation());
                ChatUtil.sendMessage(player, "Вы установили спавн для синих игроков!");
            }
            if (args[1].equalsIgnoreCase("red")) {
                team.getSpawn().get(1).add(player.getLocation());
                ChatUtil.sendMessage(player, "Вы установили спавн для красных игроков!");
            }
        }
        return false;
    }

    public static void setSpawnTeam(List<Location> spawnTeam) {
        SpawnTeamCMD.spawnTeam = spawnTeam;
    }

    public static List<Location> getSpawnTeam() {
        return spawnTeam;
    }
}
