package me.markiscool.home.commands;

import lib.assets.MCommand;
import lib.utility.Util;
import me.markiscool.Settings;
import me.markiscool.home.Home;
import me.markiscool.home.HomesConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class HomesCommand extends MCommand {

    private HomesConfig hconf;

    public HomesCommand(HomesConfig hconf) {
        super(Settings.BASE_NODE, "homes", Settings.NO_PERM);
        this.hconf = hconf;
    }

    @Override
    public void execute(CommandSender sender, String name, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            List<Home> homes = hconf.getHomes(uuid);
            String msg = "&6Homes: &f";
            if(homes != null && !homes.isEmpty()) {
                for (int i = 0; i < homes.size(); i++) {
                    String homeName = homes.get(i).getName();
                    if (i > 0) msg += ", ";
                    msg += homeName;
                }
            } else {
                msg += "NONE";
            }
            Util.sendMsg(player, Settings.PREFIX, msg);
        } else {
            Util.sendMsg(sender, Settings.PREFIX, Settings.NOT_A_PLAYER);
        }
    }
}
