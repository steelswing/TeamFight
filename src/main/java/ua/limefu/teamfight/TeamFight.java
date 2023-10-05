package ua.limefu.teamfight;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;

import ua.limefu.teamfight.commands.SetLocationsCMD;
import ua.limefu.teamfight.commands.SetupBlockLocationsCMD;
import ua.limefu.teamfight.commands.StartCMD;
import ua.limefu.teamfight.utils.ItemManager;
import ua.limefu.teamfight.utils.MapManager;
import ua.limefu.teamfight.utils.PlayerManager;

import java.util.ArrayList;

public final class TeamFight extends JavaPlugin {

    public static Countdown countdown;

    public String prefix = "§6TeamFight §8┃ ";
    public String NoPermissions = "§cУ вас нету прав!";
    public String NoConsoleAllowed = "§cТолько игрок может использовать команду!";

    public int minimumPlayers = 2;
    public int maximumPlayers = 8;
    public int lobbyTime;
    public int graceTime;

    public int scoreTeamBlue;
    public int scoreTeamRed;

    public static TeamFight main;
    public static FileManager filemanager;
    public GameState state;

    public boolean gameInProgress;

    public static MapManager maputil;
    public static ItemManager itemUtil;
    public static PlayerManager playerUtil;

    public ArrayList<Player> TeamBlue = new ArrayList<Player>();
    public ArrayList<Player> TeamRed = new ArrayList<Player>();

    @Override
    public void onEnable()
    {
        scoreTeamRed = 0;
        scoreTeamBlue = 0;

        main = this;
        countdown = new Countdown();
        itemUtil = new ItemManager();
        maputil = new MapManager();
        playerUtil = new PlayerManager();
        filemanager = new FileManager();

        gameInProgress = false;

        state = GameState.LOBBY;

        lobbyTime = 60;
        graceTime = 30;

        countdown.startPlayerLeftBroadcast();

        getCommand("start").setExecutor(new StartCMD());
        getCommand("setupblocklocation").setExecutor(new SetupBlockLocationsCMD());
        getCommand("setlocation").setExecutor(new SetLocationsCMD());

        Bukkit.getServer().getPluginManager().registerEvents(new Listeners(), this);
    }




}
