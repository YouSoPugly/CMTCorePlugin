package me.pugly.cmtcore.Commands.CMTCommands;

import me.pugly.cmtcore.CMTTeam;
import me.pugly.cmtcore.Commands.Command;
import me.pugly.cmtcore.Files.ConfigHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Teams extends Command {
    public Teams() {
        super(Arrays.asList("teams"), "Get the currently registered teams.", "cmt.score", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        HashMap<Team, CMTTeam> teams = CMTTeam.getTeams();

        for (Team t : teams.keySet()) {

            sender.sendMessage("Team " + t.getName() + " : " + teams.get(t).getTeamName());

        }
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
