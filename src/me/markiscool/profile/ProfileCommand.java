package me.markiscool.profile;

import com.mysql.cj.util.TimeUtil;
import lib.assets.MCommand;
import lib.utility.TextComponentUtil;
import lib.utility.Util;
import me.markiscool.Settings;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ProfileCommand extends MCommand {

    private ProfileManager pm;

    public ProfileCommand(ProfileManager pm) {
        super(Settings.BASE_NODE, "profile", Settings.NO_PERM);
        this.pm = pm;
    }

    @Override
    public void execute(CommandSender sender, String name, String[] args) {
        if(args.length == 1 || args.length == 0) {
            OfflinePlayer targetPlayer = null;
            if(args.length == 1) {
                Profile targetPrf = pm.getProfile(args[0]);
                if(targetPrf != null) {
                    targetPlayer = Bukkit.getOfflinePlayer(pm.getProfile(args[0]).getUUID());
                } else {
                    Util.sendMsg(sender, Settings.PREFIX, "&cPlayer not found.");
                }
            } else {
                if(sender instanceof Player) {
                    targetPlayer = (Player) sender;
                } else {
                    Util.sendMsg(sender, Settings.PREFIX, Settings.NOT_A_PLAYER);
                    return;
                }
            }

            if(targetPlayer != null) {
                Profile prf = pm.getProfile(targetPlayer.getUniqueId());
                int kills = prf.getAmountKills();
                int deaths = prf.getAmountDeaths();
                long timePlayedHours = TimeUnit.HOURS.convert(prf.getTimePlayedMs(), TimeUnit.MILLISECONDS);

                Util.sendMsg(sender, "&9-------------------------------");
                Util.sendMsg(sender, "&6" + targetPlayer.getName() + "&7's profile");
                if(targetPlayer.isOnline()) {
                    Util.sendMsg(sender, "&eLast seen: &aONLINE" );
                } else {
                    Util.sendMsg(sender, "&eLast seen: &7" + new Date(prf.getLastLoginMs()).toString());
                }
                Util.sendMsg(sender, "&eKills: &7" + kills);
                Util.sendMsg(sender, "&eDeaths: &7" + deaths);
                Util.sendMsg(sender, "&eTime played: &7" + timePlayedHours + "h");
                if(sender instanceof Player) {
                    Player playerSender = (Player) sender;
                    TextComponent tc = TextComponentUtil.generateTextComponent("&6[View Homes]", HoverEvent.Action.SHOW_TEXT, "&eClick me to view their homes", ClickEvent.Action.RUN_COMMAND, "/homesview " + targetPlayer.getName());
                    TextComponentUtil.sendTextComponent(playerSender, tc);
                }
                Util.sendMsg(sender, "&9-------------------------------");
            } else {
                Util.sendMsg(sender, Settings.PREFIX, "&cPlayer not found.");
            }
        } else {
            Util.sendMsg(sender, Settings.PREFIX, Settings.INVALID_ARGS + "&7 /profile <player_name>");
        }
    }
}