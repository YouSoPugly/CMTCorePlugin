package me.pugly.cmtcore;

import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class CMTTeam {

    //
    //              OBJECT METHODS
    //

    private Team team;
    private Set<CMTUser> users = new HashSet<>();
    private int score = -1;

    public CMTTeam(@NotNull Team t) {
        team = t;

        teams.put(t, this);
    }

    public void addUser(CMTUser u) {
        users.add(u);
    }

    public void removeUser(CMTUser u) {
        users.remove(u);
    }

    public void updateScore() {
        score = -1;
        if (users.size() > 0) {
            score = 0;
            for (CMTUser u : users) {
                score += u.getScore();
            }
        }
    }

    public int getScore() {
        return score;
    }

    public Set<CMTUser> getUsers() {
        return users;
    }

    public CMTUser getTopUser() {
        if (users.size() == 0) {
            return null;
        }

        CMTUser out = null;
        for (CMTUser cs : users) {
            if (out == null || cs.getScore() > out.getScore()) {
                out = cs;
            }
        }

        return out;
    }

    public String getTeamName() {
        return team.getDisplayName();
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

    public static List<CMTTeam> sort() {
        List<CMTTeam> out = new ArrayList<>();

        List<CMTTeam> cmtTeams = new ArrayList<>();
        for (Team t : teams.keySet())
            cmtTeams.add(teams.get(t));

        for (CMTTeam t : cmtTeams) {
            for (int i = 0; i < out.size(); i++)
                if (t.getScore() > out.get(i).getScore()) {
                    CMTTeam temp = out.get(i);
                    out.add(i, temp);
                    out.set(i, t);
                    break;
                }

            if (!out.contains(t))
                out.add(t);
        }

        return out;
    }

    public static void register(Set<Team> teams) {
        teams.forEach(CMTTeam::getTeam);
    }
}
