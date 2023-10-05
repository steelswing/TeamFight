package ua.limefu.teamfight;

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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;


public class Listeners implements Listener {
    @EventHandler
    public void playerJoin(PlayerJoinEvent event)
    {

        Player player = event.getPlayer();
        int onlinePlayers = Bukkit.getOnlinePlayers().size();

        event.setJoinMessage(null);

        if(TeamFight.main.state == GameState.LOBBY){
            Bukkit.broadcastMessage("§7" + player.getName() + " зашел на сервер (§f" + onlinePlayers + "§7/§f" + TeamFight.main.maximumPlayers + "§7)");

            if(onlinePlayers == TeamFight.main.minimumPlayers){
                TeamFight.countdown.startLobbyCD();
            }
            Bukkit.getScheduler().runTaskLater(TeamFight.main, new Runnable() {
                @Override
                public void run() {
                    TeamFight.itemUtil.giveLobbyItems(player);
                    player.teleport(TeamFight.filemanager.getLocation("lobby"));
                    player.setHealth(20);
                    player.setFoodLevel(20);
                }
            }, 5);
        }
        else
        {
            Bukkit.getScheduler().runTaskLater(TeamFight.main, new Runnable() {
                @Override
                public void run() {
                    player.getInventory().clear();
                    player.teleport(TeamFight.filemanager.getLocation("spectator"));
                }
            }, 5);
        }
    }
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(TeamFight.main.state != GameState.INGAME){
            event.setCancelled(true);
        }else if(TeamFight.main.state == GameState.INGAME){
            Player damager;
            Player damaged = (Player)event.getEntity();

            if(event.getDamager() instanceof Arrow){
                Arrow arrow = (Arrow) event.getDamager();
                damager = (Player) arrow.getShooter();
            }else{
                damager = (Player) event.getDamager();
            }

            if(TeamFight.main.TeamBlue.contains(damager) & TeamFight.main.TeamBlue.contains(damaged)){
                event.setDamage(0.0);
            }else if(TeamFight.main.TeamRed.contains(damager) & TeamFight.main.TeamRed.contains(damaged)){
                event.setDamage(0.0);
            }else{
                event.setCancelled(false);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onDeath(EntityDeathEvent event){

        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            Player killer = player.getKiller();

            event.getDrops().clear();

            killer.sendTitle("§f[§a⚔§f] " + player.getDisplayName(), "");
            killer.playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);

            Bukkit.getScheduler().runTaskLater(TeamFight.main, new Runnable() {
                @Override
                public void run() {
                    player.spigot().respawn();
                    player.teleport(TeamFight.filemanager.getLocation("spectator"));
                    player.getInventory().clear();
                }
            }, 5);
        }
    }

    @EventHandler
    public void onRegen(EntityRegainHealthEvent event){
        if(TeamFight.main.state == GameState.INGAME){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){

        Location targetBlockLocation = event.getBlock().getLocation();
        if(TeamFight.maputil.isInside(targetBlockLocation) && TeamFight.main.state == GameState.INGAME)
        {
            event.setCancelled(false);
            TeamFight.maputil.checkTargetForWinner();
            TeamFight.itemUtil.refillWool(event.getPlayer());
        }
        else
        {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Location targetBlockLocation = event.getBlock().getLocation();

        if(TeamFight.maputil.isInside(targetBlockLocation) && TeamFight.main.state == GameState.INGAME)
        {
            event.setCancelled(false);
            event.setDropItems(false);
        }
        else
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){

        if(TeamFight.main.state == GameState.GRACE && TeamFight.main.graceTime <= 14 && TeamFight.main.graceTime >= 0)
        {
            event.setTo(event.getFrom());
        }
    }


    @EventHandler
    public void onHunger(FoodLevelChangeEvent event){

        event.setCancelled(true);

    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(TeamFight.main.state != GameState.INGAME){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player){
            Player damaged = (Player)event.getEntity();
            Player damager = (Player)event.getDamager();
            if(TeamFight.main.state != GameState.INGAME){
                event.setCancelled(true);
                return;
            }else{
                if(TeamFight.main.TeamRed.contains(damaged) && TeamFight.main.TeamRed.contains(damager)){
                    event.setCancelled(true);
                    return;
                }else if(TeamFight.main.TeamBlue.contains(damaged) && TeamFight.main.TeamBlue.contains(damager)){
                    event.setCancelled(true);
                    return;
                }else{
                    event.setCancelled(false);
                    return;
                }
            }
        }
    }
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event)
    {
        if(TeamFight.main.state != GameState.INGAME)
        {
            event.setCancelled(true);
        }
    }
}
