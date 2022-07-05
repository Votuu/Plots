package de.votuucraft.plots.plots.util;

import de.votuucraft.plots.plots.Plot;
import de.votuucraft.plots.yaml.Config;
import de.votuucraft.plots.Plots;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlotInformation {

    private Plot plot;

    private List<Chunk> chunks;
    private boolean claimed;
    private UUID owner;
    private Location center;
    private Location spawn;
    private int rejections;
    private List<String> rejectionReasons;
    private PlotStatus status;

    public void load() {
        Config.create("plot-data.plot-" + plot.getId() + ".claimed", false);
        Config.create("plot-data.plot-" + plot.getId() + ".owner", "069a79f4-44e9-4726-a5be-fca90e38aaf5");
        Config.create("plot-data.plot-" + plot.getId() + ".center", "world;0;0;0;0;0;");
        Config.create("plot-data.plot-" + plot.getId() + ".spawn", "world;0;0;0;0;0;");
        Config.create("plot-data.plot-" + plot.getId() + ".rejections", 0);
        Config.createList("plot-data.plot-" + plot.getId() + ".rejectionReasons");
        Config.create("plot-data.plot-" + plot.getId() + ".status", false);

        center = Config.decodeLocation(Config.getString("plot-data.plot-" + plot.getId() + ".center"));
        claimed = Config.getBoolean("plot-data.plot-" + plot.getId() + ".claimed");
        owner = UUID.fromString(Config.getString("plot-data.plot-" + plot.getId() + ".owner"));
        spawn = Config.decodeLocation(Config.getString("plot-data.plot-" + plot.getId() + ".spawn"));
        rejections = Config.getInteger("plot-data.plot-" + plot.getId() + ".rejections");
        rejectionReasons = (List<String>) Config.getList("plot-data.plot-" + plot.getId() + ".rejectionReasons");

        World world = getCenter().getWorld();

        Config.createList("plot-data.plot-" + plot.getId() + ".chunks",
                Config.codeChunk(getCenter().getChunk()),
                Config.codeChunk(world.getChunkAt(getCenter().getChunk().getX() - 1, getCenter().getChunk().getZ() - 1)),
                Config.codeChunk(world.getChunkAt(getCenter().getChunk().getX(), getCenter().getChunk().getZ() - 1)),
                Config.codeChunk(world.getChunkAt(getCenter().getChunk().getX() - 1, getCenter().getChunk().getZ())));

        chunks = new ArrayList<Chunk>();

        for(String s : Config.getStringList("plot-data.plot-" + plot.getId() + ".chunks")) {
            chunks.add(Config.decodeChunk(s));
        }

        status = PlotStatus.BUILD;
    }

    public void save() {
        Config.setChunkListToString("plot-data.plot-" + plot.getId() + ".chunks", chunks);
        Config.set("plot-data.plot-" + plot.getId() + ".claimed", claimed);
        Config.set("plot-data.plot-" + plot.getId() + ".owner", owner.toString());
        Config.set("plot-data.plot-" + plot.getId() + ".center", Config.codeLocation(center));
        Config.set("plot-data.plot-" + plot.getId() + ".spawn", Config.codeLocation(spawn));
        Config.set("plot-data.plot-" + plot.getId() + ".rejections", rejections);
        Config.set("plot-data.plot-" + plot.getId() + ".rejectionReasons", rejectionReasons);
        Config.set("plot-data.plot-" + plot.getId() + ".status", 1);
    }

    public PlotInformation(Plot plot) {
        this.plot = plot;
    }

    public Plot getPlot() {
        return plot;
    }

    public void setPlot(Plot plot) {
        this.plot = plot;
    }

    public List<Chunk> getChunks() {
        return chunks;
    }

    public void setChunks(List<Chunk> chunks) {
        this.chunks = chunks;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public Location getCenter() {
        return center;
    }

    public void setCenter(Location center) {
        this.center = center;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public int getRejections() {
        return rejections;
    }

    public void setRejections(int rejections) {
        this.rejections = rejections;
    }

    public List<String> getRejectionReasons() {
        return rejectionReasons;
    }

    public void setRejectionReasons(List<String> rejectionReasons) {
        this.rejectionReasons = rejectionReasons;
    }

    public PlotStatus getStatus() {
        return status;
    }

    public void setStatus(PlotStatus status) {
        this.status = status;
        JavaPlugin.getPlugin(Plots.class).applyManager().updateSubmits();
    }

    public String rejectionList() {
        String result = "§9Ablehnungen§8:";

        for(String s : rejectionReasons) {
            result += "§7" + s;
        }

        return result;
    }
}
