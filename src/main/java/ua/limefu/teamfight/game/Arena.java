package ua.limefu.teamfight.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ua.limefu.teamfight.ChatUtil;
import ua.limefu.teamfight.TeamFight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Arena {

    private Location lobby;
    private final String name;
    private Stage arenaStage = Stage.CLOSED;
    private final int minPlayers = 2;
    private final int timeToStart = 20;

    private final List<Player> players = new ArrayList<>();
    private final List<Location> tradernpc = new ArrayList<>();
    private final Map<Player, Location> onJoinLocation = new HashMap<>();
    private Game game;


    public Arena(String name) {
        this.name = name;

        game = new Game(this);
    }

    public void reset() {
        arenaStage = Stage.RESET;
    }

    public void join(Player player) {
        if (ArenaList.get(player) != null) {
            ChatUtil.sendMessage(player, "Ты уже на арене!");
            return;
        }

        if (arenaStage != Stage.WAITING && arenaStage == Stage.STARTING) {
            ChatUtil.sendMessage(player, "Арена начата");
        }

        preparePlayer();
        onJoinLocation.put(player, player.getLocation());
        player.teleport(lobby);
        players.add(player);
        sendArenaMessage(player.getDisplayName() + " присоеденился(-ась)");

        if (players.size() >= minPlayers && arenaStage == Stage.WAITING) {
            startGame();
        }
    }

    private void startGame() {
        arenaStage = Stage.STARTING;
        new BukkitRunnable() {
            int ctr = timeToStart;

            @Override
            public void run() {

                sendArenaTitle("До начала игры осталось " + ctr--, "Приготовься!");

                if (players.size() < minPlayers) {
                    arenaStage = Stage.WAITING;
                    cancel();
                }
                if (ctr <= 0) {
                    game.start();
                    cancel();
                }

            }
        }.runTaskTimer(TeamFight.getInstance(), 0L, 20L);

    }

    public void quit(Player player) {
        if(ArenaList.get(player) != null) {
            ChatUtil.sendMessage(player, "Вы покинули арену!");
        }
        player.teleport(onJoinLocation.get(player));
        onJoinLocation.remove(player);
        players.remove(player);
        sendArenaMessage(player.getDisplayName() + " вышел!");
    }

    private void preparePlayer() {
        // TODO: save inv

    }

    public void sendArenaMessage(String message) {
        for (Player player: players) {
            ChatUtil.sendMessage(player, message);
        }

    }

    public void sendArenaTitle(String title, String subtitle) {

        for (Player player: players) {
            player.sendTitle(title, subtitle, 10, 40, 10);
        }

    }
    public boolean launch() {
        for (Team team: game.getTeams()) {
            if (team.getSpawn() == null) {
                return false;
            }
        }
        if (lobby == null || tradernpc.size() < 2) {
            return false;
        }
        arenaStage = Stage.WAITING;
        return true;

    }

    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Location getLobby() {
        return lobby;
    }

    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    public Game getGame() {
        return game;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public List<Location> getTradernpc() {
        return tradernpc;
    }

    public Map<Player, Location> getOnJoinLocation() {
        return onJoinLocation;
    }
}
