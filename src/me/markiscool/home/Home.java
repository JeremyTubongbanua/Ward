package me.markiscool.home;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class Home {

    public UUID owner;
    public String name;
    public Location location;

    public Home(UUID owner, String name, Location location) {
        this.owner = owner;
        this.name = name;
        this.location = location;
    }

    public Home(UUID owner, String name, World world, int x, int y, int z, float yaw, float pitch) {
        this(owner, name, new Location(world, x, y, z, yaw, pitch));
    }

    public UUID getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

}
