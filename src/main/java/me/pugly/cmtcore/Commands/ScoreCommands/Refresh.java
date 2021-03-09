package me.pugly.cmtcore.Commands.ScoreCommands;

import me.pugly.cmtcore.CMTCore;
import me.pugly.cmtcore.CMTTeam;
import me.pugly.cmtcore.CMTUser;
import me.pugly.cmtcore.Citizens.ScoreNPCs;
import me.pugly.cmtcore.Commands.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class Refresh extends Command {
    public Refresh() {
        super(Arrays.asList("reload", "refresh"), "Reload the scoreboards.", "cmt.score", false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        CMTTeam.updateAllTeams();
        CMTUser.getAllPlayers().forEach(p -> CMTUser.getUser(p).updateSidebar());
        if (CMTCore.isCitizens()) {
            ScoreNPCs.updateTeamBoard();
            ScoreNPCs.updateIndBoard();
        }
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
