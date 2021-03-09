package me.pugly.cmtcore.Commands.ScoreCommands;

import me.pugly.cmtcore.CMTUser;
import me.pugly.cmtcore.Commands.Command;
import me.pugly.cmtcore.Files.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Get extends Command {
    public Get() {
        super(Arrays.asList("get"), "Get a player's score.", "cmt.score", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length < 2 || Bukkit.getPlayer(args[1]) == null)
            sender.sendMessage(ConfigHandler.getPrefix() + "Please enter a valid player.");

        sender.sendMessage(ConfigHandler.getPrefix() + args[1] + " has " + CMTUser.getUser(Bukkit.getPlayer(args[1])).getScore() + " points.");
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {

        List<String> out = new ArrayList<>();

        if (args.length == 2) {

            for (Player p : CMTUser.getAllPlayers()) {
                out.add(p.getName());
            }
        }

        return out;
    }
}
