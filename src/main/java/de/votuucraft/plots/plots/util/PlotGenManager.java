package de.votuucraft.plots.plots.util;

import de.votuucraft.plots.plots.Plot;
import de.votuucraft.plots.Plots;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class PlotGenManager {

    private Direction direction;

    public PlotGenManager() {
        this.direction = Direction.NORTH;
    }

    public Location getCoords(int id) {
        Plot before = (Plot) JavaPlugin.getPlugin(Plots.class).plotController().getCache().get(id-1);

        int x = before.getInformation().getCenter().getBlockX();
        int z = before.getInformation().getCenter().getBlockZ();

        if(canMove(x, z)) {
            direction = Direction.byId(direction.next);
        }

        if(direction == Direction.NORTH) {
            z = z - 48;
        }else if(direction == Direction.EAST) {
            x = x + 48;
        }else if(direction== Direction.SOUTH) {
            z = z + 48;
        }else if(direction == Direction.WEST) {
            x = x - 48;
        }

        return new Location(Bukkit.getWorld("world"), x, -1, z);
    }

    private boolean canMove(int x, int z) {
        Direction next = Direction.byId(direction.next);

        if(next == Direction.NORTH) {
            z = z - 48;
        }else if(next == Direction.EAST) {
            x = x + 48;
        }else if(next == Direction.SOUTH) {
            z = z + 48;
        }else if(next == Direction.WEST) {
            x = x - 48;
        }

        if(JavaPlugin.getPlugin(Plots.class).plotController().byCenter(x, z) == null) {
            return true;
        }
        return false;
    }


    public enum Direction {

        NORTH(1, 2),
        EAST(2, 3),
        SOUTH(3, 4),
        WEST(4, 1);

        private int current;
        private int next;

        public static Direction byId(int i) {
            for(Direction cache : Direction.values()) {
                if(cache.current == i) {
                    return cache;
                }
            }

            return Direction.NORTH;
        }

        Direction(int current, int next) {
            this.current = current;
            this.next = next;
        }

        public int getCurrent() {
            return current;
        }

        public int getNext() {
            return next;
        }
    }
}
