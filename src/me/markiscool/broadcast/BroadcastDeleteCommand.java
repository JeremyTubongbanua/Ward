package me.markiscool.broadcast;

import lib.assets.MCommand;
import lib.utility.Util;
import me.markiscool.Settings;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class BroadcastDeleteCommand extends MCommand {

    private BroadcastManager bm;

    public BroadcastDeleteCommand(BroadcastManager bm) {
        super(Settings.BASE_NODE, "broadcastdelete", Settings.NO_PERM);
        this.bm = bm;
    }

    @Override
    public void execute(CommandSender sender, String name, String[] args) {
        if(args.length == 1) {
            String id = args[0];
            if(bm.containsId(id)) {
                String message = bm.getMessage(id);
                Util.sendMsg(sender, Settings.PREFIX, "&aID: &6" + id + " &ahas been removed with message: &6" + message);
                bm.remove(id);
            } else {
                Util.sendMsg(sender, Settings.PREFIX, "&cId not found.");
            }
        } else {
            Util.sendMsg(sender, Settings.PREFIX, Settings.INVALID_ARGS + "&7 /bcdel <index>");
        }
    }

}
