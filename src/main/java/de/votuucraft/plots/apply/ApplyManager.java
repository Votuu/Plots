package de.votuucraft.plots.apply;

import de.votuucraft.plots.plots.Plot;
import de.votuucraft.plots.Plots;
import de.votuucraft.plots.plots.util.PlotStatus;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class ApplyManager {

    private List<Plot> submit;
    private Map<Plot, RunningApplication> running;

    public ApplyManager() {
        this.submit = new ArrayList<>();
        this.running = new HashMap<>();
    }

    public void updateSubmits() {
        submit.clear();

        for(Object o : JavaPlugin.getPlugin(Plots.class).plotController().getCache().values()) {
            if(o instanceof Plot) {
                Plot plot = (Plot) o;

                if(plot.getInformation().getStatus() == PlotStatus.RATING) {
                    submit.add(plot);
                }
            }
        }
    }

    public void run(Plot plot) {
        RunningApplication application = new RunningApplication(plot);

        running.put(plot, application);
    }

    public RunningApplication join(int id, UUID join) {
        for(Plot plot : running.keySet()) {
            if(plot.getId() == id) {
                RunningApplication application = running.get(plot.getId());
                application.getWatcher().add(join);
                application.chat("§9" + Bukkit.getOfflinePlayer(join) + " §7hat die Application betreten.");
                return application;
            }
        }

        return null;
    }
    public void leave(int id, UUID leave) {
        for(Plot plot : running.keySet()) {
            if(plot.getId() == id) {
                RunningApplication application = running.get(plot.getId());
                application.getWatcher().remove(leave);
                application.chat("§9" + Bukkit.getOfflinePlayer(leave) + " §7hat die Application verlassen.");
            }
        }
    }

    public void close(int id) {

    }

    public Map<Plot, RunningApplication> getRunning() {
        return running;
    }

    public RunningApplication byWatcher(UUID watcher) {
        for(RunningApplication all : running.values()) {
            if(all.getWatcher().contains(watcher)) {
                return all;
            }
        }

        return null;
    }
}
