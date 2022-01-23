package lib.utility;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Util {

    /**
     * Sends the CommandSender a colourized message. (Use & for chat color)
     *
     * @param receiver - person receiving msg
     * @param msg      - msg to send
     */
    public static void sendMsg(CommandSender receiver, String msg) {
        receiver.sendMessage(colourize(msg));
    }

    /**
     * Sends the receiver a colourized series of messages (Use & for chat color)
     * Messages are sent one after another
     *
     * @param receiver - person receiving msg
     * @param text     - msg to send
     */
    public static void sendMsg(CommandSender receiver, List<String> text) {
        for (String s : text) {
            receiver.sendMessage(colourize(s));
        }
    }

    /**
     * Sends the receiver a colourized series of messages (Use & for chat color)
     * Messages are sent one after another
     *
     * Message accompanied with plugin prefix.
     *
     * @param receiver - person receiving msg
     * @param prefix   - prefix of plugin eg: [Ward]
     * @param text     - msg to send
     */
    public static void sendMsg(CommandSender receiver, String prefix, String text) {
        sendMsg(receiver, prefix + " " + text);
    }

    /**
     * Colourize a string of text (&)
     *
     * @param text - text to colourize use & for chat color
     * @return colourized text
     */
    public static String colourize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Colourize a List<string> (&)
     *
     * @param text - List to colourize
     * @return colourized List
     */
    public static List<String> colourize(List<String> text) {
        List<String> x = new ArrayList<String>();
        for (String s : text) {
            x.add(colourize(s));
        }
        return x;
    }

}
