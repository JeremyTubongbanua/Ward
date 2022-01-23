package me.markiscool.tpa.commands;

import lib.assets.MCommand;
import lib.utility.TextComponentUtil;
import lib.utility.Util;
import me.markiscool.Settings;
import me.markiscool.tpa.TpaManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaCommand extends MCommand {

    private TpaManager tpam;

    public TpaCommand(TpaManager tpam) {
        super(Settings.BASE_NODE, "tpa", Settings.NO_PERM);
        this.tpam = tpam;
    }

    @Override
    public void execute(CommandSender sender, String name, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 1) {
                Player targetPlayer = Bukkit.getPlayer(args[0]);
                if(targetPlayer != null) {
                    if(tpam.containsRecipient(player)) {
                        Util.sendMsg(player, Settings.PREFIX + "&cYour previous teleport request was voided.");
                    }
                    tpam.add(targetPlayer, player);
                    Util.sendMsg(player, Settings.PREFIX, "&aA teleport request has been sent to &6" + targetPlayer.getName() + "&a. Request will expire in &6" + Settings.TPA_EXPIRE_SECONDS + "&a seconds.");
                    Util.sendMsg(targetPlayer, Settings.PREFIX, "&6" + player.getName() + " &ahas requested that they teleport to you. Request will expire in &6" + Settings.TPA_EXPIRE_SECONDS + " &aseconds.");
                    TextComponent tc = TextComponentUtil.generateTextComponent(Settings.PREFIX + " &6[CLICK] &ato accept.", HoverEvent.Action.SHOW_TEXT, "&a/tpaccept", ClickEvent.Action.RUN_COMMAND, "/tpaccept");
                    TextComponentUtil.sendTextComponent(targetPlayer, tc);
                    Util.sendMsg(targetPlayer, Settings.PREFIX, "&6/tpdeny &ato deny.");
                } else {
                    Util.sendMsg(player, Settings.PREFIX, "&cPlayer not found.");
                }
            } else {
                Util.sendMsg(player, Settings.PREFIX, Settings.INVALID_ARGS + " &7/tpa <name>");
            }
        } else {
            Util.sendMsg(sender, Settings.PREFIX, Settings.NOT_A_PLAYER);
        }
    }
}
