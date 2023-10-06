package ua.limefu.teamfight.game.arena;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ua.limefu.teamfight.utils.ArenaUtil;

import java.util.ArrayList;
import java.util.List;

public class ArenaGUI {
    @Getter
    private static final Inventory arenaMenu = Bukkit.createInventory(null, 9, "Выбери арену");

    public void openArenaMenu(Player player) {
        for (int i = 0; i < ArenaUtil.getArenas().size(); i++) {
            Arena arena = ArenaUtil.getArenas().get(i);
            ItemStack arenaItem = new ItemStack(Material.PAPER);
            ItemMeta arenaLore = arenaItem.getItemMeta();
            arenaLore.setDisplayName(arena.getName());
            List<String> lore = new ArrayList<>();
            lore.add("Нажми чтобы присоеденится");
            arenaLore.setLore(lore);
            arenaItem.setItemMeta(arenaLore);
            getArenaMenu().setItem(i, arenaItem);
        }
        player.openInventory(getArenaMenu());
    }
}
