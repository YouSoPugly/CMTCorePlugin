package me.pugly.cmtcore.Commands.ScoreCommands;

import me.pugly.cmtcore.CMTUser;
import me.pugly.cmtcore.Commands.Command;
import me.pugly.cmtcore.Files.ConfigHandler;
import me.pugly.cmtcore.Utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Remove extends Command {
    public Remove() {
        super(Arrays.asList("remove"), "Remove score from a player.", "cmt.score", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length <= 1 || PlayerUtils.getSelectorPlayers(sender, args[1]) == null) {
            sender.sendMessage(ConfigHandler.getPrefix() + "Please enter a valid player.");
            return;
        }

        if (args.length == 2) {
            sender.sendMessage(ConfigHandler.getPrefix() + "Please enter an amount to remove.");
            return;
        }

        int amount = 0;

        try {
            amount = Integer.parseInt(args[2]);
        } catch (Exception e) {
            sender.sendMessage(ConfigHandler.getPrefix() + "Please enter a valid amount.");
            return;
        }

        for (Player p : PlayerUtils.getSelectorPlayers(sender, args[1])) {
            CMTUser.getUser(p.getName()).removeScore(amount);
            sender.sendMessage(ConfigHandler.getPrefix() + "Set " + p.getName() + "'s score to " + CMTUser.getUser(p.getName()).getScore() + ".");
        }
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
