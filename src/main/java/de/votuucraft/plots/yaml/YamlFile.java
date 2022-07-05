package de.votuucraft.plots.yaml;

import de.votuucraft.plots.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YamlFile {

    private static File file;
    private static YamlConfiguration config;

    public YamlFile(File dir, String file) {
        Logger.log("Yaml", "§e" + file + ".yml §7is being started");

        if(!dir.exists()) {
            try {
                dir.mkdirs();
                Logger.log("Yaml", "§e" + file + ".yml §7created the §edirectory§7");
            }catch (Exception e) {
                Logger.log("Yaml", "§e" + file + ".yml §7missed §7the §ctest");
                Logger.log("Yaml", "§c§lException: §f" + e.getMessage());
                return;
            }
        }

        this.file = new File(dir, file + ".yml");
        if(!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public static YamlConfiguration getConfig() {
        return config;
    }

    public static Object get(String path) {
        if(!contains(path))return null;
        return config.get(path);
    }
    public static String getString(String path) {
        if(!contains(path))return null;
        return config.getString(path);
    }
    public static Integer getInteger(String path) {
        if(!contains(path))return null;
        return config.getInt(path);
    }
    public static Boolean getBoolean(String path) {
        if(!contains(path))return null;
        return config.getBoolean(path);
    }

    public static List<?> getList(String path) {
        if(!contains(path))return null;
        return config.getList(path);
    }
    public static List<String> getStringList(String path) {
        if(!contains(path))return null;
        return config.getStringList(path);
    }
    public static List<Integer> getIntegerList(String path) {
        if(!contains(path))return null;
        return config.getIntegerList(path);
    }

    public static boolean contains(String path) {
        return config.contains(path);
    }

    public static void set(String path, Object value) {
        config.set(path, value);
        save();
    }
    public static void create(String path, Object value) {
        if(contains(path))return;

        set(path, value);
    }
    public static void createList(String path) {
        List<String> test = new ArrayList<>();

        test.add("test1");
        test.add("test2");

        create(path, test);
    }
    public static void createList(String path, Object... content) {
        List<Object> test = new ArrayList<>(Arrays.asList(content));

        create(path, test);
    }
    public static void setList(String path, Object... content) {
        List<Object> test = new ArrayList<>(Arrays.asList(content));

        set(path, test);
    }
    public static void setChunkListToString(String path, List<Chunk> content) {
        List<String> test = new ArrayList<>();

        for(Chunk o : content) {
            test.add(codeChunk(o));
        }

        set(path, test);
    }

    public static void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Location decodeLocation(String input) {
        String[] args = input.split(";");

        World world = Bukkit.getWorld(args[0]);

        double x = Double.parseDouble(args[1]);
        double y = Double.parseDouble(args[2]);
        double z = Double.parseDouble(args[3]);

        float yaw = Float.parseFloat(args[4]);
        float pitch = Float.parseFloat(args[5]);

        return new Location(world, x, y, z, yaw, pitch);
    }
    public static String codeLocation(Location location) {
        return location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getYaw() + ";" + location.getPitch() + ";";
    }
    public static Chunk decodeChunk(String input) {
        String[] args = input.split(";");

        World world = Bukkit.getWorld(args[0]);

        int x = Integer.parseInt(args[1]);
        int z = Integer.parseInt(args[2]);

        return world.getChunkAt(x, z);
    }
    public static String codeChunk(Chunk location) {
        return location.getWorld().getName() + ";" + location.getX() + ";" + location.getZ() + ";";
    }
}
