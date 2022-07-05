package de.votuucraft.plots.info;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InfoBar {

    private static Map<UUID, InfoBar> infoBarMap = new HashMap<>();

    public static Map<UUID, InfoBar> getInfoBarMap() {
        return infoBarMap;
    }

    private UUID uuid;
    private BossBar bossBar;

    public InfoBar(UUID uuid) {
        this.uuid = uuid;
        this.bossBar = Bukkit.createBossBar(" ", BarColor.WHITE, BarStyle.SOLID);
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public void subscribe() {
        for(Player players : bossBar.getPlayers()) {
            bossBar.removePlayer(players);
        }
        bossBar.addPlayer(Bukkit.getPlayer(uuid));
        bossBar.setTitle("§7loading §9content§7...");
        bossBar.setVisible(true);
    }
}
