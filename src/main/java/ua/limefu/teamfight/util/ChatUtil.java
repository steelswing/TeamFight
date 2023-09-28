package ua.limefu.teamfight.util;

import jdk.tools.jlink.plugin.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.TeamFight;

public class ChatUtil {

    private static String prefix = "&9[" + TeamFight.getInstance().getName() + "]&6";

    public static void sendMessage(Player player, String msg) {

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg));

    }
}
