package ua.limefu.teamfight.game;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ua.limefu.teamfight.game.command.SpawnTeamCMD;

import java.util.*;

public class Game {

    public final List<Team> teams = new ArrayList<>();
    private final Map<Player, Integer> moneyMap = new HashMap<>();
    private final Arena arena;
    private Round round;
    private SpawnTeamCMD spawnTeamCMD;

    public Game(Arena arena) {
        teams.add(new Team(Color.RED));
        teams.add(new Team(Color.BLUE));

        this.arena = arena;
    }

    public void addMoney(Player player, int sum){
        if (moneyMap.containsKey(player)) {
            moneyMap.put(player, moneyMap.get(player) + sum);
        } else {
            moneyMap.put(player, sum);
        }
    }

    private void playersToTeam() {
        Random random = new Random();
        for (int i = 0; i < arena.getPlayers().size() / 2 + 1; i++) {
            teams.get(0).addPlayer(arena.getPlayers().get(random.nextInt(arena.getPlayers().size())));

            if (i == arena.getPlayers().size() / 2 && arena.getPlayers().size() % 2 == 1) {
                break;
            }

            while (true) {
                Player player = arena.getPlayers().get(random.nextInt(arena.getPlayers().size()));

                if (getTeam(player) == null) {
                    teams.get(1).addPlayer(player);
                    break;
                }
            }
        }
    }

    private Team getTeam(Player player) {
        for (Team team: teams) {
            if (team.contains(player)) {
                return team;
            }
        }
        return null;
    }

    public void start() {

        playersToTeam();
        preparePlayers();
        round.timer();

        round = new Round();

    }

    private void preparePlayers() {

        for (Player player: arena.getPlayers()) {
            player.getInventory().clear();

            player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
            player.setHealth(20);
            player.setFoodLevel(20);

            player.setGameMode(GameMode.ADVENTURE);
            player.teleport((Location) getTeam(player).getSpawn());
        }
    }

    public Arena getArena() {
        return arena;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Round getRound() {
        return round;
    }

    public Map<Player, Integer> getMoneyMap() {
        return moneyMap;
    }
}
