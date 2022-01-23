package me.markiscool.home.commands;

import lib.assets.MCommand;
import lib.utility.Util;
import me.markiscool.Settings;
import me.markiscool.home.Home;
import me.markiscool.home.HomesConfig;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 *  - /home [name] - checks if user has multiple homes. If only 1 home, brings them to only home
 */
public class HomeCommand extends MCommand {

    private HomesConfig hconf;

    public HomeCommand(HomesConfig hconf) {
        super(Settings.BASE_NODE, "home", Settings.NO_PERM);
        this.hconf = hconf;
    }

    @Override
    public void execute(CommandSender sender, String name, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            if (args.length == 0) { // /home
                List<Home> homes = hconf.getHomes(uuid);
                if(homes.size() == 1) {
                    Home home = homes.get(0);
                    Location loc = home.getLocation();
                    teleport(player, home.getName(), loc);
                } else {
                    Util.sendMsg(sender, Settings.PREFIX, Settings.INVALID_ARGS + " &7Which home? (/homes)");
                }
            } else if (args.length == 1) {
                String homeName = args[0];
                Home home = hconf.getHome(uuid, homeName);
                if(home != null) {
                    Location loc = home.getLocation();
                    teleport(player, home.getName(), loc);
                } else {
                    Util.sendMsg(sender, Settings.PREFIX, "&cHome not found.");
                }
            } else {
                Util.sendMsg(sender, Settings.PREFIX, Settings.INVALID_ARGS);
            }
        } else {
            Util.sendMsg(sender, Settings.PREFIX, Settings.NOT_A_PLAYER);
        }
    }

    public void teleport(Player player, String homeName, Location location) {
        player.teleport(location);
        Util.sendMsg(player, Settings.PREFIX, "&aYou have been teleported to: &6" + homeName);
    }
}
