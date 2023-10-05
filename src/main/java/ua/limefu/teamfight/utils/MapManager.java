package ua.limefu.teamfight.utils;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.material.Wool;
import ua.limefu.teamfight.GameState;
import ua.limefu.teamfight.TeamFight;

public class MapManager {
    public void resetTarget(){
        Bukkit.getScheduler().runTaskLater(TeamFight.main, new Runnable() {
            @Override
            public void run() {
                for(int i = 1; i<=9; i++){
                    TeamFight.filemanager.getBlockLocation(i).getBlock().setType(Material.WOOL);
                }
            }
        }, 20);
    }

    public void resetTeams(){

        for(Player currentPlayer: Bukkit.getOnlinePlayers()){
            if(TeamFight.main.TeamBlue.contains(currentPlayer) || TeamFight.main.TeamRed.contains(currentPlayer)){
                currentPlayer.setHealth(20);
                currentPlayer.setFoodLevel(20);
            }
        }
        TeamFight.playerUtil.teleportPlayersInBase();
        TeamFight.main.graceTime = 30;
        TeamFight.main.state = GameState.GRACE;
        Bukkit.getScheduler().cancelTask(TeamFight.countdown.ingamecd);
        TeamFight.countdown.startGraceCD();

    }

    public void checkTargetForWinner(){
        if(TeamFight.filemanager.getBlockLocation(1).getBlock().getType() == TeamFight.filemanager.getBlockLocation(2).getBlock().getType()
                && TeamFight.filemanager.getBlockLocation(1).getBlock().getType() == TeamFight.filemanager.getBlockLocation(3).getBlock().getType()
                && TeamFight.filemanager.getBlockLocation(1).getBlock().getType() == TeamFight.filemanager.getBlockLocation(4).getBlock().getType()
                && TeamFight.filemanager.getBlockLocation(1).getBlock().getType() == TeamFight.filemanager.getBlockLocation(5).getBlock().getType()
                && TeamFight.filemanager.getBlockLocation(1).getBlock().getType() == TeamFight.filemanager.getBlockLocation(6).getBlock().getType()
                && TeamFight.filemanager.getBlockLocation(1).getBlock().getType() == TeamFight.filemanager.getBlockLocation(7).getBlock().getType()
                && TeamFight.filemanager.getBlockLocation(1).getBlock().getType() == TeamFight.filemanager.getBlockLocation(8).getBlock().getType()
                && TeamFight.filemanager.getBlockLocation(1).getBlock().getType() == TeamFight.filemanager.getBlockLocation(9).getBlock().getType()){
            if(TeamFight.filemanager.getBlockLocation(1).getBlock().getType() == Material.WOOL){
                BlockState state = TeamFight.filemanager.getBlockLocation(1).getBlock().getState();
                Wool wool = (Wool) state.getData();
                if (wool.getColor() == DyeColor.RED) {

                    TeamFight.main.scoreTeamRed++;
                    if (TeamFight.main.scoreTeamRed == 3) {
                        Bukkit.broadcastMessage(TeamFight.main.prefix + "§4Красные §7победили.");
                    } else {
                        Bukkit.broadcastMessage(TeamFight.main.prefix + "§4Красные §7выиграли раунд и теперь имеют §e" + TeamFight.main.scoreTeamRed + " §7Очков§7.");
                        resetTeams();
                    }

                    resetTarget();
                }
            }else if(TeamFight.filemanager.getBlockLocation(1).getBlock().getType() == Material.WOOL){
                BlockState state = TeamFight.filemanager.getBlockLocation(1).getBlock().getState();
                Wool wool = (Wool) state.getData();
                if (wool.getColor() == DyeColor.BLUE) {
                    TeamFight.main.scoreTeamBlue++;
                    if (TeamFight.main.scoreTeamBlue == 3) {
                        Bukkit.broadcastMessage(TeamFight.main.prefix + "§bСиние §7победили!");
                    } else {
                        Bukkit.broadcastMessage(TeamFight.main.prefix + "§bСиние §7выиграли раунд и теперь имеют §e" + TeamFight.main.scoreTeamBlue + " §7Очков§7.");
                        resetTeams();
                    }

                    resetTarget();
                }
            }else{
                return;
            }
        }
    }

    public boolean isInside(Location location){

        if(location.getBlockX() == TeamFight.filemanager.getBlockLocation(1).getBlockX() & location.getBlockY() == TeamFight.filemanager.getBlockLocation(1).getBlockY() & location.getBlockZ() == TeamFight.filemanager.getBlockLocation(1).getBlockZ()
                || location.getBlockX() == TeamFight.filemanager.getBlockLocation(2).getBlockX() & location.getBlockY() == TeamFight.filemanager.getBlockLocation(2).getBlockY() & location.getBlockZ() == TeamFight.filemanager.getBlockLocation(2).getBlockZ()
                || location.getBlockX() == TeamFight.filemanager.getBlockLocation(3).getBlockX() & location.getBlockY() == TeamFight.filemanager.getBlockLocation(3).getBlockY() & location.getBlockZ() == TeamFight.filemanager.getBlockLocation(3).getBlockZ()
                || location.getBlockX() == TeamFight.filemanager.getBlockLocation(4).getBlockX() & location.getBlockY() == TeamFight.filemanager.getBlockLocation(4).getBlockY() & location.getBlockZ() == TeamFight.filemanager.getBlockLocation(4).getBlockZ()
                || location.getBlockX() == TeamFight.filemanager.getBlockLocation(5).getBlockX() & location.getBlockY() == TeamFight.filemanager.getBlockLocation(5).getBlockY() & location.getBlockZ() == TeamFight.filemanager.getBlockLocation(5).getBlockZ()
                || location.getBlockX() == TeamFight.filemanager.getBlockLocation(6).getBlockX() & location.getBlockY() == TeamFight.filemanager.getBlockLocation(6).getBlockY() & location.getBlockZ() == TeamFight.filemanager.getBlockLocation(6).getBlockZ()
                || location.getBlockX() == TeamFight.filemanager.getBlockLocation(7).getBlockX() & location.getBlockY() == TeamFight.filemanager.getBlockLocation(7).getBlockY() & location.getBlockZ() == TeamFight.filemanager.getBlockLocation(7).getBlockZ()
                || location.getBlockX() == TeamFight.filemanager.getBlockLocation(8).getBlockX() & location.getBlockY() == TeamFight.filemanager.getBlockLocation(8).getBlockY() & location.getBlockZ() == TeamFight.filemanager.getBlockLocation(8).getBlockZ()
                || location.getBlockX() == TeamFight.filemanager.getBlockLocation(9).getBlockX() & location.getBlockY() == TeamFight.filemanager.getBlockLocation(9).getBlockY() & location.getBlockZ() == TeamFight.filemanager.getBlockLocation(9).getBlockZ()){

            return true;
        }
        else
        {
            return false;
        }
    }
}
