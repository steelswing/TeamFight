package ua.limefu.teamfight.utils;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.TeamFight;
import ua.limefu.teamfight.game.arena.Arena;

import java.util.ArrayList;
import java.util.List;

public class ArenaUtil {
    @Getter
    private static final List<Arena> arenas = new ArrayList<>();

    public static Arena get(String name) {
        for (Arena arena : arenas) {
            if (arena.getName().equals(name)) {
                return arena;
            }
        }
        return null;
    }

    public static Arena getArenaForPlayer(Player player) {
        for (Arena arena : arenas) {
            if (arena.getPlayers().contains(player)) {
                return arena;
            }
        }
        return null;
    }

    public static void putAllArenas() {
        if (TeamFight.getInstance().getConfig().isConfigurationSection("arena")) {
            ConfigurationSection configurationSection = TeamFight.getInstance().getConfig().getConfigurationSection("arena");
            getArenas().add(get(configurationSection.getKeys(false).toString()));
        }
    }


}
