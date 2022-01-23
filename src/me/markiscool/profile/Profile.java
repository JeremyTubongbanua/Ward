package me.markiscool.profile;

import org.bukkit.Bukkit;

import java.util.Date;
import java.util.UUID;

public class Profile {

    private UUID uuid;
    private int amtKills;
    private int amtDeaths;
    private long lastLoginMs;
    private String name;
    private long timePlayedMs;
    private long loggedOnMsStamp;

    public Profile(UUID uuid, int amtKills, int amtDeaths, long lastLoginMs, String name, long timePlayedMs) {
        this.uuid = uuid;
        this.amtKills = amtKills;
        this.amtDeaths = amtDeaths;
        this.lastLoginMs = lastLoginMs;
        this.name= name;
        this.timePlayedMs = timePlayedMs;
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getAmountKills() {
        return amtKills;
    }

    public int getAmountDeaths() {
        return amtDeaths;
    }

    public long getLastLoginMs() {
        return lastLoginMs;
    }

    public void setLastLoginMs(long ms) {
        this.lastLoginMs = ms;
    }

    public void incrementDeaths() {
        amtDeaths += 1;
    }

    public void incrementKills() {
        amtKills += 1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getTimePlayedMs() {
        return timePlayedMs;
    }

    public void addTimePlayedMs(long timePlayedMs) {
        this.timePlayedMs += timePlayedMs;
    }

    public void stampTime() {
        loggedOnMsStamp = System.currentTimeMillis();
    }

    public long getJoinStamp() {
        return loggedOnMsStamp;
    }

}
