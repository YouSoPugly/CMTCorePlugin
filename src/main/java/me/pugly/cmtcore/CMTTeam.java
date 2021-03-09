package me.pugly.cmtcore;

import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CMTTeam {

    //
    //              OBJECT METHODS
    //

    private Team team;
    private String teamName = "Old Man";
    private Set<CMTUser> users = new HashSet<>();
    private int score = 0;

    public CMTTeam(@NotNull Team t) {
        team = t;
        teamName = team.getDisplayName();

        teams.put(t, this);
    }

    public void addUser(CMTUser u) {
        users.add(u);
    }

    public void removeUser(CMTUser u) {
        users.remove(u);
    }

    public void updateScore() {
        score = 0;
        for (CMTUser u : users) {
            score += u.getScore();
        }
    }

    public int getScore() {
        return score;
    }

    public Set<CMTUser> getUsers() {
        return users;
    }

    public String getTeamName() {
        return teamName;
    }

    public Team getTeam() {
        return team;
    }

    //
    //              STATIC METHODS
    //

    private static HashMap<Team, CMTTeam> teams = new HashMap<>();

    public static CMTTeam getTeam(@NotNull Team t) {
        if (teams.get(t) == null)
            teams.put(t, new CMTTeam(t));

        return teams.get(t);
    }

    public static HashMap<Team, CMTTeam> getTeams() {
        return teams;
    }

    public static void updateAllTeams() {
        for (Team t : teams.keySet()) {
            teams.get(t).updateScore();
        }
    }
}
