package ua.limefu.teamfight;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import ua.limefu.teamfight.game.Countdown;
import ua.limefu.teamfight.game.GameState;
import ua.limefu.teamfight.game.Listeners;
import ua.limefu.teamfight.utils.ArenaUtil;
import ua.limefu.teamfight.game.arena.ArenaListener;
import ua.limefu.teamfight.game.command.*;
import ua.limefu.teamfight.game.manager.FileManager;
import ua.limefu.teamfight.game.manager.ItemManager;
import ua.limefu.teamfight.game.manager.MapManager;
import ua.limefu.teamfight.game.manager.PlayerManager;


public final class TeamFight extends JavaPlugin {

    @Getter
    private Countdown countdown;

    public final String prefix = "§6TeamFight §8┃ ";
    public final String NoPermissions = "§cУ вас нету прав!";
    public final String NoConsoleAllowed = "§cТолько игрок может использовать команду!";

    @Getter
    private static TeamFight instance;
    @Getter
    private FileManager filemanager;
    @Getter
    @Setter
    private GameState state;


    @Getter
    private MapManager maputil;
    @Getter
    private ItemManager itemUtil;
    @Getter
    private PlayerManager playerUtil;


    @Override
    public void onEnable() {

        instance = this;
        ArenaUtil.putAllArenas();
        countdown = new Countdown();
        itemUtil = new ItemManager();
        maputil = new MapManager();
        playerUtil = new PlayerManager();
        filemanager = new FileManager();



        getCommand("start").setExecutor(new StartCMD());
        getCommand("setupblocklocation").setExecutor(new SetupBlockLocationsCMD());
        getCommand("setlocation").setExecutor(new SetLocationsCMD());
        getCommand("createarena").setExecutor(new CreateArenaCMD());
        getCommand("arena").setExecutor(new ArenaUseCMD());

        Bukkit.getServer().getPluginManager().registerEvents(new ArenaListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Listeners(), this);
    }


}
