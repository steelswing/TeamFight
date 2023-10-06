package ua.limefu.teamfight.game.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.TeamFight;
import ua.limefu.teamfight.game.arena.Arena;
import ua.limefu.teamfight.utils.ArenaUtil;


import java.util.Random;

public class PlayerManager {

    private final TeamFight plugin = TeamFight.getInstance();

    public void pushPlayersInTeam(Player player) {
        Arena arena = ArenaUtil.getArenaForPlayer(player);
        if (arena != null) {
            if (arena.getTeamBlue().size() == 0 || arena.getTeamRed().size() == 0) {
                arena.getTeamBlue().clear();
                arena.getTeamRed().clear();
            }

            for (Player currentPlayer : Bukkit.getOnlinePlayers()) {

                if (!arena.getTeamBlue().contains(currentPlayer) && !arena.getTeamRed().contains(currentPlayer)) {
                    if (arena.getTeamBlue().size() < arena.getTeamRed().size()) {
                        arena.getTeamBlue().add(currentPlayer);
                    } else if (arena.getTeamRed().size() < arena.getTeamBlue().size()) {
                        arena.getTeamRed().add(currentPlayer);
                    } else {
                        Random random = new Random();
                        int randomInt = random.nextInt(100);

                        if (randomInt <= 49) {
                            arena.getTeamBlue().add(currentPlayer);
                        } else {
                            arena.getTeamRed().add(currentPlayer);
                        }
                    }
                }
            }
        }
    }


    public void teleportPlayersInBase(Arena name) {
        if (name != null) {


            for (int i = 0; i < name.getTeamBlue().size(); i++) {
                Player targetPlayer = name.getTeamBlue().get(i);
                targetPlayer.teleport(plugin.getFilemanager().getLocation(name, "blue"));
            }

            for (int i = 0; i < name.getTeamRed().size(); i++) {
                Player targetPlayer = name.getTeamRed().get(i);
                targetPlayer.teleport(plugin.getFilemanager().getLocation(name, "red"));
            }
        }
    }


    public void teleportPlayersInStartPosition(Arena arena) {
        if (arena != null) {
            for (int i = 0; i < arena.getTeamBlue().size(); i++) {
                Player targetPlayer = arena.getTeamBlue().get(i);
                targetPlayer.teleport(plugin.getFilemanager().getLocation(arena, "blue2"));
            }

            for (int i = 0; i < arena.getTeamRed().size(); i++) {
                Player targetPlayer = arena.getTeamRed().get(i);
                targetPlayer.teleport(plugin.getFilemanager().getLocation(arena, "red2"));
            }
        }
    }

    public void endGame(Arena arena) {
        if (arena != null) {
            arena.setScoreTeamBlue(0);
            arena.setScoreTeamRed(0);
            for (int i = 0; i < arena.getPlayers().size(); i++) {
                Player targetPlayer = arena.getTeamBlue().get(i);
                targetPlayer.teleport(plugin.getFilemanager().getLocation(arena, "lobby"));
            }

            arena.getTeamRed().clear();
            arena.getTeamBlue().clear();
        }

    }
}
