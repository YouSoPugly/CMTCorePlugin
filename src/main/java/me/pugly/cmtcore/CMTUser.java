package me.pugly.cmtcore;

import me.pugly.cmtcore.Files.ConfigHandler;
import me.pugly.cmtcore.Utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class CMTUser {

    //
    //              OBJECT METHODS
    //

    private int score = 0;
    private CMTTeam team;
    private Player player;
    private Scoreboard scoreboard;
    private Objective objective;
    private boolean contestant = false;

    public CMTUser(Player p) {
        this.player = p;
        users.put(p, this);

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        p.setScoreboard(scoreboard);
        updateSidebar();
    }

    public CMTTeam getTeam() {
        return team;
    }

    public int getScore() {
        return score;
    }

    public Player getPlayer() {
        return player;
    }

    public int getTeamScore() {
        return team.getScore();
    }

    public void updateSidebar() {
        objective.unregister();
        objective = scoreboard.registerNewObjective("sidebar", "dummy", TextUtils.colorize(ConfigHandler.getScoreboardName()));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<String> lines = ConfigHandler.getPlayerScoreboard();

        if (player.hasPermission("cmt.score")) {
            lines = ConfigHandler.getStaffScoreboard();
        }

        StringBuilder append = new StringBuilder("&7");

        for (int i = 0; i < lines.size(); i++) {
            String text = TextUtils.fix(lines.get(i) + append, this);

            if (text.length() > 40)
                text = text.substring(0, 40);

            objective.getScore(text).setScore(lines.size() - 1 - i);
            append.append("&7");
        }
    }

    //
    //              BUILDER
    //

    public static CMTUser create(Player p) {
        if (users.containsKey(p))
            return getUser(p);

        return new CMTUser(p);
    }

    public CMTUser setScore(int score) {
        this.score = score;
        return this;
    }

    public CMTUser addScore(int score) {
        this.score += score;
        return this;
    }

    public CMTUser removeScore(int score) {
        this.score -= score;
        return this;
    }

    public CMTUser setTeam(CMTTeam team) {
        if (this.team != null) {
            this.team.removeUser(this);
        }
        this.team = team;
        team.addUser(this);
        return this;
    }

    public CMTUser setContestant(boolean contestant) {
        this.contestant = contestant;
        return this;
    }

    //
    //              STATIC METHODS
    //

    private static HashMap<Player, CMTUser> users = new HashMap<>();

    public static CMTUser getUser(Player p) {
        if (users.get(p) == null) {
            registerUser(create(p));
        }

        return users.get(p);
    }

    public static void registerUser(CMTUser user) {
        if (!users.containsKey(user.getPlayer()))
            users.put(user.getPlayer(), user);
    }

    public static void unregisterUser(CMTUser user) {
        users.remove(user.getPlayer());
    }

    public static Set<Player> getAllPlayers() {
        return users.keySet();
    }

    public static Set<Player> getAllContestants() {
        Set<Player> out = new HashSet<>();
        for (Player p : users.keySet()) {
            if (users.get(p).contestant)
                out.add(p);
        }
        return out;
    }

    public static List<CMTUser> sortContestants() {
        List<CMTUser> out = new ArrayList<>();

        Player[] keys = users.keySet().toArray(new Player[0]);

        for (int i = 0; i < keys.length; i++) {
            if (out.size() == 0 || out.size() < i+1 || users.get(keys[i]).getScore() < out.get(i).getScore()) {
                out.add(i, users.get(keys[i]));
            }
        }

        return out;
    }

}
