package ua.limefu.teamfight.game.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.TeamFight;
import ua.limefu.teamfight.game.GameState;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Arena {
    private final int minimumPlayers = 2;
    private final int maximumPlayers = 8;
    @Setter
    private int lobbyTime = 30;
    @Setter
    private int graceTime = 10;
    @Setter
    private Location lobbyLocations;
    @Setter
    private Location spawnBlueTeam;
    @Setter
    private Location spawnRedLocation;
    private final List<Player> players = new ArrayList<>();

    @Setter
    private int inGameTime = 1800;

    @Setter
    private boolean gameInProgress;


    private final String name;

    @Setter
    private int scoreTeamBlue;
    @Setter
    private int scoreTeamRed;
    public ArrayList<Player> teamBlue = new ArrayList<Player>();
    public ArrayList<Player> teamRed = new ArrayList<Player>();


    public Arena(String name) {
        this.name = name;
        scoreTeamBlue = 0;
        scoreTeamRed = 0;
        TeamFight.getInstance().setState(GameState.LOBBY);
    }
}
