package me.pugly.cmtcore.Utils;

import me.pugly.cmtcore.CMTUser;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {


    private static Pattern HEX_PATTERN = Pattern.compile("#[1234567890ABCDEFabcdef]{6}");

    public static String colorize(String message) {
        Matcher matcher = HEX_PATTERN.matcher(ChatColor.translateAlternateColorCodes('&', message));
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of(matcher.group(0)).toString());
        }

        return matcher.appendTail(buffer).toString();
    }

    public static String fix(String message, CMTUser user) {
        String out = colorize(message);
        out = out.replaceAll("%player-score%", "" + user.getScore());
        out = out.replaceAll("%team-score%", "" + user.getTeam());
        out = out.replaceAll("%player%", "" + user.getPlayer().getName());
        out = out.replaceAll("%team%", "" + user.getTeam().getTeamName());

//        ArrayList<Team> sorted = sh.sortTeams();
//        out = out.replaceAll("%team-1%", "" + sorted.get(sorted.size()-1).getDisplayName());
//        out = out.replaceAll("%team-2%", "" + sorted.get(sorted.size()-2).getDisplayName());
//        out = out.replaceAll("%team-3%", "" + sorted.get(sorted.size()-3).getDisplayName());
//        out = out.replaceAll("%team-4%", "" + sorted.get(sorted.size()-4).getDisplayName());
//        out = out.replaceAll("%team-5%", "" + sorted.get(sorted.size()-5).getDisplayName());
//        HashMap<Team, Integer> added = sh.addTeams();
//        out = out.replaceAll("%team-1-score%", "" + added.get(sorted.get(sorted.size()-1)));
//        out = out.replaceAll("%team-2-score%", "" + added.get(sorted.get(sorted.size()-2)));
//        out = out.replaceAll("%team-3-score%", "" + added.get(sorted.get(sorted.size()-3)));
//        out = out.replaceAll("%team-4-score%", "" + added.get(sorted.get(sorted.size()-4)));
//        out = out.replaceAll("%team-5-score%", "" + added.get(sorted.get(sorted.size()-5)));

        return out;
    }

}
