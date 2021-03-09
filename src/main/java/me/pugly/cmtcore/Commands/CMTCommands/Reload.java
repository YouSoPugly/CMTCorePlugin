package me.pugly.cmtcore.Commands.CMTCommands;

import me.pugly.cmtcore.Commands.Command;
import me.pugly.cmtcore.Files.ConfigHandler;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class Reload extends Command {
    public Reload() {
        super(Arrays.asList("reload"), "Reload command", "cmt.reload", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ConfigHandler.reloadConfig();
        sender.sendMessage(ConfigHandler.getPrefix() + "Configuration reloaded.");
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
