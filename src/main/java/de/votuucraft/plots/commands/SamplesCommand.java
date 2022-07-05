package de.votuucraft.plots.commands;

import de.votuucraft.plots.Plots;
import de.votuucraft.plots.plots.Plot;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SamplesCommand implements CommandExecutor, TabCompleter {

    private Map<String, String[]> alias;

    public SamplesCommand() {
        this.alias = new HashMap<>();

        alias.put("overview", new String[]{"overview", "listall", "list"});
        alias.put("add", new String[]{"add"});
        alias.put("remove", new String[]{"remove"});
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Plots application = JavaPlugin.getPlugin(Plots.class);
        Player player = (Player) sender;

        if(args.length == 0) {
            player.openInventory(application.sample().getList().getInventory());
            return true;
        }

        if(equals("overview", args[0])) {
            player.openInventory(application.sample().getList().getInventory());
        }

        if(equals("add", args[0])) {
            Plot plot = application.plotController().currentPlot(player);

            if(plot == null) {
                player.sendMessage(" §8\u25cf §9Plot §8| §7Du befindest dich auf keinem Plot.");
                return false;
            }

            if(args.length == 1) {
                player.sendMessage(" §8\u25cf §9Plot §8| §7Möchtest du wirklich diesen Plot als Beispiel machen? Füge in dem Chat §9confirm §7hinzu.");
                return false;
            }

            if(args[1].equalsIgnoreCase("confirm")) {
                if(application.sample().getCache().contains(plot)) {
                    player.sendMessage(" §8\u25cf §9Plot §8| §7Dieser Plot ist bereits als §9Beispielt §7markiert.");
                    return false;
                }

                application.sample().getCache().add(plot);
                application.sample().getList().update();
                player.sendMessage(" §8\u25cf §9Plot §8| §7Dieser Plot wurde als §9Beispiel §7markiert.");
            }else {
                player.sendMessage(" §8\u25cf §9Plot §8| §7Bitte bestätige mit §9/samples add confirm§7!");
            }
        }
        if(equals("remove", args[0])) {
            Plot plot = application.plotController().currentPlot(player);

            if(plot == null) {
                player.sendMessage(" §8\u25cf §9Plot §8| §7Du befindest dich auf keinem Plot.");
                return false;
            }

            if(args.length == 1) {
                player.sendMessage(" §8\u25cf §9Plot §8| §7Soll dieser Plot wirklich kein Beispielt mehr sein? Füge in dem Chat §9confirm §7hinzu.");
                return false;
            }

            if(args[1].equalsIgnoreCase("confirm")) {
                if(!application.sample().getCache().contains(plot)) {
                    player.sendMessage(" §8\u25cf §9Plot §8| §7Dieser Plot ist nicht als §9Beispielt §7markiert.");
                    return false;
                }

                application.sample().getCache().remove(plot);
                application.sample().getList().update();
                player.sendMessage(" §8\u25cf §9Plot §8| §7Dieser Plot ist nun nichtmehr als §9Beispiel §7markiert.");
            }else {
                player.sendMessage(" §8\u25cf §9Plot §8| §7Bitte bestätige mit §9/samples remove confirm§7!");
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();

        if(args.length == 1) {
            list.add("overview");
        }

        List<String> completer = new ArrayList<>();
        String arg = args[args.length-1].toLowerCase();

        for(String s : list) {
            String work = s.toLowerCase();
            if(work.startsWith(arg)) {
                completer.add(s);
            }
        }

        return completer;
    }

    public TextComponent buildComponent(String content, String hover) {
        TextComponent build = new TextComponent(content);
        build.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(hover)));
        return build;
    }
    public TextComponent buildComponent(String content) {
        TextComponent build = new TextComponent(content);
        return build;
    }

    public boolean equals(String key, String value) {
        for(String s : alias.get(key)) {
            if(s.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
