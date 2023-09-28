package ua.limefu.teamfight.game;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArenaList {

    public static List<Arena> arenas = new ArrayList<>();

    public static Arena get(String name) {
        for (Arena arena: arenas){
            if (arena.getName().equals(name)) {
                return arena;
            }
        }
        return null;

    }
    public static Arena getPlayer(Player player) {
        for (Arena arena: arenas) {
            if (arena.getPlayers().contains(player)) {
                return arena;
            }
        }
        return null;
    }
}
