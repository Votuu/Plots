package de.votuucraft.plots.samples;

import de.votuucraft.plots.Plots;
import de.votuucraft.plots.plots.Plot;
import de.votuucraft.plots.util.Logger;
import de.votuucraft.plots.yaml.Config;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Sample {

    private List<Plot> cache;
    private SamplesList list;

    public Sample() {
        this.cache = new ArrayList<>();
        this.list = new SamplesList();

        Config.create("samples.cache", "");

        for(String s : Config.getString("samples.cache").split(";")) {
            if(!(s == null || s.isEmpty())) {
                Plot plot = (Plot) JavaPlugin.getPlugin(Plots.class).plotController().getCache().get(Integer.parseInt(s.replace(";", "")));

                if(plot == null) {
                    Logger.log("§cSamples", "Plot wasnt found");
                    return;
                }

                cache.add(plot);
            }
        }
    }

    public SamplesList getList() {
        return list;
    }

    public List<Plot> getCache() {
        return cache;
    }
}
