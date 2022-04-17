package dev.mattrm.mc.gametools.teams;

import dev.mattrm.mc.gametools.annotations.GTService;
import dev.mattrm.mc.gametools.util.Service;

import static dev.mattrm.mc.gametools.Constants.PLUGIN_NAME;

@GTService(plugin = PLUGIN_NAME)
public class TeamService extends Service {
    private static final TeamService INSTANCE = new TeamService();

    public static TeamService getInstance() {
        return INSTANCE;
    }

    @Override
    protected void setup() {
        this.getLogger().info("Team setup");
    }

    @Override
    protected void teardown() {
        this.getLogger().info("Team teardown");
    }
}
