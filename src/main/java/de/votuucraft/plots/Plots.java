package de.votuucraft.plots;

import de.votuucraft.plots.apply.ApplyManager;
import de.votuucraft.plots.info.Advertising;
import de.votuucraft.plots.plots.Plot;
import de.votuucraft.plots.plots.PlotController;
import de.votuucraft.plots.yaml.Config;
import de.votuucraft.plots.commands.PlotCommand;
import de.votuucraft.plots.commands.SamplesCommand;
import de.votuucraft.plots.listeners.AntiCheatListener;
import de.votuucraft.plots.listeners.InventoryClickListener;
import de.votuucraft.plots.listeners.PlayerJoinListener;
import de.votuucraft.plots.samples.Sample;
import de.votuucraft.plots.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Plots extends JavaPlugin {

    private PlotController plotControllerReturnment;
    private Sample sampleReturnment;
    private ApplyManager applyManager;
    public static boolean applications;

    @Override
    public void onEnable() {
        new Config(new File("./application -build/yaml/"), "config");

        Config.create("applications", true);

        Config.create("actionbar.info-3", "§c§lʟᴇɢᴇɴᴅᴡᴀʀʀɪᴏʀ §7hat ein neues Video hochgeladen§8: §4yt.votuu.de/");
        Config.create("actionbar.info-2", "§9§lғʀᴇᴇ ʀᴏʙᴜx? §7Gucke hier vorbei§8: §atwitter.com/nichtamon");
        Config.create("actionbar.info-1", "§9§lғʀᴇᴇ ʀᴏʙᴜx? §7Gucke hier vorbei§8: §atwitter.com/nichtamon");

        Config.create("plot-sign.line-4", " ");
        Config.create("plot-sign.line-3", "&9&lPlots");
        Config.create("plot-sign.line-2", "&7by &9Votuu");
        Config.create("plot-sign.line-1", " ");

        Config.create("plot-data.plots", "");

        applications = Config.getBoolean("applications");

        Logger.log("dvntr", "§9██████╗░██╗░░░██╗███╗░░██╗████████╗██████╗░");
        Logger.log("dvntr", "§9██╔══██╗██║░░░██║████╗░██║╚══██╔══╝██╔══██╗");
        Logger.log("dvntr", "§9██║░░██║╚██╗░██╔╝██╔██╗██║░░░██║░░░██████╔╝");
        Logger.log("dvntr", "§9██║░░██║░╚████╔╝░██║╚████║░░░██║░░░██╔══██╗");
        Logger.log("dvntr", "§9██████╔╝░░╚██╔╝░░██║░╚███║░░░██║░░░██║░░██║");
        Logger.log("dvntr", "§9╚═════╝░░░░╚═╝░░░╚═╝░░╚══╝░░░╚═╝░░░╚═╝░░╚═╝");

        plotControllerReturnment = new PlotController("PlotController");

        PluginManager manager = Bukkit.getPluginManager();

        manager.registerEvents(new PlayerJoinListener(), this);
        manager.registerEvents(new AntiCheatListener(), this);
        manager.registerEvents(new InventoryClickListener(), this);

        getCommand("plot").setExecutor(new PlotCommand());
        getCommand("samples").setExecutor(new SamplesCommand());

        for(String s : Config.getString("plot-data.plots").split(";")) {
            if(!(s == null || s.isEmpty())) {
                Plot plot = new Plot(Integer.parseInt(s));
                plotController().getCache().put(Integer.parseInt(s), plot);
            }
        }

        new Advertising();
        sampleReturnment = new Sample();

        applyManager = new ApplyManager();
    }

    @Override
    public void onDisable() {
        for(Object o : plotController().getCache().values()) {
            if(o instanceof Plot) {
                Plot plot = (Plot) o;

                if(!Config.getString("plot-data.plots").contains(plot.getId() + ";")) {
                    Config.set("plot-data.plots", Config.getString("plot-data.plots") + plot.getId() + ";");
                }

                plot.getInformation().save();
            }
        }
    }

    public PlotController plotController() {
        return plotControllerReturnment;
    }

    public Sample sample() {
        return sampleReturnment;
    }

    public ApplyManager applyManager() {
        return applyManager;
    }
}
