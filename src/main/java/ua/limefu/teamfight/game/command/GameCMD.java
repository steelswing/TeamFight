package ua.limefu.teamfight.game.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.limefu.teamfight.game.Arena;
import ua.limefu.teamfight.game.ArenaList;

public class GameCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (args.length < 2) {
                        sender.sendMessage("Недостаточно аргументов!");
                    }

                    String name = args[1];
                    Arena arena = new Arena(name);
                    ArenaList.arenas.add(arena);
                    sender.sendMessage("Арена создана!");
                    return true;
                } else if (args[0].equalsIgnoreCase("setLobby")) {
                    if (args.length < 2) {
                        sender.sendMessage("Недостаточно аргументов!");
                    }
                    Arena arena = ArenaList.get(args[1]);
                    if (arena == null) {
                        sender.sendMessage("Арена не найдена");
                    } else {
                        arena.setLobby(((Player) sender).getLocation());
                        sender.sendMessage("Лобби установлено!");
                        return true;
                    }
                } else {
                    if (args[0].equalsIgnoreCase("join")) {
                        Arena arena = ArenaList.get(args[1]);

                        if (arena == null) {
                            sender.sendMessage("Арены не существует");
                            return false;
                        }
                        arena.join((Player) sender);
                        return true;
                    } else if (args[0].equalsIgnoreCase("leave")) {
                        Arena arena = ArenaList.getPlayer((Player) sender);
                        if (arena == null) {
                            sender.sendMessage("Ты не на арене!");
                            return true;
                        }
                        arena.quit((Player) sender);
                    }
                }
        }
        return false;
    }
}
