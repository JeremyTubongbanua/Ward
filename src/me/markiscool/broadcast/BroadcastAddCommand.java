package me.markiscool.broadcast;

import lib.assets.MCommand;
import lib.utility.Util;
import me.markiscool.Settings;
import org.bukkit.command.CommandSender;

public class BroadcastAddCommand extends MCommand {

    private BroadcastManager bm;

    public BroadcastAddCommand(BroadcastManager bm) {
        super(Settings.BASE_NODE, "broadcastadd", Settings.NO_PERM);
        this.bm = bm;
    }

    @Override
    public void execute(CommandSender sender, String name, String[] args) {
        if(args.length >= 2) {
            String id = args[0];
            if(!bm.containsId(id)) {
                String msg = "";
                for (int i = 1; i < args.length; i++) {
                    if (i > 1) msg += " ";
                    msg += args[i];
                }
                bm.addMessage(id, Util.colourize(msg));
                Util.sendMsg(sender, Settings.PREFIX, "&aAdded message: &r" + msg + " &ato id: &6" + id);
            } else {
                Util.sendMsg(sender, Settings.PREFIX, "&cThere is already a broadcast with this id.");
            }
        } else {
            Util.sendMsg(sender, Settings.PREFIX, "&cInvalid amount of arguments. &7/bcadd <msg>");
        }
    }
}
