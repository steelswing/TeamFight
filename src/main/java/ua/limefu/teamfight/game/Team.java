package ua.limefu.teamfight.game;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.ChatUtil;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private final List<Location> spawn = new ArrayList<>();

    private Color color;

    private final List<Player> players = new ArrayList<>();

    public Team(Color color) {
        this.color = color;
    }

    public void addPlayer(Player player) {
        players.add(player);

        ChatUtil.sendMessage(player, "Ты в команде " + color.toString());

    }
    public boolean contains(Player player) {
        return players.contains(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Color getColor() {
        return color;
    }

    public List<Location> getSpawn() {
        return spawn;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
