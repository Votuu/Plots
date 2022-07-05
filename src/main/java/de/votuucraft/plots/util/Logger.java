package de.votuucraft.plots.util;

import org.bukkit.Bukkit;

public class Logger {

    public static void log(String pre, String message) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if(player.hasPermission("application.notify")) {
                player.sendMessage(" §8\u25cf §e" + pre + " §8| §7" + message);
            }
        });
        Bukkit.getConsoleSender().sendMessage(" §8\u25cf §e" + pre + " §8| §7" + message);
    }
}
