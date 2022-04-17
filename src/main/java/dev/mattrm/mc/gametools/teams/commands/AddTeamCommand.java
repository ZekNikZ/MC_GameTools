package dev.mattrm.mc.gametools.teams.commands;

import dev.mattrm.mc.gametools.annotations.GTCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.jetbrains.annotations.NotNull;

import static dev.mattrm.mc.gametools.Constants.PLUGIN_NAME;

@GTCommand(plugin = PLUGIN_NAME, commands = "addteam")
@Commands(@org.bukkit.plugin.java.annotation.command.Command(
    name = "addteam",
    desc = "Add a team",
    usage = "/addteam <id> <prefix> <name>"
))
public class AddTeamCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length < 3) {
            return false;
        }

        sender.sendMessage(ChatColor.GRAY + "Successfully created new team '" + args[0] + "'");

        return true;
    }
}
