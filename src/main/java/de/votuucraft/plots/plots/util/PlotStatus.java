package de.votuucraft.plots.plots.util;

public enum PlotStatus {

    BUILD("§eɪᴍ ʙᴀᴜ"),
    RATING("§cʙᴇᴡᴇʀᴛᴜɴɢ"),
    REBUILD("§eᴜᴍʙᴀᴜ"),
    ACCEPT("§aᴀɴɢᴇɴᴏᴍᴍᴇɴ");

    private String name;

    PlotStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
