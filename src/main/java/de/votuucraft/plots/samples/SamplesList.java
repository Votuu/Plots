package de.votuucraft.plots.samples;

import de.votuucraft.plots.Plots;
import de.votuucraft.plots.plots.Plot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class SamplesList {

    private Inventory inventory;

    public SamplesList() {
        this.inventory = Bukkit.createInventory(null, 5*9, "§9Beispiele §8□ §7Übersicht");
    }

    public void update() {
        inventory.clear();

        inventory.setItem(0, air());
        inventory.setItem(9, air());
        inventory.setItem(18, air());
        inventory.setItem(27, air());
        inventory.setItem(36, air());

        for(Plot plot : JavaPlugin.getPlugin(Plots.class).sample().getCache()) {
            inventory.addItem(sample(plot));
        }
    }

    private ItemStack air() {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }
    private ItemStack sample(Plot plot) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        OfflinePlayer player = Bukkit.getOfflinePlayer(plot.getInformation().getOwner());

        meta.setOwningPlayer(player);
        meta.setDisplayName("§7Plot von §9" + player.getName());
        meta.setLocalizedName(player.getUniqueId().toString());
        meta.setLore(Arrays.asList(
                " ",
                "§7§oKlick, um dich zu teleportieren"));

        item.setItemMeta(meta);
        return item;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
