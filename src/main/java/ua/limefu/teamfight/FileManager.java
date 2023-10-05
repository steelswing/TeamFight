package ua.limefu.teamfight;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.arena.Arena;

import java.io.File;
import java.io.IOException;

public class FileManager {
    File file = new File("plugins/TeamFight", "locations.yml");
    public FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

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
        String nameblock = "block"+countAsString;

        cfg.set(name+"."+nameblock+".world", loc.getWorld().getName());
        cfg.set(name+"."+nameblock+".x", loc.getX());
        cfg.set(name+"."+nameblock+".y", loc.getY()-1);
        cfg.set(name+"."+nameblock+".z", loc.getZ());
        player.sendMessage("§aБлок  " + count + " на арене "+ name +" был сохранен. §7(§e" + loc.getBlockX() + "§7/§e" + (loc.getBlockY()-1) + "§7/§e" + loc.getBlockZ()+"§7)");
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.0F);

        saveCfg();
    }

    public Location getBlockLocation(Arena arena, int count) {
        String countAsString = String.valueOf(count);
        String name = arena.getName();
        String nameblock = "block"+countAsString;
        World w = Bukkit.getWorld(cfg.getString(name+"."+nameblock+".world"));
        double x = cfg.getDouble(name+"."+nameblock+".x");
        double y = cfg.getDouble(name+"."+nameblock+".y");
        double z = cfg.getDouble(name+"."+nameblock+".z");
        Location loc = new Location(w, x, y, z);
        return loc;
    }

    public void saveLocation(Arena arena, String locatName, Location loc, Player player) {

        String name = arena.getName();
        String locationName = locatName;
        cfg.set(name+"."+locationName+".world", loc.getWorld().getName());
        cfg.set(name+"."+locationName+".x", loc.getX());
        cfg.set(name+"."+locationName+".y", loc.getY());
        cfg.set(name+"."+locationName+".z", loc.getZ());

        cfg.set(name+"."+locationName+".yaw", loc.getYaw());
        cfg.set(name+"."+locationName+".pitch", loc.getPitch());

        player.sendMessage("§7Локация §e" + name + " §7была сохраненная.");
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);

        saveCfg();
    }

    public Location getLocation(Arena arena, String locationName) {
        String name = arena.getName();
        String nameLocal = locationName;
        World w = Bukkit.getWorld(cfg.getString(name+".world"));
        double x = cfg.getDouble(name+"."+nameLocal+".x");
        double y = cfg.getDouble(name+"."+nameLocal+".y");
        double z = cfg.getDouble(name+"."+nameLocal+".z");
        Location loc = new Location(w, x, y, z);
        loc.setYaw(cfg.getInt(name+"."+nameLocal+".yaw"));
        loc.setPitch(cfg.getInt(name+"."+nameLocal+".pitch"));
        return loc;
    }
}
