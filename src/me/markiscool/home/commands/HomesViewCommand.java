package me.markiscool.home.commands;

import lib.assets.MCommand;
import lib.utility.TextComponentUtil;
import lib.utility.Util;
import me.markiscool.Settings;
import me.markiscool.home.Home;
import me.markiscool.home.HomesConfig;
import me.markiscool.profile.Profile;
import me.markiscool.profile.ProfileManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HomesViewCommand extends MCommand {

    private HomesConfig hc;
    private ProfileManager pm;

    public HomesViewCommand(HomesConfig hc, ProfileManager pm) {
        super(Settings.BASE_NODE, "homesview", Settings.NO_PERM);
        this.hc = hc;
        this.pm = pm;
    }

    @Override
    public void execute(CommandSender sender, String name, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 1) {
                Profile targetPrf = pm.getProfile(args[0]);
                if(targetPrf != null) {
                    List<Home> homes = hc.getHomes(targetPrf.getUUID());
                    Util.sendMsg(player, "&9--------------------------------");
                    for(Home home : homes) {
                        Location loc = home.getLocation();
                        double x = loc.getX();
                        double y = loc.getY();
                        double z = loc.getZ();
                        TextComponent tc = TextComponentUtil.generateTextComponent(
                                "&6" + home.getName() + "&a: (" + x + ", " + y + ", " + z+ ")",
                                HoverEvent.Action.SHOW_TEXT,
                                "&aClick me to teleport",
                                ClickEvent.Action.RUN_COMMAND,
                                "/tp " + x + " " + y + " " + z);
                        TextComponentUtil.sendTextComponent(player, tc);
                    }
                    Util.sendMsg(player, "&9--------------------------------");
                } else {
                    Util.sendMsg(player, Settings.PREFIX, "&cProfile was not found.");
                }
            } else {
                Util.sendMsg(player, Settings.PREFIX,Settings.INVALID_ARGS + "&7/homesview <player>");
            }
        } else {
            Util.sendMsg(sender, Settings.PREFIX, Settings.NOT_A_PLAYER);
        }
    }
}
