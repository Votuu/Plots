package de.votuucraft.plots.listeners;

import de.votuucraft.plots.info.InfoBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(null);

        if(InfoBar.getInfoBarMap().get(player.getUniqueId()) == null) {
            InfoBar.getInfoBarMap().put(player.getUniqueId(), new InfoBar(player.getUniqueId()));
        }

        InfoBar.getInfoBarMap().get(player.getUniqueId()).subscribe();
    }
}
