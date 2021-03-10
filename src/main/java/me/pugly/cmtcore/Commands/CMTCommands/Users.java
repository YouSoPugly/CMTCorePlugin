package me.pugly.cmtcore.Commands.CMTCommands;

import me.pugly.cmtcore.CMTTeam;
import me.pugly.cmtcore.CMTUser;
import me.pugly.cmtcore.Commands.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Users extends Command {
    public Users() {
        super(Arrays.asList("users"), "Get the currently stored usernames.", "cmt.score", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        for (String s : CMTUser.getUsernames()) {

            sender.sendMessage("User: " + s + "  ?Online: " + ((Bukkit.getPlayer(s) == null) ? "false" : "true") + "  ?Contestant: " + CMTUser.getUser(s).isContestant());
            if (Bukkit.getPlayer(s) != null) {
                sender.sendMessage("?Score: " + CMTUser.getUser(s).getScore() + "  ?Team: " + ((CMTUser.getUser(s).getTeam() == null) ? "N/A" : CMTUser.getUser(s).getTeam().getTeamName()));
            }
        }

    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
