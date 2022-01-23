package me.markiscool.home.commands;

import lib.assets.MCommand;
import lib.utility.Util;
import me.markiscool.Settings;
import me.markiscool.home.Home;
import me.markiscool.home.HomesConfig;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SetHomeCommand extends MCommand {

    private HomesConfig hconf;

    public SetHomeCommand(HomesConfig hconf) {
        super(Settings.BASE_NODE, "sethome", Settings.NO_PERM);
        this.hconf = hconf;
    }

    @Override
    public void execute(CommandSender sender, String name, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();
            if(args.length == 1) {
                String homeName = args[0];
                Home homeCheck = hconf.getHome(uuid, homeName);
                if(homeCheck == null) {
                    Block b = player.getLocation().getBlock();
                    Location loc = new Location(player.getWorld(), b.getX(), b.getY(), b.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                    hconf.registerHome(uuid, homeName, loc);
                    Util.sendMsg(player, Settings.PREFIX, "&aSuccessfully created home &6" + homeName);
                } else {
                    Util.sendMsg(player, Settings.PREFIX, "&cYou already have a home with this name");
                }
            } else {
                Util.sendMsg(player, Settings.PREFIX, "&cInvalid arguments. &7/sethome <home_name>");
            }
        } else {
            Util.sendMsg(sender, Settings.PREFIX, Settings.NOT_A_PLAYER);
        }
    }
}
