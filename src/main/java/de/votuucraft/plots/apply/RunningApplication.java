package de.votuucraft.plots.apply;

import de.votuucraft.plots.plots.Plot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RunningApplication {

    private List<UUID> watcher;
    private Plot plot;

    public RunningApplication(Plot plot) {
        this.watcher = new ArrayList<>();
        this.plot = plot;

        watcher.add(plot.getInformation().getOwner());
    }

    public void chat(Player player, String message) {

    }
    public void chat(String message) {
        for(UUID uuid : watcher) {
            if(Bukkit.getPlayer(uuid) != null) {
                Player player = Bukkit.getPlayer(uuid);

                player.sendMessage(" §8\u25cf §9Running §8| §cSystem §8- §f" + message);
            }
        }
    }

    public List<UUID> getWatcher() {
        return watcher;
    }
}
