package me.markiscool.home.commands;

import lib.assets.MCommand;
import lib.utility.Util;
import me.markiscool.Settings;
import me.markiscool.home.Home;
import me.markiscool.home.HomesConfig;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DelhomeCommand extends MCommand {

    private HomesConfig hconf;

    public DelhomeCommand(HomesConfig hconf) {
        super(Settings.BASE_NODE, "delhome", Settings.NO_PERM);
        this.hconf = hconf;
    }

    @Override
    public void execute(CommandSender sender, String name, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 1) {
                String homeName = args[0];
                UUID uuid = player.getUniqueId();
                Home home = hconf.getHome(uuid, homeName);
                if(home != null) {
                    Location loc = home.getLocation();
                    int x = loc.getBlockX();
                    int y = loc.getBlockY();
                    int z = loc.getBlockZ();
                    hconf.removeHome(uuid, home);
                    Util.sendMsg(player, Settings.PREFIX, "&6" + homeName + " &awas removed. &6Coordinates: &a(" + x + ", " + y + ", " + z + ")");
                } else {
                    Util.sendMsg(player, Settings.PREFIX, "&cHome not found");
                }
            } else {
                Util.sendMsg(player, Settings.PREFIX, Settings.INVALID_ARGS + " &7Try /delhome <name>");
            }
        } else {
            Util.sendMsg(sender, Settings.PREFIX, Settings.NOT_A_PLAYER);
        }
    }
}
