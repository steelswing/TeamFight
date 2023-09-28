package ua.limefu.teamfight.game;

import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.util.ChatUtil;

import java.util.ArrayList;
import java.util.List;

public class Team {

    @Getter
    private final List<Location> spawn = new ArrayList<>();

    private final Color color;
    @Getter
    private final List<Player> players = new ArrayList<>();

    public Team(Color color) {
        this.color = color;
    }

    public void addPlayer(Player player) {
        players.add(player);
        ChatUtil.sendMessage(player, "Ты в команде " + color.toString());

    }
    public boolean contains(Player player) {
        return getPlayers().contains(player);
    }
}
