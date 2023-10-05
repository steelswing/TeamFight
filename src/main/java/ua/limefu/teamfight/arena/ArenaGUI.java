package ua.limefu.teamfight.arena;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ArenaGUI {
    public void openArenaMenu(Player player) {
        Inventory arenaMenu = Bukkit.createInventory(null, 9, "Выбери арену");
        for (int i = 0; i < ArenaList.getArenas().size(); i++) {
            Arena arena = ArenaList.getArenas().get(i);
            ItemStack arenaItem = new ItemStack(Material.PAPER);
            ItemMeta arenaLore = arenaItem.getItemMeta();
            arenaLore.setDisplayName(arena.getName());
            List<String> lore = new ArrayList<>();
            lore.add("Нажми чтобы присоеденится");
            arenaLore.setLore(lore);
            arenaItem.setItemMeta(arenaLore);
            arenaMenu.setItem(i, arenaItem);
        }
        player.openInventory(arenaMenu);
    }
}
