package ua.limefu.teamfight;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ua.limefu.teamfight.arena.Arena;

public class Countdown {

    public boolean lobbystarted = false;
    public boolean restartstarted = false;


    private final TeamFight plugin = TeamFight.getInstance();

    String prefix = plugin.prefix;

    public static int lobbycd;
    public int informcd;
    public int gracecd;
    public int ingamecd;
    int restartcd;

    @SuppressWarnings("deprecation")
    public void startPlayerLeftBroadcast(Arena arena) {
        if (arena != null) {
            informcd = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {

                @Override
                public void run() {
                    if (arena.getPlayers().size() < arena.getMinimumPlayers() && arena.getPlayers().size() > 0) {
                        int playerNeeded = arena.getMinimumPlayers() - arena.getPlayers().size();
                        if (playerNeeded == 1) {
                            Bukkit.broadcastMessage(prefix + "§7Для того чтобы раунд начался, нужен еще 1 игрок в игре.");
                        } else {
                            Bukkit.broadcastMessage(prefix + "§7Для того чтобы раунд начался нужно еще §f" + playerNeeded + " §7игроков.");
                        }
                    }
                }
            }, 0, 20 * 60);
        }
    }

    public void startLobbyCD(Arena arena) {
        if (arena != null) {
            if (!lobbystarted) {
                lobbystarted = true;
                lobbycd = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

                    @Override
                    public void run() {
                        Bukkit.getScheduler().cancelTask(informcd);

                        if (arena.getLobbyTime() == 60) {
                            broadcastLobbyTime(arena);
                            setPlayerLevel(arena);
                        } else if (arena.getLobbyTime() <= 59 && arena.getLobbyTime() >= 31) {
                            setPlayerLevel(arena);
                        } else if (arena.getLobbyTime() == 30) {
                            broadcastLobbyTime(arena);
                            setPlayerLevel(arena);
                        } else if (arena.getLobbyTime() <= 29 && arena.getLobbyTime() >= 6) {
                            setPlayerLevel(arena);
                        } else if (arena.getLobbyTime() <= 5 && arena.getLobbyTime() > 1) {
                            broadcastLobbyTime(arena);
                            setPlayerLevel(arena);
                        } else if (arena.getLobbyTime() == 1) {
                            Bukkit.broadcastMessage(prefix + "§7Игра начинается через секунду");
                            setPlayerLevel(arena);
                        } else if (arena.getLobbyTime() == 0) {
                            Bukkit.broadcastMessage(prefix + "§7Игра начинается.");
                            setPlayerLevel(arena);

                            for (Player currentPlayer : Bukkit.getOnlinePlayers()) {
                                plugin.getPlayerUtil().pushPlayersInTeam(currentPlayer);
                            }
                            plugin.getPlayerUtil().teleportPlayersInBase(arena);

                            plugin.setState(GameState.GRACE);
                            startGraceCD(arena);
                            Bukkit.getScheduler().cancelTask(lobbycd);
                        }
                        arena.setLobbyTime(arena.getLobbyTime() - 1);
                    }
                }, 0, 20L);

            }
        }
    }


    public void startGraceCD(Arena arena) {

        if (arena != null) {
            gracecd = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

                @Override
                public void run() {
                    if (arena.getGraceTime() == 30) {
                        if (!arena.isGameInProgress()) {
                            Bukkit.broadcastMessage("");
                            Bukkit.broadcastMessage("");
                            Bukkit.broadcastMessage(prefix + "§7Поменяйте цвет шерсти в центре карты чтобы победить.");
                            Bukkit.broadcastMessage(prefix + "§7Для победы нужно 3 очка!");
                            Bukkit.broadcastMessage("");
                            Bukkit.broadcastMessage("");
                            arena.setGameInProgress(true);
                        }
                        for (Player currentPlayer : arena.getPlayers()) {
                            if (arena.getTeamBlue().contains(currentPlayer) || arena.getTeamRed().contains(currentPlayer)) {
                                plugin.getItemUtil().giveIngameItems(currentPlayer);
                            }
                        }
                        setPlayerLevel(arena);
                    } else if (arena.getGraceTime() <= 30 && arena.getGraceTime() >= 16) {
                        setPlayerLevel(arena);
                    } else if (arena.getGraceTime() == 15) {

                        plugin.getPlayerUtil().teleportPlayersInStartPosition(arena);
                        setPlayerLevel(arena);

                    } else if (arena.getGraceTime()<= 14 && arena.getGraceTime() >= 4) {
                        setPlayerLevel(arena);
                    } else if (arena.getGraceTime() == 3) {
                        setPlayerLevel(arena);
                        for (Player currentPlayer : Bukkit.getOnlinePlayers()) {
                            currentPlayer.sendTitle("§c§l»3«", null);
                        }
                    } else if (arena.getGraceTime() == 2) {
                        setPlayerLevel(arena);
                        for (Player currentPlayer : Bukkit.getOnlinePlayers()) {
                            currentPlayer.sendTitle("§e§l»2«", null);
                        }
                    } else if (arena.getGraceTime() == 1) {
                        setPlayerLevel(arena);
                        for (Player currentPlayer : Bukkit.getOnlinePlayers()) {
                            currentPlayer.sendTitle("§a§l»1«", null);
                        }
                    } else if (arena.getGraceTime() == 0) {
                        setPlayerLevel(arena);
                        for (Player currentPlayer : Bukkit.getOnlinePlayers()) {
                            currentPlayer.sendTitle("§b§lВперед!", null);
                        }
                        plugin.setState(GameState.INGAME);
                        Bukkit.getScheduler().cancelTask(gracecd);
                    }
                    arena.setGraceTime(arena.getGraceTime() - 1 );
                }
            }, 0, 20L);
        }
    }


    public void setPlayerLevel(Arena arena){
        if (arena != null) {
            if (plugin.getState() == GameState.LOBBY) {
                for (Player currentPlayer : arena.getPlayers()) {
                    currentPlayer.setLevel(arena.getLobbyTime());
                }
            } else if (plugin.getState() == GameState.GRACE)
                for (Player currentPlayer : Bukkit.getOnlinePlayers()) {
                    currentPlayer.setLevel(arena.getGraceTime());
                }
        }
    }

    public void broadcastLobbyTime(Arena arena){
         if (arena != null) {
             Bukkit.broadcastMessage(prefix + "§7До начала игры осталось §f" + arena.getLobbyTime() + " §7секунд.");
         }
    }
}
