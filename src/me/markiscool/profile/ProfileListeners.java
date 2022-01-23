package me.markiscool.profile;

import lib.assets.MListener;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ProfileListeners extends MListener {

    private ProfileManager pm;

    public ProfileListeners(ProfileManager pm) {
        this.pm = pm;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Profile profile = pm.getProfile(player.getUniqueId());
        profile.setLastLoginMs(System.currentTimeMillis());
        profile.addTimePlayedMs(System.currentTimeMillis() - profile.getJoinStamp());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Profile prf = pm.getProfile(player.getUniqueId());
        prf.incrementDeaths();
    }

    @EventHandler
    public void onPlayerKillEntity(EntityDeathEvent event) {
        Entity dead = event.getEntity();
        Player killer = ((LivingEntity) dead).getKiller();
        if(killer != null) {
            Profile prf = pm.getProfile(killer.getUniqueId());
            prf.incrementKills();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Profile prf = pm.getProfile(player.getUniqueId());
        if(prf != null) {
            String prfName = prf.getName();
            String playerName = player.getName();
            if (!prfName.equalsIgnoreCase(playerName)) {
                prf.setName(playerName);
            }
        } else {
            prf = new Profile(player.getUniqueId(), 0, 0, System.currentTimeMillis(), player.getName(), 0);
            pm.addNewProfile(prf);
        }
        prf.stampTime();
    }

}
