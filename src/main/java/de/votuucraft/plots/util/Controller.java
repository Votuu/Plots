package de.votuucraft.plots.util;

import de.votuucraft.plots.Plots;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public abstract class Controller {

    private Map<Object, Object> cache;
    protected Plots instance;
    private boolean ready;

    public Controller(String name) {
        this.instance = JavaPlugin.getPlugin(Plots.class);
        Logger.log("Controller", "§e" + name + " §7is being §eprepared");
        ready = false;

        cache = new HashMap<>();

        try {
            cache.put(-1, "test");
            cache.clear();
            Logger.log("Controller", "§e" + name + " §7passed §9test");
        }catch (Exception e) {
            Logger.log("Controller", "§e" + name + " §7didnt passed §etest");
            Logger.log("Controller", "§c§lError: §f" + e.getMessage());
            ready = false;
            return;
        }

        Logger.log("Controller", "§e" + name + " §7was successful §estarted");
        ready = true;
    }

    public Map<Object, Object> getCache() {
        return cache;
    }

    public Object byKey(Object key) {
        return cache.get(key);
    }
}
