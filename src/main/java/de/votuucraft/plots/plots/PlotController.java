package de.votuucraft.plots.plots;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import de.votuucraft.plots.plots.util.PlotGenManager;
import de.votuucraft.plots.util.Controller;
import de.votuucraft.plots.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

public class PlotController extends Controller {

    public PlotController(String name) {
        super(name);
        this.plotGenManager = new PlotGenManager();
    }
    public PlotGenManager plotGenManager;

    @Override
    public String toString() {
        return getCache().toString();
    }

    public Plot addEmptyPlot() {
        Plot plot = new Plot(getCache().size()+1);
        plot.newCreation();
        plot.reset();
        plot.getInformation().setClaimed(false);
        getCache().put(plot.getId(), plot);
        return plot;
    }
    public Plot byCenter(int x, int z) {
        for(Object o : getCache().values()) {
            if(o instanceof Plot) {
                Plot plot = (Plot) o;

                if(plot.getInformation().getCenter().getBlockX() == x && plot.getInformation().getCenter().getBlockZ() == z) {
                    return plot;
                }
            }
        }
        return null;
    }
    public Plot getPlot(UUID owner) {
        for(Object o : getCache().values()) {
            if(o instanceof Plot) {
                Plot plot = (Plot) o;

                if(plot.getInformation().getOwner().equals(owner)) {
                    return plot;
                }
            }
        }
        return null;
    }
    public Plot newPlot(UUID owner) {
        for(Object o : getCache().values()) {
            if(o instanceof Plot) {
                Plot plot = (Plot) o;

                if(!plot.getInformation().isClaimed()) {
                    plot.reset();
                    plot.getInformation().setClaimed(true);
                    plot.getInformation().setOwner(owner);
                    return plot;
                }
            }
        }
        Plot plot = new Plot(getCache().size()+1);
        plot.newCreation();
        plot.reset();
        plot.getInformation().setClaimed(true);
        plot.getInformation().setOwner(owner);
        getCache().put(plot.getId(), plot);
        return plot;
    }
    public Plot currentPlot(Player player) {
        Chunk chunk = player.getChunk();

        for(Object o : getCache().values()) {
            if(o instanceof Plot) {
                Plot plot = (Plot) o;
                if(plot.getInformation().getChunks().contains(chunk)) {
                    return plot;
                }
            }
        }
        return null;
    }
    public Plot currentPlot(Location location) {
        Chunk chunk = location.getChunk();

        for(Object o : getCache().values()) {
            if(o instanceof Plot) {
                Plot plot = (Plot) o;
                if(plot.getInformation().getChunks().contains(chunk)) {
                    return plot;
                }
            }
        }
        return null;
    }

    public void paste(Location center) {
        File schem = new File("./plugins/FastAsyncWorldEdit/schematics", "plot.schem");

        ClipboardFormat format = ClipboardFormats.findByFile(schem);

        Clipboard clipboard = null;

        try {
            ClipboardReader reader = format.getReader(new FileInputStream(schem));
            clipboard = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        World world = BukkitAdapter.adapt(Bukkit.getWorld("world"));

        EditSession session = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1);

        Operation operation = new ClipboardHolder(clipboard).createPaste(session).to(BlockVector3.at(center.getBlockX(), 0, center.getBlockZ())).ignoreAirBlocks(false).build();

        try {
            Operations.complete(operation);
            session.flushSession();
        } catch (WorldEditException e) {
            Logger.log("Schematic", "Cant paste §ePlot schematic §7at §e" + center.toString());
            Logger.log("Schematic", "§c§lError: §f" + e.getMessage());
        }
    }
}
