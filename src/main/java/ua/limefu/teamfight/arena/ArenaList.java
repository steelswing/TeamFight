package ua.limefu.teamfight.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.TeamFight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArenaList {
    @Getter @Setter
    private static List<Arena> arenas = new ArrayList<>();

    public static Arena get(String name) {
        for (Arena arena: arenas) {
            if (arena.getName().equals(name)) {
                return arena;
            }
        }
        return null;
    }

    public static Arena getArenaForPlayer(Player player) {
        for (Arena arena: arenas) {
            if (arena.getPlayers().contains(player)) {
                return arena;
            }
        }
        return null;
    }

    public static void putAllArenas() {
        if (TeamFight.getInstance().getConfig().isConfigurationSection("arena")) {
            ConfigurationSection configurationSection = TeamFight.getInstance().getConfig().getConfigurationSection("arena");
            getArenas().addAll((Collection<? extends Arena>) get(configurationSection.getKeys(false).toString()));
        }
    }


}
