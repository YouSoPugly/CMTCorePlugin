package me.pugly.cmtcore;

import me.pugly.cmtcore.Citizens.ScoreNPCs;
import me.pugly.cmtcore.Commands.CMTCommands.Reload;
import me.pugly.cmtcore.Commands.CMTCommands.Teams;
import me.pugly.cmtcore.Commands.CMTCommands.Users;
import me.pugly.cmtcore.Commands.Command;
import me.pugly.cmtcore.Commands.CommandHandler;
import me.pugly.cmtcore.Commands.ScoreCommands.*;
import me.pugly.cmtcore.Files.ConfigHandler;
import me.pugly.cmtcore.Listeners.onJoin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import sun.security.krb5.Config;

import java.util.logging.Level;

public final class CMTCore extends JavaPlugin {

    private static JavaPlugin plugin;
    private static CommandHandler cmtCommand;
    private static CommandHandler scoreCommand;
    private static boolean citizens = false;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);
        saveConfig();

        ConfigHandler.reloadConfig();
        CMTTeam.register(ConfigHandler.getTeams());

        cmtCommand = new CommandHandler("cmt", this)
                .registerCommand(new Reload())
                .registerCommand(new Teams())
                .registerCommand(new Users());

        scoreCommand = new CommandHandler("score", this)
                .registerCommand(new Add())
                .registerCommand(new Get())
                .registerCommand(new Remove())
                .registerCommand(new Set())
                .registerCommand(new Refresh());

        Bukkit.getPluginManager().registerEvents(new onJoin(), this);

        if(getServer().getPluginManager().getPlugin("Citizens") == null || !getServer().getPluginManager().getPlugin("Citizens").isEnabled()) {
            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled, disabling npc function.");
        } else {
            citizens = true;
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                ScoreNPCs.updateTeamBoard();
                ScoreNPCs.updateIndBoard();
            }, 0L, 1200L);
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            CMTTeam.updateAllTeams();
            CMTUser.getAllPlayers().forEach(p -> CMTUser.getUser(p.getName()).updateSidebar());
        }, 0L, 20L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static boolean isCitizens() {
        return citizens;
    }
}
