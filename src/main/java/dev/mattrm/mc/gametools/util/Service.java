package dev.mattrm.mc.gametools.util;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public abstract class Service implements Listener {
    protected JavaPlugin plugin;

    public final void init(JavaPlugin plugin) {
        this.plugin = plugin;
        this.setup();
    }

    public final void destroy() {
        this.teardown();
    }

    public final JavaPlugin getPlugin() {
        return this.plugin;
    }

    public final Logger getLogger() {
        return this.plugin.getLogger();
    }

    protected void setup() {}

    protected void teardown() {}
}
