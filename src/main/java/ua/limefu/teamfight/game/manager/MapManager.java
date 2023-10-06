package ua.limefu.teamfight.game.manager;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.material.Wool;
import ua.limefu.teamfight.game.GameState;
import ua.limefu.teamfight.TeamFight;
import ua.limefu.teamfight.game.arena.Arena;

public class MapManager {
    private boolean allBlocksMatch = true;
    private final TeamFight plugin = TeamFight.getInstance();

    public void resetTarget(Arena arena) {
        if (arena != null) {
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    for (int i = 1; i <= 9; i++) {
                        plugin.getFilemanager().getBlockLocation(arena, i).getBlock().setType(Material.WOOL);
                    }
                }
            }, 20);
        }
    }

    public void resetTeams(Arena arena) {
        if (arena != null) {

            for (Player currentPlayer : arena.getPlayers()) {
                if (arena.getTeamBlue().contains(currentPlayer) || arena.getTeamRed().contains(currentPlayer)) {
                    currentPlayer.setHealth(20);
                    currentPlayer.setFoodLevel(20);
                }
            }
            plugin.getPlayerUtil().teleportPlayersInBase(arena);
            arena.setGraceTime(30);
            plugin.setState(GameState.GRACE);
            Bukkit.getScheduler().cancelTask(plugin.getCountdown().getIngamecd());
            plugin.getCountdown().startGraceCD(arena);
        }

    }

    public void checkTargetForWinner(Arena arena) {
        if (arena != null) {
            Material referencedBlockMaterial = plugin.getFilemanager().getBlockLocation(arena, 1).getBlock().getType();

            for (int i = 2; i <= 9; i++) {
                if (plugin.getFilemanager().getBlockLocation(arena, i).getBlock().getType() != referencedBlockMaterial) {
                    allBlocksMatch = false;
                    break;
                }
            }

            if (allBlocksMatch) {
                if (plugin.getFilemanager().getBlockLocation(arena, 1).getBlock().getType() == Material.WOOL) {
                    BlockState state = plugin.getFilemanager().getBlockLocation(arena, 1).getBlock().getState();
                    Wool wool = (Wool) state.getData();
                    if (wool.getColor() == DyeColor.RED) {

                        arena.setScoreTeamRed(arena.getScoreTeamRed() + 1);
                        if (arena.getScoreTeamBlue() == 3) {
                            Bukkit.broadcastMessage(plugin.prefix + "§4Красные §7победили.");
                            plugin.getPlayerUtil().endGame(arena);
                            plugin.setState(GameState.LOBBY);
                            resetTarget(arena);
                        } else {
                            Bukkit.broadcastMessage(plugin.prefix + "§4Красные §7выиграли раунд и теперь имеют §e" + arena.getScoreTeamRed() + " §7Очков§7.");
                            resetTeams(arena);
                        }

                        resetTarget(arena);
                    } else if (wool.getColor() == DyeColor.BLUE) {
                        arena.setScoreTeamBlue(arena.getScoreTeamBlue() + 1);
                        if (arena.getScoreTeamBlue() == 3) {
                            Bukkit.broadcastMessage(plugin.prefix + "§bСиние §7победили!");
                            plugin.getPlayerUtil().endGame(arena);
                            plugin.setState(GameState.LOBBY);
                            resetTarget(arena);

                        } else {
                            Bukkit.broadcastMessage(plugin.prefix + "§bСиние §7выиграли раунд и теперь имеют §e" + arena.getScoreTeamBlue() + " §7Очков§7.");
                            resetTeams(arena);
                        }
                        resetTarget(arena);
                    }

                }
            }
        }
    }


    public boolean isInside(Arena arena, Location location) {
        if (arena != null) {
            int[] blockLocations = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
            for (int blockNumber : blockLocations) {
                Location blockLocation = plugin.getFilemanager().getBlockLocation(arena, blockNumber);
                if (location.getBlockX() == blockLocation.getBlockX()
                        && location.getBlockY() == blockLocation.getBlockY() && location.getBlockZ() == blockLocation.getBlockZ()
                ) {
                    return true;
                }
            }
        }
        return false;
    }
}
