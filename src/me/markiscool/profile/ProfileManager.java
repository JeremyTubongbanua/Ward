package me.markiscool.profile;

import lib.assets.MConfig;
import me.markiscool.WardPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProfileManager extends MConfig {

    private List<Profile> profiles;

    public ProfileManager(WardPlugin pl) {
        super(pl.getDataFolder(), "users");
        profiles = new ArrayList<>();
    }

    public Profile getProfile(UUID uuid) {
        for(Profile pro : profiles) {
            if(pro.getUUID().equals(uuid)) {
                return pro;
            }
        }
        return null;
    }

    public Profile getProfile(String name) {
        for(Profile prf : profiles) {
            if(prf.getName().equalsIgnoreCase(name)) {
                return prf;
            }
        }
        return null;
    }

    public void addNewProfile(Profile prf) {
        profiles.add(prf);
    }

    @Override
    public void push() {
        set("profiles", null);
        createSection("profiles");
        for(Profile prf : profiles) {
            String strUUID = prf.getUUID().toString();
            int amtKills = prf.getAmountKills();
            int amtDeaths = prf.getAmountDeaths();
            long lastLoginMs = prf.getLastLoginMs();
            String name = prf.getName();
            long timePlayedMs = prf.getTimePlayedMs();
            set("profiles." + strUUID + ".kills", amtKills);
            set("profiles." + strUUID + ".deaths", amtDeaths);
            set("profiles." + strUUID + ".last-login-ms", lastLoginMs);
            set("profiles." + strUUID + ".name", name);
            set("profiles." + strUUID + ".time-played-ms", timePlayedMs);
        }
        save();
    }

    @Override
    public void pull() {
        profiles.clear();
        for(String strUUID : getSection("profiles").getKeys(false)) {
            UUID uuid = UUID.fromString(strUUID);
            int amtKills = getInt("profiles." + strUUID + ".kills");
            int amtDeaths = getInt("profiles." + strUUID + ".deaths");
            long lastLoginMs = (long) getDouble("profiles." + strUUID + ".last-login-ms");
            String name = getString("profiles." + strUUID + ".name");
            long timePlayedMs = (long) getDouble("profiles." + strUUID + ".time-played-ms");
            Profile prf = new Profile(uuid, amtKills, amtDeaths, lastLoginMs, name, timePlayedMs);
            profiles.add(prf);
        }
    }

    @Override
    protected void defaultConfig() {
        if(getSection("profiles") == null) {
            createSection("profiles");
            save();
        }
    }
}
