package me.pugly.cmtcore.Citizens;

import me.pugly.cmtcore.CMTTeam;
import me.pugly.cmtcore.CMTUser;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ScoreNPCs {

    static Random random = new Random();

    public static void updateTeamBoard() {

        NPC[] npcs = new NPC[12];
        for (int i = 31; i < 43; i++) {
            npcs[i-31] = CitizensAPI.getNPCRegistry().getById(i);
        }

        List<CMTTeam> sortedTeams = CMTTeam.sort();

        for (int i = 0; i < 12; i++) {

            CMTTeam team = sortedTeams.get(i);

            npcs[i].setAlwaysUseNameHologram(true);

            npcs[i].setName(team.getTeamName() + " \u00a7f\u00a7l- \u00a76" + team.getScore());

            npcs[i].getOrAddTrait(SkinTrait.class).setSkinName("Svenskpungkula");

            if (sortedTeams.get(i) != null)
                colorPodium(npcs[i].getStoredLocation(), team.getTeam().getName());

            if (sortedTeams.get(i) != null) {
                if (sortedTeams.get(i).getUsers().size() > 0) {
                    CMTUser user = (CMTUser) team.getUsers().toArray()[random.nextInt(team.getUsers().toArray().length)];
                    npcs[i].getOrAddTrait(SkinTrait.class).setSkinName(user.getPlayer().getName());
                }
            }
        }
    }

    public static void colorPodium(Location l, String color) {
        Location center = l.clone().add(0,-1,0);
        center.getBlock().setType(Material.valueOf(color.toUpperCase() + "_CONCRETE"));
        center.clone().add(1,0,0).getBlock().setType(Material.valueOf(color.toUpperCase() + "_CONCRETE"));
        center.clone().add(-1,0,0).getBlock().setType(Material.valueOf(color.toUpperCase() + "_CONCRETE"));
        center.clone().add(0,0,1).getBlock().setType(Material.valueOf(color.toUpperCase() + "_CONCRETE"));
        center.clone().add(0,0,-1).getBlock().setType(Material.valueOf(color.toUpperCase() + "_CONCRETE"));
        center.clone().add(1,0,1).getBlock().setType(Material.valueOf(color.toUpperCase() + "_WOOL"));
        center.clone().add(-1,0,1).getBlock().setType(Material.valueOf(color.toUpperCase() + "_WOOL"));
        center.clone().add(1,0,-1).getBlock().setType(Material.valueOf(color.toUpperCase() + "_WOOL"));
        center.clone().add(-1,0,-1).getBlock().setType(Material.valueOf(color.toUpperCase() + "_WOOL"));
    }

    public static void updateIndBoard() {

        List<CMTUser> top = CMTUser.sortContestants();

        NPC ind1 = CitizensAPI.getNPCRegistry().getById(26);
        NPC ind2 = CitizensAPI.getNPCRegistry().getById(27);
        NPC ind3 = CitizensAPI.getNPCRegistry().getById(28);

        if (top.size() == 0)
            return;

        while (top.size() < 3)
            top.add(top.get(0));

        SkinTrait st;

        ind1.setAlwaysUseNameHologram(true);
        ind1.setName("\u00a7f\u00a7l" + top.get(0).getPlayer().getName() + "\u00a7f - \u00a76" + top.get(0).getScore());
        st = ind1.getOrAddTrait(SkinTrait.class);
        st.setSkinName(top.get(0).getPlayer().getName());

        ind2.setAlwaysUseNameHologram(true);
        ind2.setName("\u00a7f\u00a7l" + top.get(1).getPlayer().getName() + "\u00a7f - \u00a76" + top.get(1).getScore());
        st = ind2.getOrAddTrait(SkinTrait.class);
        st.setSkinName(top.get(1).getPlayer().getName());

        ind3.setAlwaysUseNameHologram(true);
        ind3.setName("\u00a7f\u00a7l" + top.get(2).getPlayer().getName() + "\u00a7f - \u00a76" + top.get(2).getScore());
        st = ind3.getOrAddTrait(SkinTrait.class);
        st.setSkinName(top.get(2).getPlayer().getName());
    }

}
