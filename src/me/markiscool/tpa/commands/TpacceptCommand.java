package me.markiscool.tpa.commands;

import lib.assets.MCommand;
import lib.utility.Util;
import me.markiscool.Settings;
import me.markiscool.tpa.TpaManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpacceptCommand extends MCommand {

    private TpaManager tpam;

    public TpacceptCommand(TpaManager tpam) {
        super(Settings.BASE_NODE, "tpaccept", Settings.NO_PERM);
        this.tpam = tpam;
    }

    @Override
    public void execute(CommandSender sender, String name, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(tpam.containsTarget(player)) {
                Player recipient = tpam.getRecipient(player);
                recipient.teleport(player.getLocation());
                tpam.removeAllInstances(player);
                Util.sendMsg(recipient, Settings.PREFIX, "&aYou have teleported to &6" + player.getName());
                Util.sendMsg(player, Settings.PREFIX, "&aYou have accepted &6" + recipient.getName() + "&a's teleport request.");
            } else {
                Util.sendMsg(player, Settings.PREFIX, "&cYou do not have any incoming requests.");
            }
        } else {
            Util.sendMsg(sender, Settings.PREFIX, Settings.NOT_A_PLAYER);
        }
    }
}
