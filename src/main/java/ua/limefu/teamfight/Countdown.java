package ua.limefu.teamfight;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Countdown {

    public boolean lobbystarted = false;
    public boolean restartstarted = false;

    String prefix = TeamFight.main.prefix;
    int minimumPlayers = TeamFight.main.minimumPlayers;

    public static int lobbycd;
    public int informcd;
    public int gracecd;
    public int ingamecd;
    int restartcd;

    @SuppressWarnings("deprecation")
    public void startPlayerLeftBroadcast(){
        informcd = Bukkit.getScheduler().scheduleAsyncRepeatingTask(TeamFight.main, new Runnable() {

            @Override
            public void run() {
                if(Bukkit.getOnlinePlayers().size() < minimumPlayers && Bukkit.getOnlinePlayers().size() > 0){
                    int playerNeeded = minimumPlayers-Bukkit.getOnlinePlayers().size();
                    if(playerNeeded == 1){
                        Bukkit.broadcastMessage(prefix + "§7Для того чтобы раунд начался, нужен еще 1 игрок в игре.");
                    }else{
                        Bukkit.broadcastMessage(prefix + "§7Для того чтобы раунд начался нужно еще §f" + playerNeeded + " §7игроков.");
                    }
                }
            }
        }, 0, 20*60);
    }

    public void startLobbyCD()
    {
        if(!lobbystarted)
        {
            lobbystarted = true;
            lobbycd = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) TeamFight.main, new Runnable()
            {

                @Override
                public void run()
                {
                    Bukkit.getScheduler().cancelTask(informcd);

                    if(TeamFight.main.lobbyTime == 60){
                        broadcastLobbyTime();
                        setPlayerLevel();
                    }else if(TeamFight.main.lobbyTime <= 59 && TeamFight.main.lobbyTime >= 31){
                        setPlayerLevel();
                    }else if(TeamFight.main.lobbyTime == 30){
                        broadcastLobbyTime();
                        setPlayerLevel();
                    }else if(TeamFight.main.lobbyTime <= 29 && TeamFight.main.lobbyTime >= 6){
                        setPlayerLevel();
                    }else if(TeamFight.main.lobbyTime <= 5 && TeamFight.main.lobbyTime > 1){
                        broadcastLobbyTime();
                        setPlayerLevel();
                    }else if(TeamFight.main.lobbyTime == 1){
                        Bukkit.broadcastMessage(prefix + "§7Игра начинается через секунду");
                        setPlayerLevel();
                    }else if(TeamFight.main.lobbyTime == 0){
                        Bukkit.broadcastMessage(prefix + "§7Игра начинается.");
                        setPlayerLevel();

                        for(Player currentPlayer:Bukkit.getOnlinePlayers()){
                            TeamFight.playerUtil.pushPlayersInTeam(currentPlayer);
                        }
                        TeamFight.playerUtil.teleportPlayersInBase();

                        TeamFight.main.state = GameState.GRACE;
                        startGraceCD();
                        Bukkit.getScheduler().cancelTask(lobbycd);
                    }
                    TeamFight.main.lobbyTime--;
                }
            }, 0, 20L);

        }
    }


    public void startGraceCD(){

        gracecd = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) TeamFight.main, new Runnable() {

            @Override
            public void run() {
                if(TeamFight.main.graceTime == 30){
                    if(!TeamFight.main.gameInProgress){
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage(prefix + "§7Поменяйте цвет шерсти в центре карты чтобы победить.");
                        Bukkit.broadcastMessage(prefix + "§7Для победы нужно 3 очка!");
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("");
                        TeamFight.main.gameInProgress = true;
                    }
                    for(Player currentPlayer:Bukkit.getOnlinePlayers()){
                        if(TeamFight.main.TeamBlue.contains(currentPlayer) || TeamFight.main.TeamRed.contains(currentPlayer)){
                            TeamFight.itemUtil.giveIngameItems(currentPlayer);
                        }
                    }
                    setPlayerLevel();
                }else if(TeamFight.main.graceTime <= 30 && TeamFight.main.graceTime >= 16){
                    setPlayerLevel();
                }else if(TeamFight.main.graceTime == 15){

                    TeamFight.playerUtil.teleportPlayersInStartPosition();
                    setPlayerLevel();

                }else if(TeamFight.main.graceTime <= 14 && TeamFight.main.graceTime >= 4){
                    setPlayerLevel();
                }else if(TeamFight.main.graceTime == 3){
                    setPlayerLevel();
                    for(Player currentPlayer:Bukkit.getOnlinePlayers()){
                        currentPlayer.sendTitle("§c§l»3«", null);
                    }
                }else if(TeamFight.main.graceTime == 2){
                    setPlayerLevel();
                    for(Player currentPlayer:Bukkit.getOnlinePlayers()){
                        currentPlayer.sendTitle("§e§l»2«", null);
                    }
                }else if(TeamFight.main.graceTime == 1){
                    setPlayerLevel();
                    for(Player currentPlayer:Bukkit.getOnlinePlayers()){
                        currentPlayer.sendTitle("§a§l»1«", null);
                    }
                }else if(TeamFight.main.graceTime == 0){
                    setPlayerLevel();
                    for(Player currentPlayer:Bukkit.getOnlinePlayers()){
                        currentPlayer.sendTitle("§b§lВперед!", null);
                    }
                    TeamFight.main.state = GameState.INGAME;
                    Bukkit.getScheduler().cancelTask(gracecd);
                }
                TeamFight.main.graceTime--;
            }
        }, 0, 20L);
    }


    public void setPlayerLevel(){
        if(TeamFight.main.state == GameState.LOBBY){
            for(Player currentPlayer:Bukkit.getOnlinePlayers()){
                currentPlayer.setLevel(TeamFight.main.lobbyTime);
            }
        }else if(TeamFight.main.state == GameState.GRACE)
            for(Player currentPlayer:Bukkit.getOnlinePlayers()){
                currentPlayer.setLevel(TeamFight.main.graceTime);
            }
    }

    public void broadcastLobbyTime(){
        Bukkit.broadcastMessage(prefix + "§7До начала игры осталось §f" + TeamFight.main.lobbyTime + " §7секунд.");
    }
}
