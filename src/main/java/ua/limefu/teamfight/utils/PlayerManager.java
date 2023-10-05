package ua.limefu.teamfight.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.TeamFight;

import java.util.Random;

public class PlayerManager {
    public void pushPlayersInTeam(Player player){
        if(TeamFight.main.TeamBlue.size() == 0 || TeamFight.main.TeamRed.size() == 0){
            TeamFight.main.TeamBlue.clear();
            TeamFight.main.TeamRed.clear();
        }

        for(Player currentPlayer: Bukkit.getOnlinePlayers()){

            if(!TeamFight.main.TeamBlue.contains(currentPlayer) && !TeamFight.main.TeamRed.contains(currentPlayer)){
                if(TeamFight.main.TeamBlue.size() < TeamFight.main.TeamRed.size()){
                    TeamFight.main.TeamBlue.add(currentPlayer);
                }else if(TeamFight.main.TeamRed.size() < TeamFight.main.TeamBlue.size()){
                    TeamFight.main.TeamRed.add(currentPlayer);
                }else{
                    Random random = new Random();
                    int randomInt = random.nextInt(100);

                    if(randomInt <= 49){
                        TeamFight.main.TeamBlue.add(currentPlayer);
                    }else{
                        TeamFight.main.TeamRed.add(currentPlayer);
                    }
                }
            }
        }
    }


    public void teleportPlayersInBase(){
        for(int i = 0; i < TeamFight.main.TeamBlue.size(); i++){
            Player targetPlayer = TeamFight.main.TeamBlue.get(i);
            targetPlayer.teleport(TeamFight.filemanager.getLocation("blue"));
        }

        for(int i = 0; i < TeamFight.main.TeamRed.size(); i++){
            Player targetPlayer = TeamFight.main.TeamRed.get(i);
            targetPlayer.teleport(TeamFight.filemanager.getLocation("red"));
        }
    }


    public void teleportPlayersInStartPosition(){
        for(int i = 0; i < TeamFight.main.TeamBlue.size(); i++){
            Player targetPlayer = TeamFight.main.TeamBlue.get(i);
            targetPlayer.teleport(TeamFight.filemanager.getLocation("blue2"));
        }

        for(int i = 0; i < TeamFight.main.TeamRed.size(); i++){
            Player targetPlayer = TeamFight.main.TeamRed.get(i);
            targetPlayer.teleport(TeamFight.filemanager.getLocation("red2"));
        }
    }

    public void endGame() {
        TeamFight.main.scoreTeamBlue = 0;
        TeamFight.main.scoreTeamRed = 0;
        for(int i = 0; i < Bukkit.getOnlinePlayers().size(); i++){
            Player targetPlayer = TeamFight.main.TeamBlue.get(i);
            targetPlayer.teleport(TeamFight.filemanager.getLocation("lobby"));
        }

        TeamFight.main.TeamRed.clear();;
        TeamFight.main.TeamBlue.clear();

    }
}
