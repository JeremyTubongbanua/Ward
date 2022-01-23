package me.markiscool;

import me.markiscool.broadcast.*;
import me.markiscool.home.HomesConfig;
import me.markiscool.home.commands.*;
import me.markiscool.listeners.DeathListener;
import me.markiscool.profile.ProfileCommand;
import me.markiscool.profile.ProfileListeners;
import me.markiscool.profile.ProfileManager;
import me.markiscool.tpa.LeaveListener;
import me.markiscool.tpa.TpaManager;
import me.markiscool.tpa.commands.TpaCommand;
import me.markiscool.tpa.commands.TpacceptCommand;
import me.markiscool.tpa.commands.TpdenyCommand;
import lib.MPlugin;

public class WardPlugin extends MPlugin {

    private HomesConfig hconf;
    private TpaManager tpam;
    private BroadcastManager bm;
    private ProfileManager pm;

    public WardPlugin() {
        super("Ward");
    }

    @Override
    public void instantiateAssets() {
        hconf = new HomesConfig(this);
        tpam = new TpaManager(this);
        bm = new BroadcastManager(this);
        pm = new ProfileManager(this);
    }

    @Override
    public void registerConfigs() {
        addConfig(hconf);
        addConfig(bm);
        addConfig(pm);
    }

    @Override
    public void registerDatabase() {

    }

    @Override
    public void registerListeners() {
        addListener(new LeaveListener(tpam));
        addListener(new ProfileListeners(pm));
        addListener(new DeathListener());
    }

    @Override
    public void registerCommands() {
        addCommand(new HomeCommand(hconf));
        addCommand(new DelhomeCommand(hconf));
        addCommand(new SetHomeCommand(hconf));
        addCommand(new HomesCommand(hconf));
        addCommand(new HomesViewCommand(hconf, pm));

        addCommand(new TpacceptCommand(tpam));
        addCommand(new TpaCommand(tpam));
        addCommand(new TpdenyCommand(tpam));

        addCommand(new BroadcastCommand(bm));
        addCommand(new BroadcastAddCommand(bm));
        addCommand(new BroadcastDeleteCommand(bm));
        addCommand(new BroadcastListCommand(bm));

        addCommand(new ProfileCommand(pm));
    }
}
