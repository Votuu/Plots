package de.votuucraft.plots.commands;

import de.votuucraft.plots.Plots;
import de.votuucraft.plots.plots.Plot;
import de.votuucraft.plots.plots.util.PlotStatus;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class PlotCommand implements CommandExecutor, TabCompleter {

    private Map<String, String[]> alias;

    public PlotCommand() {
        this.alias = new HashMap<>();

        alias.put("teleport", new String[]{"teleport", "tp", "visit", "home"});
        alias.put("get", new String[]{"get", "auto", "create", "init"});
        alias.put("submit", new String[]{"submit", "finish", "ready"});
        alias.put("status", new String[]{"status", "info"});
        alias.put("clear", new String[]{"clear", "reset"});
        alias.put("delete", new String[]{"delete", "remove"});
        alias.put("deliver", new String[]{"deliver", "unfinish"});
        alias.put("usage", new String[]{"usage", "help"});
        alias.put("create-empty", new String[]{"create-empty"});
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Plots application = JavaPlugin.getPlugin(Plots.class);
        Player player = (Player) sender;

        if(args.length == 0) {
            sendHelp(sender);
            return true;
        }

        if(equals("teleport", args[0])) {
            if(application.plotController().getPlot(player.getUniqueId()) == null) {
                player.sendMessage(" §8\u25cf §9Plot §8| §7Du besitzt keinen Plot. Benutze §9/plot get§7.");
                return true;
            }

            Plot plot = application.plotController().getPlot(player.getUniqueId());

            player.teleport(plot.getInformation().getSpawn());
        }

        if(equals("get", args[0])) {
            if(application.plotController().getPlot(player.getUniqueId()) != null) {
                player.sendMessage(" §8\u25cf §9Plot §8| §7Du kannst maximal §c1 Plot §7haben.");
                return true;
            }

            Plot plot = application.plotController().newPlot(player.getUniqueId());

            player.teleport(plot.getInformation().getSpawn());

            player.sendMessage(" §8\u25cf §9Plot §8| §7Du hast du den Plot §9Plot-" + plot.getId() + "§7.");
        }

        if(Plots.applications) {
            if(equals("submit", args[0])) {
                if(application.plotController().getPlot(player.getUniqueId()) == null) {
                    player.sendMessage(" §8\u25cf §9Plot §8| §7Du besitzt keinen Plot. Benutze §9/plot get§7.");
                    return true;
                }

                Plot plot = application.plotController().getPlot(player.getUniqueId());

                if(plot.getInformation().getStatus() == PlotStatus.ACCEPT) {
                    player.sendMessage(" §8\u25cf §9Plot §8| §7Dein Plot ist bereits " + PlotStatus.ACCEPT.getName() + "§7.");
                    return true;
                }

                if(plot.getInformation().getStatus() == PlotStatus.RATING) {
                    player.sendMessage(" §8\u25cf §9Plot §8| §7Du hast dein Plot bereits abgegeben.");
                    return true;
                }

                plot.getInformation().setStatus(PlotStatus.RATING);
                player.sendMessage(" §8\u25cf §9Plot §8| §7Du hast deinen Plot §9abgegeben§7.");
            }

            if(equals("deliver", args[0])) {
                if(application.plotController().getPlot(player.getUniqueId()) == null) {
                    player.sendMessage(" §8\u25cf §9Plot §8| §7Du besitzt keinen Plot. Benutze §9/plot get§7.");
                    return true;
                }

                Plot plot = application.plotController().getPlot(player.getUniqueId());

                if(plot.getInformation().getStatus() == PlotStatus.ACCEPT) {
                    player.sendMessage(" §8\u25cf §9Plot §8| §7Dein Plot ist bereits " + PlotStatus.ACCEPT.getName() + "§7.");
                    return true;
                }

                if(plot.getInformation().getStatus() != PlotStatus.RATING) {
                    player.sendMessage(" §8\u25cf §9Plot §8| §7Du hast deinen Plot §cnicht §7zurücknehmen.");
                    return true;
                }

                plot.getInformation().setStatus(PlotStatus.BUILD);
                player.sendMessage(" §8\u25cf §9Plot §8| §7Du hast deinen Plot §9zurückgenommen§7.");
            }
        }

        if(equals("status", args[0])) {
            if(player.hasPermission("plot.info")) {
                Plot plot = application.plotController().currentPlot(player);

                if(plot == null) {
                    sender.sendMessage(" §8\u25cf §9Plot §8| §7Du bist auf keinem §9Plot§7.");
                    return true;
                }

                player.sendMessage(" §8\u25cf §9Plot §8| §7Informationen über §9Plot-" + plot.getId());
                player.sendMessage(" ");
                player.sendMessage(" §8\u25cf §9Plot §8| §7Besitzer §8- §9" + Bukkit.getOfflinePlayer(plot.getInformation().getOwner()).getName());
                player.sendMessage(" §8\u25cf §9Plot §8| §7Status §8- §9" + plot.getInformation().getStatus().getName());
                player.spigot().sendMessage(buildComponent(" §8\u25cf §9Plot §8| §7Ablehnungen §8- §9"), buildComponent("§9" + plot.getInformation().getRejections() + " Ablehnungen §7§o(Hover)", plot.getInformation().rejectionList()));

                player.spigot().sendMessage(buildComponent(" §8\u25cf §9Plot §8| §7Beispiel §8- " + (application.sample().getCache().contains(plot) ? "§a✔ Ja" : "§c✘ Nein") + " "),
                        buildComponent((player.hasPermission("plot.sample") ? "§8[§eäɴᴅᴇʀɴ§8]" : ""), "§7§oKlicke, um den Status zu ändern", (application.sample().getCache().contains(plot) ? "/samples remove" : "/samples add")));
            }
        }

        if(equals("clear", args[0])) {
            Plot plot = application.plotController().currentPlot(player);

            if(plot == null) {
                sender.sendMessage(" §8\u25cf §9Plot §8| §7Du bist auf keinem §9Plot§7.");
                return true;
            }

            if(player.getCooldown(Material.STRUCTURE_BLOCK) == 0) {
                if(player.hasPermission("plot.clear")) {
                    player.sendMessage(" §8\u25cf §9Plot §8| §7Der Plot von §9" + Bukkit.getOfflinePlayer(plot.getInformation().getOwner()).getName() + " §7wird §9geleeret§7.");
                    plot.clear();
                    player.setCooldown(Material.STRUCTURE_BLOCK, 20*10);
                }
            }else {
                player.sendMessage(" §8\u25cf §9Plot §8| §7Du kannst ein §9Plot §7nur alle §c10s §7leeren.");
            }
        }

        if(equals("delete", args[0])) {
            sender.sendMessage("delete");
        }

        if(equals("usage", args[0])) {
            sendHelp(sender);
        }

        if(equals("create-empty", args[0])) {
            Plot plot = JavaPlugin.getPlugin(Plots.class).plotController().addEmptyPlot();
            player.teleport(plot.getInformation().getSpawn());
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();

        if(args.length == 1) {
            list.add("teleport");
            list.add("get");
            list.add("submit");
            list.add("deliver");
            list.add("status");
            list.add("clear");
            list.add("delete");
            list.add("usage");

            if(Plots.applications) {
                list.add("submit");
                list.add("deliver");
            }
        }

        if(args.length == 2) {
            if(args[0].equalsIgnoreCase("status")) {

            }
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
    public TextComponent buildComponent(String content, String hover, String request) {
        TextComponent build = new TextComponent(content);
        build.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(hover)));
        build.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, request + " "));
        return build;
    }
    public TextComponent buildComponent(String content) {
        TextComponent build = new TextComponent(content);
        return build;
    }

    public void sendHelp(CommandSender sender) {
        TextComponent pre = new TextComponent(" §8\u25cf §9Plot §8| ");
        sender.sendMessage(" §8\u25cf §9Plot §8| §7Hilfe für §9/plot§8:");
        sender.sendMessage(" ");
        sender.spigot().sendMessage(pre, buildComponent("§9/plot teleport §9§o{name}",
                "§7/plot §9teleport §9§o{name}\n" +
                        "§7/plot §9tp §9§o{name}\n" +
                        "§7/plot §9visit §9§o{name}\n" +
                        "§7/plot §9home §9§o{name}\n" +
                        " \n" +
                        "§7§o{name} muss nicht angegeben werden"),
                buildComponent(" §8- §7Teleportiert dich zu dem Plot von §8\"§7name§8\""));
        sender.spigot().sendMessage(pre, buildComponent("§9/plot get",
                "§7/plot §9get\n" +
                        "§7/plot §9auto\n" +
                        "§7/plot §9create\n" +
                        "§7/plot §9init"),
                buildComponent(" §8- §7Gibt dir einen Plot"));
        sender.spigot().sendMessage(pre, buildComponent("§9/plot submit",
                "§7/plot §9submit\n" +
                        "§7/plot §9finish\n" +
                        "§7/plot §9ready"),
                buildComponent(" §8- §7Gibt deinen Plot ab, damit er bewertet wird"));
        sender.spigot().sendMessage(pre, buildComponent("§9/plot deliver",
                        "§7/plot §9deliver\n" +
                                "§7/plot §9unfinish"),
                buildComponent(" §8- §7Zieht deinen Plot von der Bewertung zurück"));
        sender.spigot().sendMessage(pre, buildComponent("§9/plot status",
                "§7/plot §9status\n" +
                        "§7/plot §9info"),
                buildComponent(" §8- §7Zeigt dir alle Infos über dein Plot"));
        sender.spigot().sendMessage(pre, buildComponent("§9/plot clear",
                "§7/plot §9clear\n" +
                        "§7/plot §9reset"),
                buildComponent(" §8- §7Setzt deinen Plot zurück"));
        sender.spigot().sendMessage(pre, buildComponent("§9/plot delete",
                "§7/plot §9delete\n" +
                        "§7/plot §9remove"),
                buildComponent(" §8- §7Lösch deinen Plot",
                        "§c§lACHTUNG: §r§7Diese Aktion kann nicht zurückgesetzt werden"));
        sender.spigot().sendMessage(pre, buildComponent("§9/plot usage",
                        "§7/plot §9usage\n" +
                                "§7/plot §9help"),
                buildComponent(" §8- §7Zeigt dir alle Argumente an"));
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
