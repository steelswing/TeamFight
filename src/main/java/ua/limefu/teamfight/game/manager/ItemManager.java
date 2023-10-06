package ua.limefu.teamfight.game.manager;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import ua.limefu.teamfight.game.arena.Arena;
import ua.limefu.teamfight.utils.ArenaUtil;

public class ItemManager {
    private Arena arena;

    public void giveLobbyItems(Player player) {

        ItemStack teamChooser = new ItemStack(Material.NETHER_STAR);
        ItemMeta teamChooserMeta = teamChooser.getItemMeta();
        teamChooserMeta.setDisplayName("§eВыбор команды §7(ПКМ)");
        teamChooser.setItemMeta(teamChooserMeta);

        player.setGameMode(GameMode.SURVIVAL);

        player.getInventory().clear();

        player.getInventory().setItem(4, teamChooser);

    }

    public void giveIngameItems(Player player) {
        arena = ArenaUtil.getArenaForPlayer(player);
        if (arena != null) {

            ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
            ItemMeta stoneSwordMeta = stoneSword.getItemMeta();
            stoneSwordMeta.setUnbreakable(true);
            stoneSword.setItemMeta(stoneSwordMeta);


            ItemStack bow = new ItemStack(Material.BOW);
            ItemMeta bowMeta = bow.getItemMeta();
            bowMeta.setUnbreakable(true);
            bow.setItemMeta(bowMeta);


            ItemStack shear = new ItemStack(Material.SHEARS);
            ItemMeta shearMeta = shear.getItemMeta();
            shearMeta.setUnbreakable(true);
            shear.setItemMeta(shearMeta);


            ItemStack shield = new ItemStack(Material.SHIELD);
            ItemMeta shieldMeta = shield.getItemMeta();
            shieldMeta.setUnbreakable(true);
            shield.setItemMeta(shieldMeta);


            ItemStack redLeatherBoots = new ItemStack(Material.LEATHER_BOOTS);
            LeatherArmorMeta redLeatherBootsMeta = (LeatherArmorMeta) redLeatherBoots.getItemMeta();
            redLeatherBootsMeta.setColor(Color.RED);
            redLeatherBootsMeta.setUnbreakable(true);
            redLeatherBoots.setItemMeta(redLeatherBootsMeta);


            ItemStack aquaLeatherBoots = new ItemStack(Material.LEATHER_BOOTS);
            LeatherArmorMeta aquaLeatherBootsMeta = (LeatherArmorMeta) aquaLeatherBoots.getItemMeta();
            aquaLeatherBootsMeta.setColor(Color.AQUA);
            aquaLeatherBootsMeta.setUnbreakable(true);
            aquaLeatherBoots.setItemMeta(aquaLeatherBootsMeta);

            player.getInventory().clear();

            player.getInventory().setItem(0, stoneSword);
            player.getInventory().setItem(1, bow);
            player.getInventory().setItem(2, shear);
            player.getInventory().setItem(4, new ItemStack(Material.ARROW, 8));

            if (arena.getTeamRed().contains(player)) {
                player.getInventory().setItem(3, new ItemStack(Material.WOOL, 64, (short) 14));
                player.getInventory().setBoots(redLeatherBoots);
            } else {
                player.getInventory().setItem(3, new ItemStack(Material.WOOL, 64, (short) 11));
                player.getInventory().setBoots(aquaLeatherBoots);
            }

        }
    }

    public void refillWool(Player player) {
        arena = ArenaUtil.getArenaForPlayer(player);
        if (arena != null) {
            if (arena.getTeamRed().contains(player)) {
                player.getInventory().setItem(3, new ItemStack(Material.WOOL, 64, (short) 14));
            } else {
                player.getInventory().setItem(3, new ItemStack(Material.WOOL, 64, (short) 11));
                ;
            }

        }
    }
}
