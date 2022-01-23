package me.markiscool.tpa;

import lib.assets.MListener;
import lib.utility.Util;
import me.markiscool.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;

/**
 * If a profile quits and someone was trying to tp to them,
 * their request is null. They can no longer accept it.
 */
public class LeaveListener extends MListener {

    private TpaManager tpam;

    public LeaveListener(TpaManager tpam) {
        this.tpam = tpam;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(tpam.containsTarget(player) || tpam.containsRecipient(player)) {
            Player potentialRecipient = tpam.getRecipient(player);
            if(potentialRecipient != null) {
                Util.sendMsg(potentialRecipient, Settings.PREFIX, "&6" + player.getName() + " &chas left and requests were voided   ");
            }
            tpam.removeAllInstances(player);
        }
    }

}
