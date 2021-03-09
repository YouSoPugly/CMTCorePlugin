package me.pugly.cmtcore;

import me.pugly.cmtcore.Commands.CMTCommands.Reload;
import me.pugly.cmtcore.Commands.Command;
import me.pugly.cmtcore.Commands.CommandHandler;
import me.pugly.cmtcore.Commands.ScoreCommands.Add;
import me.pugly.cmtcore.Commands.ScoreCommands.Get;
import me.pugly.cmtcore.Commands.ScoreCommands.Remove;
import me.pugly.cmtcore.Commands.ScoreCommands.Set;
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

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);
        saveConfig();

        ConfigHandler.reloadConfig();

        cmtCommand = new CommandHandler("cmt", this)
                .registerCommand(new Reload());

        scoreCommand = new CommandHandler("score", this)
                .registerCommand(new Add())
                .registerCommand(new Get())
                .registerCommand(new Remove())
                .registerCommand(new Set());

        Bukkit.getPluginManager().registerEvents(new onJoin(), this);

//        if(getServer().getPluginManager().getPlugin("Citizens") == null || getServer().getPluginManager().getPlugin("Citizens").isEnabled() == false) {
//            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
//            getServer().getPluginManager().disablePlugin(this);
//            return;
//        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
