package me.pugly.cmtcore;

import me.pugly.cmtcore.Files.ConfigHandler;
import me.pugly.cmtcore.Utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;
import java.util.stream.Collectors;

public class CMTUser {

    //
    //              OBJECT METHODS
    //

    private int score = 0;
    private CMTTeam team;
    private String player;
    private Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private Objective objective = null;
    private boolean contestant = false;

    public CMTUser(String p) {
        this.player = p;
        users.put(p, this);

        if (ConfigHandler.getAllPlayers().contains(p))
            contestant = true;

        updateSidebar();
    }

    public CMTTeam getTeam() {
        return team;
    }

    public int getScore() {
        return score;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(player);
    }

    public int getTeamScore() {
        return team.getScore();
    }

    public void updateSidebar() {

        if (objective != null)
            objective.unregister();

        objective = scoreboard.registerNewObjective("sidebar", "dummy", TextUtils.colorize(ConfigHandler.getScoreboardName()));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<String> lines = ConfigHandler.getPlayerScoreboard();

        if (!contestant) {
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

    public boolean isContestant() {
        return contestant;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    //
    //              BUILDER
    //

    public static CMTUser create(String p) {
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
        if (team == null)
            return this;
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

    public CMTUser newSidebar() {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Bukkit.getPlayer(player).setScoreboard(scoreboard);

        return this;
    }

    //
    //              STATIC METHODS
    //

    private static final HashMap<String, CMTUser> users = new HashMap<>();

    public static CMTUser getUser(String p) {
        if (users.get(p) == null && Bukkit.getPlayer(p) != null) {
            create(p);
        }

        return users.get(p);
    }

    public static void unregisterUser(CMTUser user) {
        users.remove(user.getPlayer().getName());
    }

    public static Set<Player> getAllPlayers() {
        return users.keySet().stream().map(Bukkit::getPlayer).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    public static Set<Player> getAllContestants() {
        Set<Player> out = new HashSet<>();
        for (String p : users.keySet()) {
            if (users.get(p).contestant)
                out.add(Bukkit.getPlayer(p));
        }
        return out.stream().filter(Objects::nonNull).collect(Collectors.toSet());
    }

    public static Set<String> getUsernames() {
        return users.keySet();
    }

    public static List<CMTUser> sortContestants() {
        List<CMTUser> out = new ArrayList<>();

        List<CMTUser> cmtUsers = new ArrayList<>();
        for (String p : users.keySet())
            cmtUsers.add(users.get(p));

        cmtUsers = cmtUsers.stream().filter(CMTUser::isContestant).collect(Collectors.toList());

        for (CMTUser u : cmtUsers) {
            for (int i = 0; i < out.size(); i++)
                if (u.getScore() > out.get(i).getScore()) {
                    CMTUser temp = out.get(i);
                    out.add(i, temp);
                    out.set(i, u);
                    break;
                }

            if (!out.contains(u))
                out.add(u);
        }

        return out;
    }

}
