package lib.utility;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TextComponentUtil {

    /**
     * Sample use:
     * TextComponent tComp = generateTextComponent("Click me once done, HoverEvent.Action.SHOW_TEXT, "&eAre you done?", ClickEvent.Action.RUN_COMMAND, "/done");
     * sendTextComponent(player, tComp);
     * @param text - overall msg
     * @param hoverAction - what happens when user hovers (eg show text)
     * @param hoverActionValue - what shows up when user hovers (eg "&ehello")
     * @param clickAction - what happens when user clicks (eg run_command)
     * @param clickActionValue - what happens when user clicks (eg command to run like /home)
     * @return
     */
    public static TextComponent generateTextComponent(String text, HoverEvent.Action hoverAction, String hoverActionValue, ClickEvent.Action clickAction, String clickActionValue) {
        TextComponent component = new TextComponent(ChatColor.translateAlternateColorCodes('&', text));
        component.setHoverEvent(new HoverEvent(hoverAction, new ComponentBuilder(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', hoverActionValue)).create()));
        component.setClickEvent(new ClickEvent(clickAction, clickActionValue));
        return component;
    }

    public static void sendTextComponent(Player player, TextComponent component) {
        player.spigot().sendMessage(component);
    }

}
