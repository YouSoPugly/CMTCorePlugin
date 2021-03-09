package me.pugly.cmtcore;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CMTUser {

    //
    //              OBJECT METHODS
    //

    private int score = 0;
    private CMTTeam team;
    private Player player;

    public CMTUser(Player p) {
        this.player = p;
        users.put(p, this);
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

}
