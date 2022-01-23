package me.markiscool.broadcast;

import lib.assets.MConfig;
import lib.utility.Util;
import me.markiscool.Settings;
import me.markiscool.WardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BroadcastManager extends MConfig {

    //nameid, msg
    private List<String> nameIds;
    private Map<String, String> messages;
    private int currentCycle = 0;

    public BroadcastManager(WardPlugin pl) {
        super(pl.getDataFolder(), "broadcast");
        registerScheduler(pl);
        nameIds = new ArrayList<>();
        messages = new HashMap<>();
    }

    public void broadcast(String text) {
        Bukkit.broadcastMessage(Util.colourize(Settings.PREFIX + " " + text));
    }

    public void addMessage(String nameId, String msg) {
        nameIds.add(nameId);
        messages.put(nameId, msg);
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public String getMessage(String id) {
        return messages.get(id);
    }

    public boolean containsId(String id) {
        return nameIds.contains(id) && messages.containsKey(id);
    }

    public void remove(String id) {
        nameIds.remove(id);
        messages.remove(id);
    }

    @Override
    public void pull() {
        ConfigurationSection secMessages = getSection("messages");
        if(secMessages != null) {
            messages.clear();
            for(String id : secMessages.getKeys(false)) {
                String message = getString("messages." + id);
                nameIds.add(id);
                messages.put(id, message);
            }
        }
    }

    @Override
    public void push() {
        set("messages", null);
        for(Map.Entry<String, String> entry : messages.entrySet()) {
            String id = entry.getKey();
            String msg = entry.getValue();
            set("messages." + id, msg);
        }
        save();
    }

    @Override
    protected void defaultConfig() {
        if(getSection("messages") == null) {
            createSection("messages");
            save();
        }
    }

    private void registerScheduler(WardPlugin pl) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!messages.isEmpty()) {
                    if(currentCycle < nameIds.size()) {
                        String id = nameIds.get(currentCycle);
                        String msg = messages.get(id);
                        broadcast(msg);
                        if (currentCycle + 1 >= messages.size()) {
                            currentCycle = 0;
                        } else {
                            currentCycle++;
                        }
                    } else {
                        currentCycle = 0;
                    }
                }
            }
        }.runTaskTimer(pl, 5 * Settings.TICKS_IN_SECOND, 120 * Settings.TICKS_IN_SECOND);

        new BukkitRunnable() {
            @Override
            public void run() {
                push();
            }
        }.runTaskTimer(pl, 5 * Settings.TICKS_IN_SECOND, 5 * Settings.TICKS_IN_SECOND);
    }

}
