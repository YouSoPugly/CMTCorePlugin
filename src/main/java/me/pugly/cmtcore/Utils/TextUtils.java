package me.pugly.cmtcore.Utils;

import me.pugly.cmtcore.CMTTeam;
import me.pugly.cmtcore.CMTUser;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        //out = out.replaceAll("%team-score%", "" + user.getTeam().getScore());
        out = out.replaceAll("%player%", "" + user.getPlayer().getName());
        //out = out.replaceAll("%team%", "" + user.getTeam().getTeamName());

        List<CMTTeam> sorted = CMTTeam.sort();

        out = out.replaceAll("%team-1%", "" + sorted.get(0).getTeamName());
        out = out.replaceAll("%team-2%", "" + sorted.get(1).getTeamName());
        out = out.replaceAll("%team-3%", "" + sorted.get(2).getTeamName());
        out = out.replaceAll("%team-4%", "" + sorted.get(3).getTeamName());
        out = out.replaceAll("%team-5%", "" + sorted.get(4).getTeamName());

        out = out.replaceAll("%team-1-score%", "" + sorted.get(0).getScore());
        out = out.replaceAll("%team-2-score%", "" + sorted.get(1).getScore());
        out = out.replaceAll("%team-3-score%", "" + sorted.get(2).getScore());
        out = out.replaceAll("%team-4-score%", "" + sorted.get(3).getScore());
        out = out.replaceAll("%team-5-score%", "" + sorted.get(4).getScore());

        return out;
    }

}
