package ua.limefu.teamfight.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.FileManager;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    @Getter private final int minimumPlayers = 2;
    @Getter private final int maximumPlayers = 8;
    @Getter @Setter private int lobbyTime = 30;
    @Getter @Setter private int graceTime = 10;
    @Getter @Setter private Location lobbyLocations;
    @Getter @Setter private Location spawnBlueTeam;
    @Getter @Setter private Location spawnRedLocation;
    @Getter
    private final List<Player> players = new ArrayList<>();

    @Getter @Setter
    private boolean gameInProgress;


    @Getter private final String name;

    @Getter @Setter private int scoreTeamBlue;
    @Getter @Setter private int scoreTeamRed;
    @Getter public ArrayList<Player> teamBlue = new ArrayList<Player>();
    @Getter public ArrayList<Player> teamRed = new ArrayList<Player>();


    public Arena(String name) {
        this.name = name;
        scoreTeamBlue = 0;
        scoreTeamRed = 0;
    }
}
