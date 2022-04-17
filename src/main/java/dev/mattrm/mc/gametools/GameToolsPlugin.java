package dev.mattrm.mc.gametools;

import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import static dev.mattrm.mc.gametools.Constants.*;

@Plugin(name = PLUGIN_NAME, version = PLUGIN_VERSION)
@Description(PLUGIN_DESCRIPTION)
@Author(PLUGIN_AUTHOR)
@ApiVersion(ApiVersion.Target.v1_18)
public final class GameToolsPlugin extends GTPlugin {
    public GameToolsPlugin() {
        super(PLUGIN_NAME);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
