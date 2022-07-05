package de.votuucraft.plots.listeners;

import de.votuucraft.plots.Plots;
import de.votuucraft.plots.plots.Plot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if((event.getCurrentItem() == null || event.getClickedInventory() == null)) {
            return;
        }

        Plots application = JavaPlugin.getPlugin(Plots.class);

        if(event.getClickedInventory() == application.sample().getList().getInventory()) {
            event.setCancelled(true);

            if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§7Plot von §9")) {
                Plot plot = application.plotController().getPlot(UUID.fromString(event.getCurrentItem().getItemMeta().getLocalizedName()));
                //application.applyController().getCache().put(player.getUniqueId(), new Apply(player.getUniqueId(), plot));

                player.teleport(plot.getInformation().getSpawn());
                player.closeInventory();
            }
        }

        /*if(event.getClickedInventory() == application.applyController().getList().getInventory()) {
            event.setCancelled(true);

            if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§7Plot von §9")) {
                Plot plot = application.plotController().getPlot(UUID.fromString(event.getCurrentItem().getItemMeta().getLocalizedName()));

                player.teleport(plot.getInformation().getSpawn());
                //application.applyController().getWorking().add(plot);
                player.sendMessage(" §8\u25cf §9Apply §8| §7Du §9bewertest§7 das Plot von §9" + Bukkit.getOfflinePlayer(plot.getInformation().getOwner()).getName() + " §7.");
                player.sendMessage(" ");
                Bukkit.dispatchCommand(player, "plot status");
                player.closeInventory();
            }
        }*/
    }
}
