package me.pugly.cmtcore.Listeners;

import me.pugly.cmtcore.CMTTeam;
import me.pugly.cmtcore.CMTUser;
import me.pugly.cmtcore.Files.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onJoin implements Listener {

    @EventHandler
    public void listener(PlayerJoinEvent e) {

        if (ConfigHandler.getAllPlayers().contains(e.getPlayer().getName())) {
            e.getPlayer().addScoreboardTag("cmt");

            CMTUser.registerUser(
                CMTUser.create(e.getPlayer())
                        .setTeam(CMTTeam.getTeam(ConfigHandler.getTeam(e.getPlayer())))
            );

            System.out.println(ConfigHandler.getTeam(e.getPlayer()).getName());
            ConfigHandler.getTeam(e.getPlayer()).addEntry(e.getPlayer().getName());
        }

    }

}
