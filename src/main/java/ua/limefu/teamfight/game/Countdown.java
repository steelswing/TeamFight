package ua.limefu.teamfight.game;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.TeamFight;
import ua.limefu.teamfight.game.GameState;
import ua.limefu.teamfight.game.arena.Arena;
import ua.limefu.teamfight.utils.ChatUtil;

public class Countdown {

    private boolean lobbystarted = false;
    private boolean restartstarted = false;


    private final TeamFight plugin = TeamFight.getInstance();

    private String prefix = plugin.prefix;

    private static int lobbycd;
    private int informcd;
    private int gracecd;
    @Getter
    @Setter
    private int ingamecd;
    @Getter
    @Setter
    private int restartcd;

    public void startPlayerLeftBroadcast(Arena arena) {
        if (arena != null) {
            informcd = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, () -> {
                if (arena.getPlayers().size() < arena.getMinimumPlayers() && arena.getPlayers().size() > 0) {
                    int playerNeeded = arena.getMinimumPlayers() - arena.getPlayers().size();
                    if (playerNeeded == 1) {
                        ChatUtil.sendArenaMessage(arena, prefix + "§7Для того чтобы раунд начался, нужен еще 1 игрок в игре.");
                    } else {
                        ChatUtil.sendArenaMessage(arena, prefix + "§7Для того чтобы раунд начался нужно еще §f" + playerNeeded + " §7игроков.");
                    }
                }
            }, 0, 20 * 60);
        }
    }

    public void startLobbyCD(Arena arena) {
        if (arena != null) {
            if (!lobbystarted) {
                lobbystarted = true;
                lobbycd = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                    Bukkit.getScheduler().cancelTask(informcd);
                    if (arena.getLobbyTime() <= 60) {
                        setPlayerLevel(arena);
                        if (arena.getLobbyTime() < 5) {
                            ChatUtil.sendArenaMessage(arena, "§7Игра начинается через" + arena.getLobbyTime() + "секунд!");
                            if (arena.getLobbyTime() == 0) {
                                for (Player currentPlayer : Bukkit.getOnlinePlayers()) {
                                    plugin.getPlayerUtil().pushPlayersInTeam(currentPlayer);
                                }
                                plugin.getPlayerUtil().teleportPlayersInBase(arena);

                                plugin.setState(GameState.GRACE);
                                startGraceCD(arena);
                                Bukkit.getScheduler().cancelTask(lobbycd);

                            }
                        }
                    }
                    arena.setLobbyTime(arena.getLobbyTime() - 1);

                }, 0, 20L);

            }
        }
    }


    public void startGraceCD(Arena arena) {

        if (arena != null) {
            gracecd = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                if (arena.getGraceTime() == 30) {
                    if (!arena.isGameInProgress()) {
                        ChatUtil.sendArenaMessage(arena, "");
                        ChatUtil.sendArenaMessage(arena, "");
                        ChatUtil.sendArenaMessage(arena, prefix + "§7Поменяйте цвет шерсти в центре карты чтобы победить.");
                        ChatUtil.sendArenaMessage(arena, prefix + "§7Для победы нужно 3 очка!");
                        ChatUtil.sendArenaMessage(arena, "");
                        ChatUtil.sendArenaMessage(arena, "");
                        arena.setGameInProgress(true);
                    }
                    for (Player currentPlayer : arena.getPlayers()) {
                        if (arena.getTeamBlue().contains(currentPlayer) || arena.getTeamRed().contains(currentPlayer)) {
                            plugin.getItemUtil().giveIngameItems(currentPlayer);
                        }
                    }
                    setPlayerLevel(arena);
                } else if (arena.getGraceTime() <= 30) {
                    setPlayerLevel(arena);
                    if (arena.getGraceTime() <= 3) {
                        ChatUtil.sendArenaMessage(arena, "§a§l»" + arena.getGraceTime() + "«");
                        if (arena.getGraceTime() == 0) {
                            startGameCD(arena);
                            Bukkit.getScheduler().cancelTask(gracecd);
                        }
                    }
                }

                arena.setGraceTime(arena.getGraceTime() - 1);
            }, 0, 20L);
        }
    }

    public void startGameCD(Arena arena) {
        if (arena != null) {
            setIngamecd(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                if (arena.getInGameTime() <= 1800) {
                    setPlayerLevel(arena);
                    if (arena.getInGameTime() == 0) {
                        Bukkit.getScheduler().cancelTask(getIngamecd());
                    }
                }
                arena.setInGameTime(arena.getInGameTime() - 1);

            }, 0, 20L));
        }
    }



    public void setPlayerLevel(Arena arena) {
        if (arena != null) {
            if (plugin.getState() == GameState.LOBBY) {
                for (Player currentPlayer : arena.getPlayers()) {
                    currentPlayer.setLevel(arena.getLobbyTime());
                }
            } else if (plugin.getState() == GameState.GRACE)
                for (Player currentPlayer : arena.getPlayers()) {
                    currentPlayer.setLevel(arena.getGraceTime());
                }
        }
    }

    public void broadcastLobbyTime(Arena arena) {
        if (arena != null) {
            ChatUtil.sendArenaMessage(arena, prefix + "§7До начала игры осталось §f" + arena.getLobbyTime() + " §7секунд.");
        }
    }
}
