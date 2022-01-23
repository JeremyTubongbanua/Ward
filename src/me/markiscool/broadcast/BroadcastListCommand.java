package me.markiscool.broadcast;

import lib.assets.MCommand;
import lib.utility.Util;
import me.markiscool.Settings;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class BroadcastListCommand extends MCommand {

    private BroadcastManager bm;

    public BroadcastListCommand(BroadcastManager bm) {
        super(Settings.BASE_NODE, "broadcastlist", Settings.NO_PERM);
        this.bm = bm;
    }

    @Override
    public void execute(CommandSender sender, String name, String[] args) {
        Map<String, String> messages = bm.getMessages();
        Util.sendMsg(sender, "&6Broadcast Messages:");
        Util.sendMsg(sender, "&7======================================");
        if(!messages.isEmpty()) {
            for (Map.Entry<String, String> entry : messages.entrySet()) {
                Util.sendMsg(sender, "&6" + entry.getKey() + ": &r" + entry.getValue());
            }
        } else {
            Util.sendMsg(sender, "&cEmpty! &aAdd some with &6/bcadd <msg>");
        }
    }
}
