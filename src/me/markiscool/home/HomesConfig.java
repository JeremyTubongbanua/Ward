package me.markiscool.home;

import lib.assets.MConfig;
import me.markiscool.Settings;
import me.markiscool.WardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class HomesConfig extends MConfig {

    //owner, home
    private Map<UUID, List<Home>> homes;

    public HomesConfig(WardPlugin pl) {
        super(pl.getDataFolder(), "homes");
        homes = new HashMap<>();
        registerScheduler(pl);
    }

    public List<Home> getHomes(UUID uuid) {
        for(Map.Entry<UUID, List<Home>> entry : homes.entrySet()) {
            if(entry.getKey().equals(uuid)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Home getHome(UUID owner, String name) {
        for(Map.Entry<UUID, List<Home>> entry : homes.entrySet()) {
            if(entry.getKey().equals(owner)) {
                for(Home home : entry.getValue()) {
                    if(home.getName().equalsIgnoreCase(name)) {
                        return home;
                    }
                }
            }
        }
        return null;
    }

    public void registerHome(UUID owner, String name, Location loc) {
        Home home = new Home(owner, name, loc);
        List<Home> pHomes = getHomes(owner);
        if(pHomes == null) {
            homes.put(owner, new ArrayList<>());
            pHomes = getHomes(owner);
        }
        pHomes.add(home);
    }

    public void removeHome(UUID owner, Home home) {
        homes.get(owner).remove(home);
    }

    @Override
    public void push() {
        set("homes", null);
        createSection("homes");
        for(Map.Entry<UUID, List<Home>> entry : homes.entrySet()) {
            String u = entry.getKey().toString();
            List<Home> pHomes = entry.getValue();
            for(Home home : pHomes) {
                Location loc = home.getLocation();
                String name = home.getName();
                String world = loc.getWorld().getName();
                int x = loc.getBlockX();
                int y = loc.getBlockY();
                int z = loc.getBlockZ();
                float yaw = loc.getYaw();
                float pitch = loc.getPitch();

                set("homes." + u + "." + name + ".location.world", world);
                set("homes." + u + "." + name + ".location.x", x);
                set("homes." + u + "." + name + ".location.y", y);
                set("homes." + u + "." + name + ".location.z", z);
                set("homes." + u + "." + name + ".location.yaw", yaw);
                set("homes." + u + "." + name + ".location.pitch", pitch);
            }
        }
        save();
    }

    @Override
    public void pull() {
        ConfigurationSection secHomes = getSection("homes");
        if(secHomes != null) {
            this.homes.clear();
            for(String u : secHomes.getKeys(false)) {
                UUID uuid = UUID.fromString(u);
                List<Home> pHomes = new ArrayList<>();
                for(String homeName : getSection("homes." + u).getKeys(false)) {
                    World world = Bukkit.getWorld(getString("homes." + u + "." + homeName + ".location.world"));
                    int x = getInt("homes." + u + "." + homeName + ".location.x");
                    int y = getInt("homes." + u + "." + homeName + ".location.y");
                    int z = getInt("homes." + u + "." + homeName + ".location.z");
                    float yaw = (float) getDouble("homes." + u + "." + homeName + ".location.yaw");
                    float pitch = (float) getDouble("homes." + u + "." + homeName + ".location.pitch");
                    Home home = new Home(uuid, homeName, new Location(world, x, y, z, yaw, pitch));
                    pHomes.add(home);
                }
                homes.put(uuid, pHomes);
            }
        }
    }

    @Override
    protected void defaultConfig() {
        if(getSection("homes") == null) {
            createSection("homes");
            save();
        }
    }

    private void registerScheduler(WardPlugin pl) {
        new BukkitRunnable() {
            @Override
            public void run() {
                push();
            }
        }.runTaskTimer(pl, 30* Settings.TICKS_IN_SECOND, 10 * Settings.TICKS_IN_SECOND);
    }
}
