package ua.limefu.teamfight.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.TeamFight;
import ua.limefu.teamfight.game.arena.Arena;

public class ChatUtil {

    private static final String prefix = TeamFight.getInstance().prefix;

    public static void sendMessage(Player player, String msg) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg));
    }

    public static void sendArenaMessage(Arena arena, String msg) {
        for (Player player : arena.getPlayers()) {
            sendMessage(player, msg);
        }
    }
}
