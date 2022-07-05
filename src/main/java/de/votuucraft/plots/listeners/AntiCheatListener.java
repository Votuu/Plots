package de.votuucraft.plots.listeners;

import de.votuucraft.plots.Plots;
import de.votuucraft.plots.plots.Plot;
import de.votuucraft.plots.yaml.Config;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiCheatListener implements Listener {

    /*
        * TODO:
        *  BlockExplodeEvent -
        *  BlockGrowEvent -
        *  BlockPhysicsEvent -
        *  BlockPistonEvent -
        *  BlockPistonExtendEvent -
        *  BlockSpreadEvent -
        *  LeavesDecayEvent -
        *  NotePlayEvent -
        *  SignChangeEvent -
        *  SpongeAbsortEvent
        *  EnchantItemEvent
        *  CreatureSpawnEvent -
        *  PlayerFishEvent
    */

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        Plot plot = JavaPlugin.getPlugin(Plots.class).plotController().currentPlot(event.getBlock().getLocation());

        event.setDropItems(false);

        if(plot == null) {
            if(!player.hasPermission("plot.break")) {
                event.setCancelled(true);
            }
            return;
        }

        if(plot.getInformation().getOwner().equals(player.getUniqueId())) {
            return;
        }

        if(!player.hasPermission("plot.break")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        Plot plot = JavaPlugin.getPlugin(Plots.class).plotController().currentPlot(event.getBlock().getLocation());

        if(plot == null) {
            if(!player.hasPermission("plot.break")) {
                event.setCancelled(true);
            }
            return;
        }

        if(plot.getInformation().getOwner().equals(player.getUniqueId())) {
            return;
        }

        if(!player.hasPermission("plot.place")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if(event.getEntity() instanceof FallingBlock) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if(!player.hasPermission("plot.drop")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockRedstone(BlockRedstoneEvent event) {
        event.setNewCurrent(0);
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockGrow(BlockGrowEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockSpread(BlockSpreadEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onNotePlay(NotePlayEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();

        if(!player.hasPermission("plot.sign")) {
            event.setLine(0, Config.getString("plot-sign.line-4"));
            event.setLine(1, Config.getString("plot-sign.line-3"));
            event.setLine(2, Config.getString("plot-sign.line-2"));
            event.setLine(3, Config.getString("plot-sign.line-1"));
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        event.setCancelled(true);
    }
}
