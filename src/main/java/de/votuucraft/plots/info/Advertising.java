package de.votuucraft.plots.info;

import de.votuucraft.plots.plots.Plot;
import de.votuucraft.plots.Plots;
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

                    if(id == 1) {
                        if(application.plotController().getPlot(player.getUniqueId()) != null) {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§7Du kannst dein §9Plot §7mit §9/plot submit §7abgeben"));
                        }else {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§7Du kannst ein §9Plot §7mit §9/plot get §7erstellen"));
                        }
                    }else if(id == 2) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§9§lғʀᴇᴇ ʀᴏʙᴜx? §7Gucke hier vorbei§8: §atwitter.com/nichtamon"));
                    }else if(id == 3) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§c§lʟᴇɢᴇɴᴅᴡᴀʀʀɪᴏʀ §7hat ein neues Video hochgeladen§8: §4yt.votuu.de/"));
                    }
                });
            }
        }.runTaskTimer(JavaPlugin.getPlugin(Plots.class), 20, 20);
    }
}
