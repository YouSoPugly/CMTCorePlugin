package me.pugly.cmtcore.Files;

import me.pugly.cmtcore.CMTCore;
import me.pugly.cmtcore.Utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ConfigHandler {

    private static FileConfiguration config = CMTCore.getPlugin().getConfig();
    private static HashMap<String, Team> playerTeamMap = new HashMap<>();

    public static void reloadConfig() {

        CMTCore.getPlugin().reloadConfig();
        config = CMTCore.getPlugin().getConfig();

        Set<String> keys = config.getConfigurationSection("teams").getKeys(false);
        System.out.println(keys.toArray());

        for (String k : keys) {
            for (String p : getPlayers(k)) {
                playerTeamMap.put(p, (Bukkit.getScoreboardManager().getMainScoreboard().getTeam(k) == null) ?
                        Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(k) : Bukkit.getScoreboardManager().getMainScoreboard().getTeam(k));
            }
        }

    }

    public static String getPrefix() {

        FileConfiguration conf = config;

        String prefix = conf.getString("prefix");
        prefix = TextUtils.colorize(prefix);

        return prefix + " ";
    }

    public static String getScoreboardName() {
        return config.getString("scoreboard-title");
    }

    public static List<String> getStaffScoreboard() {
        return config.getStringList("staff-scoreboard");
    }

    public static List<String> getPlayerScoreboard() {
        return config.getStringList("scoreboard");
    }


    public static List<String> getPlayers(String team) {
        return config.getConfigurationSection("teams").getStringList(team);
    }

    public static Set<String> getAllPlayers() {
        return playerTeamMap.keySet();
    }

    public static Team getTeam(Player p) {
        return playerTeamMap.get(p.getName());
    }

}
