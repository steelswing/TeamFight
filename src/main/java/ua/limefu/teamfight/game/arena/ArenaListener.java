package ua.limefu.teamfight.game.arena;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import ua.limefu.teamfight.game.GameState;
import ua.limefu.teamfight.TeamFight;
import ua.limefu.teamfight.utils.ArenaUtil;
import ua.limefu.teamfight.utils.ChatUtil;

public class ArenaListener implements Listener {

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (e.getInventory().equals(ArenaGUI.getArenaMenu())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(ArenaGUI.getArenaMenu())) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            ItemStack item = e.getCurrentItem();

            if (item != null && item.getType() != Material.AIR) {
                String itemName = item.getItemMeta().getDisplayName();
                Arena arena = ArenaUtil.get(itemName);
                if (arena != null) {
                    arena.getPlayers().add(player);
                    int onlinePlayers = arena.getPlayers().size();
                    TeamFight.getInstance().getCountdown().startPlayerLeftBroadcast(arena);
                    if (TeamFight.getInstance().getState() == GameState.LOBBY) {
                        ChatUtil.sendArenaMessage(arena, "§7" + player.getName() + " зашел на сервер (§f" + onlinePlayers + "§7/§f" + arena.getMaximumPlayers() + "§7)");

                        if (onlinePlayers == arena.getMinimumPlayers()) {
                            TeamFight.getInstance().getCountdown().startLobbyCD(arena);
                        }
                        Bukkit.getScheduler().runTaskLater(TeamFight.getInstance(), () -> {
                            TeamFight.getInstance().getItemUtil().giveLobbyItems(player);
                            player.teleport(TeamFight.getInstance().getFilemanager().getLocation(arena, "lobby"));
                            player.setHealth(20);
                            player.setFoodLevel(20);
                        }, 5);
                    } else {
                        Bukkit.getScheduler().runTaskLater(TeamFight.getInstance(), () -> {
                            player.getInventory().clear();
                            player.teleport(TeamFight.getInstance().getFilemanager().getLocation(arena, "spectator"));
                        }, 5);
                    }

                }
            }
        }
    }
}
