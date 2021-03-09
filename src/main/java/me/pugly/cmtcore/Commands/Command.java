package me.pugly.cmtcore.Commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class Command {

    public final List<String> aliases;
    public final String description;
    public final String permission;
    public final boolean player;
    public boolean enabled = true;

    public Command(@NotNull List<String> aliases, @NotNull String description, @NotNull String permission, boolean player) {
        this.aliases = aliases;
        this.description = description;
        this.permission = permission;
        this.player = player;
    }

    public abstract void execute(CommandSender sender, String[] args); // What happens when this subcommand is run

    public abstract List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args); // What happens when the player hits tab. return a List<String> that contains the tab complete options
}

