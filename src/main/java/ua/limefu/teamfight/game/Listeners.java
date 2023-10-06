package ua.limefu.teamfight.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import ua.limefu.teamfight.TeamFight;
import ua.limefu.teamfight.game.arena.Arena;
import ua.limefu.teamfight.utils.ArenaUtil;


public class Listeners implements Listener {
    private final TeamFight plugin = TeamFight.getInstance();
    private Arena arena;

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (plugin.getState() != GameState.INGAME) {
            event.setCancelled(true);
        } else if (plugin.getState() == GameState.INGAME) {
            Player damager;
            Player damaged = (Player) event.getEntity();
            arena = ArenaUtil.getArenaForPlayer(damaged);
            if (arena != null) {

                if (event.getDamager() instanceof Arrow) {
                    Arrow arrow = (Arrow) event.getDamager();
                    damager = (Player) arrow.getShooter();
                } else {
                    damager = (Player) event.getDamager();
                }

                if (arena.getTeamBlue().contains(damager) & arena.getTeamBlue().contains(damaged)) {
                    event.setDamage(0.0);
                } else if (arena.getTeamRed().contains(damager) & arena.getTeamRed().contains(damaged)) {
                    event.setDamage(0.0);
                } else {
                    event.setCancelled(false);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onDeath(EntityDeathEvent event) {

        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player killer = player.getKiller();
            arena = ArenaUtil.getArenaForPlayer(player);
            if (arena != null) {

                event.getDrops().clear();

                killer.sendTitle("§f[§a⚔§f] " + player.getDisplayName(), "");
                killer.playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);

                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        player.spigot().respawn();
                        player.teleport(plugin.getFilemanager().getLocation(arena, "spectator"));
                        player.getInventory().clear();
                    }
                }, 5);
            }
        }
    }

    @EventHandler
    public void onRegen(EntityRegainHealthEvent event) {
        Player player = (Player) event.getEntity();
        arena = ArenaUtil.getArenaForPlayer(player);
        if (arena != null) {
            if (plugin.getState() == GameState.INGAME) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        Location targetBlockLocation = event.getBlock().getLocation();
        Player player = event.getPlayer();
        Arena arena = ArenaUtil.getArenaForPlayer(player);
        if (arena != null) {
            if (plugin.getMaputil().isInside(arena, targetBlockLocation) && plugin.getState() == GameState.INGAME) {
                event.setCancelled(false);
                plugin.getMaputil().checkTargetForWinner(arena);
                plugin.getItemUtil().refillWool(event.getPlayer());
            } else {
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Location targetBlockLocation = event.getBlock().getLocation();
        arena = ArenaUtil.getArenaForPlayer(event.getPlayer());
        if (arena != null) {

            if (plugin.getMaputil().isInside(arena, targetBlockLocation) && plugin.getState() == GameState.INGAME) {
                event.setCancelled(false);
                event.setDropItems(false);
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        arena = ArenaUtil.getArenaForPlayer(player);
        if (arena != null) {

            if (plugin.getState() == GameState.GRACE && arena.getGraceTime() <= 14 && arena.getGraceTime() >= 0) {
                event.setTo(event.getFrom());
            }
        } else event.setCancelled(true);
    }


    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {

        event.setCancelled(true);

    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Player player = (Player) event.getEntity();
        arena = ArenaUtil.getArenaForPlayer(player);
        if (arena != null) {
            if (plugin.getState() != GameState.INGAME) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player damaged = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            arena = ArenaUtil.getArenaForPlayer(damaged);
            if (arena != null) {
                if (plugin.getState() != GameState.INGAME) {
                    event.setCancelled(true);
                    return;
                } else {
                    if (arena.getTeamRed().contains(damaged) && arena.getTeamRed().contains(damager)) {
                        event.setCancelled(true);
                        return;
                    } else if (arena.getTeamBlue().contains(damaged) && arena.getTeamBlue().contains(damager)) {
                        event.setCancelled(true);
                        return;
                    } else {
                        event.setCancelled(false);
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);

    }
}
