package ua.limefu.teamfight.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import ua.limefu.teamfight.util.ChatUtil;
import ua.limefu.teamfight.TeamFight;

public class Round implements Listener {
    private Game game;
    private Stage stage;
    private Arena arena;
    private Team team;

    private final int coolDown = 20;

    public void timer() {

        stage = Stage.ROUND_STARTING;
        new BukkitRunnable() {
            int ctr = coolDown;

            @Override
            public void run() {
                arena.sendArenaTitle("До начала игры осталось " + ctr--, "Приготовься!");
                if (ctr <= 0) {

                    onStarting();
                    cancel();
                    stage = Stage.ROUND_GOING;
                }
            }
        }.runTaskTimer(TeamFight.getInstance(), 0L, 100L);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (stage == Stage.ROUND_GOING) {
                    stage = Stage.ROUND_ENDED;
                    arena.sendArenaMessage("Игра окончена!");
                } else {
                    if (stage == Stage.ROUND_ENDED) {
                        cancel();
                    }
                }
            }
        }.runTaskLater(TeamFight.getInstance(), 2400L);
    }

    public void gameEnd() {
        if (stage == Stage.ROUND_ENDED) {
            team.getPlayers().clear();
            game.getTeams().clear();
            stage = Stage.GAME_ENDED;
        }
    }

    @EventHandler
    public void onDamageTeam(EntityDamageByEntityEvent e) {
        Player player = (Player) e.getDamager();
        if (game.getTeams().get(0).contains(player) && game.getTeams().get(0).contains((Player) e.getEntity())) {
            e.setCancelled(true);
        }
        if (game.getTeams().get(1).contains(player) && game.getTeams().get(1).contains((Player) e.getEntity())) {
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onDeathEvent(EntityDeathEvent e) {
        Player player = (Player) e.getEntity();
        if (game.getTeams().contains(player)) {
            player.getInventory().clear();
            game.getTeams().remove(player);
            player.teleport((Location) arena.getOnJoinLocation());
            ChatUtil.sendMessage(player, "Вы проиграли!");
        }
    }

    public void onStarting() {
        if (stage == Stage.ROUND_STARTING) {
            team.getPlayers().forEach(player -> player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 10)));
        }
    }
}
