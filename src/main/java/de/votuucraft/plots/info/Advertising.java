package de.votuucraft.plots.info;

import de.votuucraft.plots.plots.Plot;
import de.votuucraft.plots.Plots;
import de.votuucraft.plots.yaml.Config;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Advertising {

    private int state;
    private int id;

    public Advertising() {
        Plots application = JavaPlugin.getPlugin(Plots.class);

        this.state = 0;
        this.id = 1;

        new BukkitRunnable() {
            @Override
            public void run() {
                if(state == 10) {
                    state = 0;
                    
                    if(id == 3) {
                        id = 1;
                    }else {
                        id++;
                    }
                }else {
                    state++;
                }
                Bukkit.getOnlinePlayers().forEach(player -> {
                    InfoBar bar = InfoBar.getInfoBarMap().get(player.getUniqueId());
                    Plot plot = application.plotController().currentPlot(player);

                    if(plot == null) {
                        bar.getBossBar().setColor(BarColor.WHITE);
                        bar.getBossBar().setTitle("§7Du bist auf §ckeinem §7Plot");
                    }else if(!plot.getInformation().isClaimed()) {
                        bar.getBossBar().setColor(BarColor.RED);
                        bar.getBossBar().setTitle("§7Plot hat §ckeinen §7Besitzer");
                    }else {
                        bar.getBossBar().setColor(BarColor.GREEN);
                        bar.getBossBar().setTitle("§7Plot von §a" + Bukkit.getOfflinePlayer(plot.getInformation().getOwner()).getName());
                    }

                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Config.getString("actionbar.info-" + id)));
                });
            }
        }.runTaskTimer(JavaPlugin.getPlugin(Plots.class), 20, 20);
    }
}
