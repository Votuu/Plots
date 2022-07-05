package de.votuucraft.plots.plots;

import de.votuucraft.plots.Plots;
import de.votuucraft.plots.plots.util.PlotInformation;
import de.votuucraft.plots.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class Plot {

    private int id;
    private PlotInformation information;

    public Plot(int id) {
        this.id = id;

        this.information = new PlotInformation(this);

        information.load();

        Logger.log("Plot", "§9Plot-" + id + " §7wird §9erstellt§7...");

        JavaPlugin.getPlugin(Plots.class).plotController().getCache().put(id, this);
    }

    public void newCreation() {
        World world = Bukkit.getWorld("world");

        PlotController controller = JavaPlugin.getPlugin(Plots.class).plotController();

        if(id == 1) {
            information.setCenter(new Location(world, 0, -1, 0));
            information.setSpawn(new Location(world, 0, 1, 0));
        }else {
            information.setCenter(JavaPlugin.getPlugin(Plots.class).plotController().plotGenManager.getCoords(id));
            information.setSpawn(information.getCenter());
        }

        information.getChunks().clear();

        information.getChunks().add(information.getCenter().getChunk());
        information.getChunks().add(world.getChunkAt(information.getCenter().getChunk().getX() - 1, information.getCenter().getChunk().getZ() - 1));
        information.getChunks().add(world.getChunkAt(information.getCenter().getChunk().getX(), information.getCenter().getChunk().getZ() - 1));
        information.getChunks().add(world.getChunkAt(information.getCenter().getChunk().getX() - 1, information.getCenter().getChunk().getZ()));

        controller.paste(information.getCenter());
    }
    public void clear() {
        PlotController controller = JavaPlugin.getPlugin(Plots.class).plotController();

        controller.paste(information.getCenter());
    }

    public void reset() {
        clear();
        information.setRejections(0);
        information.getRejectionReasons().clear();
    }

    public PlotInformation getInformation() {
        return information;
    }

    public int getId() {
        return id;
    }
}
