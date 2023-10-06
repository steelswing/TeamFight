package ua.limefu.teamfight.game.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.game.arena.Arena;

import java.io.File;
import java.io.IOException;

public class FileManager {
    private final File file;
    private final FileConfiguration cfg;
    public FileManager() {
        file = new File("plugins/TeamFight", "locations.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }
    public void saveCfg() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveBlockLocation(Arena arena, Location loc, Player player, int count) {

        String countAsString = String.valueOf(count);
        String name = arena.getName();
        String nameblock = "block" + countAsString;
        String path = name + "." + nameblock;

        cfg.set(path + ".world", loc.getWorld().getName());
        cfg.set(path + ".x", loc.getX());
        cfg.set(path + ".y", loc.getY() - 1);
        cfg.set(path + ".z", loc.getZ());
        player.sendMessage("§aБлок  " + count + " на арене " + name + " был сохранен. §7(§e" + loc.getBlockX() + "§7/§e" + (loc.getBlockY() - 1) + "§7/§e" + loc.getBlockZ() + "§7)");
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.0F);

        saveCfg();
    }

    public Location getBlockLocation(Arena arena, int count) {
        String countAsString = String.valueOf(count);
        String name = arena.getName();
        String nameblock = "block" + countAsString;
        String path = name + "." + nameblock;
        World w = Bukkit.getWorld(cfg.getString(path + ".world"));
        double x = cfg.getDouble(path + ".x");
        double y = cfg.getDouble(path + ".y");
        double z = cfg.getDouble(path + ".z");
        Location loc = new Location(w, x, y, z);
        return loc;
    }

    public void saveLocation(Arena arena, String locatName, Location loc, Player player) {

        String name = arena.getName();
        String path = name + "." + locatName;
        cfg.set(path + ".world", loc.getWorld().getName());
        cfg.set(path + ".x", loc.getX());
        cfg.set(path + ".y", loc.getY());
        cfg.set(path + ".z", loc.getZ());

        cfg.set(path + ".yaw", loc.getYaw());
        cfg.set(path + ".pitch", loc.getPitch());

        player.sendMessage("§7Локация §e" + name + " §7была сохраненная.");
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);

        saveCfg();
    }

    public Location getLocation(Arena arena, String locationName) {
        String name = arena.getName();
        String path = name + "." + locationName;
        World w = Bukkit.getWorld(cfg.getString(path + ".world"));
        double x = cfg.getDouble(path + ".x");
        double y = cfg.getDouble(path + ".y");
        double z = cfg.getDouble(path + ".z");
        Location loc = new Location(w, x, y, z);
        loc.setYaw(cfg.getInt(path + ".yaw"));
        loc.setPitch(cfg.getInt(path + ".pitch"));
        return loc;
    }
}
