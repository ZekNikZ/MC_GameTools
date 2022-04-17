package dev.mattrm.mc.gametools;

import dev.mattrm.mc.gametools.annotations.GTCommand;
import dev.mattrm.mc.gametools.annotations.GTService;
import dev.mattrm.mc.gametools.util.Service;
import org.atteo.classindex.ClassFilter;
import org.atteo.classindex.ClassIndex;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GTPlugin extends JavaPlugin {
    private final Logger LOGGER = this.getLogger();
    private final String pluginId;

    public GTPlugin(String pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    public void onEnable() {
        // Load services
        PluginManager pluginManager = this.getServer().getPluginManager();
        for (Service service : getServices(this.pluginId)) {
            service.init(this);
            pluginManager.registerEvents(service, this);
        }

        // Load commands
        for (GTCommand.Data command : getCommands(this.pluginId)) {
            this.getCommand(command.name()).setExecutor(command.executor());
        }
    }

    @Override
    public void onDisable() {

    }

    private List<Service> getServices(String pluginId) {
        List<Service> services = new ArrayList<>();

        ClassFilter.only().classes().from(ClassIndex.getAnnotated(GTService.class, this.getClassLoader())).forEach(clazz -> {
            LOGGER.info("Found potential service class " + clazz.getCanonicalName());

            // Filter by pluginId
            if (!clazz.getAnnotation(GTService.class).plugin().equals(pluginId)) {
                return;
            }

            if (!Service.class.isAssignableFrom(clazz)) {
                LOGGER.warning("Class " + clazz.getCanonicalName() + " is marked as GTService but does not extend Service.");
                return;
            }

            Object instance;
            try {
                Method method = clazz.getMethod("getInstance");
                if (!method.getReturnType().equals(clazz) || !Modifier.isStatic(method.getModifiers())) {
                    LOGGER.warning("Class " + clazz.getCanonicalName() + " does not have a correct getInstance() method.");
                    return;
                }

                LOGGER.info("Loading service class " + clazz.getCanonicalName());
                instance = method.invoke(null);
            } catch (NoSuchMethodException e) {
                LOGGER.warning("Class " + clazz.getCanonicalName() + " is marked as GTService but does not have a getInstance() method.");
                return;
            } catch (InvocationTargetException | IllegalAccessException e) {
                LOGGER.warning("Could not get instance of class " + clazz.getCanonicalName());
                LOGGER.log(Level.WARNING, e.getMessage(), e);
                return;
            }

            services.add((Service) instance);
        });

        return services;
    }

    private List<GTCommand.Data> getCommands(String pluginId) {
        List<GTCommand.Data> commands = new ArrayList<>();

        ClassFilter.only().classes().withPublicDefaultConstructor().from(ClassIndex.getAnnotated(GTCommand.class, this.getClassLoader())).forEach(clazz -> {
            // Filter by pluginId
            if (!clazz.getAnnotation(GTCommand.class).plugin().equals(pluginId)) {
                return;
            }

            CommandExecutor instance;
            try {
                Constructor<?> ctor = clazz.getConstructor();
                instance = (CommandExecutor) ctor.newInstance();
            } catch (NoSuchMethodException e) {
                LOGGER.severe("No default constructor found for class " + clazz.getCanonicalName());
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                return;
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                LOGGER.severe("Could not construct class " + clazz.getCanonicalName());
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                return;
            }

            Arrays.stream(clazz.getAnnotation(GTCommand.class).commands()).forEach(command -> {
                commands.add(new GTCommand.Data(command, instance));
                LOGGER.info("Found command /" + command + " - " + clazz.getCanonicalName());
            });
        });

        return commands;
    }
}
