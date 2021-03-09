package me.pugly.cmtcore.Utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerUtils {

    public static List<Player> getSelectorPlayers(CommandSender sender, String selector) {

        List<Player> out = new ArrayList<>();

        if (Bukkit.selectEntities(sender, selector).isEmpty())
            return null;

        for (Entity s : Bukkit.selectEntities(sender, selector)) {
            if (s instanceof  Player)
                out.add((Player) s);
        }

        return out;
    }

}
