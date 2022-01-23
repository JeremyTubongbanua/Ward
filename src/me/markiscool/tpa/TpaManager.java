package me.markiscool.tpa;

import lib.utility.Util;
import me.markiscool.Settings;
import me.markiscool.WardPlugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TpaManager {

    //target, recipient
    private Map<Player, Player> players;
    //target, timeToExpire (seconds)
    private Map<Player, Long> expire;

    public TpaManager(WardPlugin pl) {
        players = new HashMap<>();
        expire = new HashMap<>();
        registerScheduler(pl);
    }

    public void add(Player target, Player recipient) {
        players.put(target, recipient);
        expire.put(target, Settings.TPA_EXPIRE_SECONDS);
    }

    public boolean containsTarget(Player target) {
        return players.containsKey(target);
    }

    public boolean containsRecipient(Player recipient) {
        return players.containsValue(recipient);
    }

    public Player getRecipient(Player target) {
        return players.get(target);
    }

    public void removeAllInstances(Player player) {
        players.remove(player);
        expire.remove(player);
        if(containsRecipient(player)) {
            for(Map.Entry<Player, Player> entry : players.entrySet()) {
                if(entry.getValue() == player) {
                    players.remove(entry.getKey());
                }
            }
        }
    }

    public long getTimeToExpire(Player target) {
        return expire.get(target);
    }

    private void registerScheduler(WardPlugin pl) {
        new BukkitRunnable() {
            @Override
            public void run() {
                List<Player> toRemove = new ArrayList<>();
                List<Player> toDeplete = new ArrayList<>();
                for(Player target : expire.keySet()) {
                    if(expire.get(target) <= 0) {
                        toRemove.add(target);
                    } else {
                        toDeplete.add(target);
                    }
                }

                for(Player p : toRemove) {
                    Player recipient = getRecipient(p);
                    if(recipient.isOnline()) {
                        Util.sendMsg(p, Settings.PREFIX, "&cYour teleport request to &6" + recipient.getName() + "&c has expired.");
                        Util.sendMsg(recipient, Settings.PREFIX, "&6" + recipient.getName() + "&c's teleport request has expired.");
                    }
                    removeAllInstances(p);
                }

                for(Player p : toDeplete) {
                    long time = getTimeToExpire(p);
                    expire.remove(p);
                    expire.put(p, time-1);
                }
            }
        }.runTaskTimer(pl, 0*Settings.TICKS_IN_SECOND, 1*Settings.TICKS_IN_SECOND);
    }

}
