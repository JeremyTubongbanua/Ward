package me.markiscool.listeners;

import lib.assets.MListener;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener extends MListener {

    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.setDeathMessage("&7Riperino in Pepperoni: &c" + player.getDisplayName());
        event.setKeepInventory(true);
        event.setKeepLevel(true);
    }

}
