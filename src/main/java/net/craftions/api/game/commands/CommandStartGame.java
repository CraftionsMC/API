/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.api.game.commands;

import net.craftions.api.game.Game;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandStartGame implements CommandExecutor {

    public static Game game = null;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(game == null){
            sender.sendMessage("§7There is §cno §7game that can be started!");
        }else {
            if(args.length == 0){
                game.start(10);
                sender.sendMessage("§aSuccessfully §7started the game §e" + game.getName());
            }else {
                try {
                    game.start(Integer.parseInt(args[0]) + 1);
                    sender.sendMessage("§aSuccessfully §7started the game §e" + game.getName());
                }catch (NumberFormatException ex){
                    sender.sendMessage("§c" + args[0] + " §7is §cnot §7a valid number.");
                }
            }
        }
        return true;
    }
}
