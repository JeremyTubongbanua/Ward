package me.markiscool.broadcast;

import lib.assets.MCommand;
import lib.utility.Util;
import me.markiscool.Settings;
import org.bukkit.command.CommandSender;

public class BroadcastCommand extends MCommand {

    private BroadcastManager bm;

    public BroadcastCommand(BroadcastManager bm) {
        super(Settings.BASE_NODE, "broadcast", Settings.NO_PERM);
        this.bm = bm;
    }

    @Override
    public void execute(CommandSender sender, String name, String[] args) {
        if(args.length > 0) {
            String msg = "";
            for(int i = 0; i < args.length; i++) {
                if(i > 0) msg += " ";
                msg += args[i];
            }
            bm.broadcast(msg);
        } else {
            Util.sendMsg(sender, Settings.PREFIX, Settings.INVALID_ARGS + " &7/bc <msg..>");
        }
    }
}
