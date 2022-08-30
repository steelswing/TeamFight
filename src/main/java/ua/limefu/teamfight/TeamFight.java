package ua.limefu.teamfight;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ua.limefu.teamfight.game.Round;
import ua.limefu.teamfight.game.command.SpawnTeamCMD;

public final class TeamFight extends JavaPlugin {

    private static TeamFight instance;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Round(), this);
        getCommand("setspawnteam").setExecutor(new SpawnTeamCMD());
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static TeamFight getInstance() {
        return instance;
    }
}
