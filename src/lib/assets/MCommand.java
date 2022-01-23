package lib.assets;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public abstract class MCommand implements CommandExecutor {

    private String baseNode;
    private String name;
    private String noPermMessage;

    private Permission perm;

    /**
     * Will create a permission based on baseNode and name
     * eg: (marklibtest.hello.base)
     * @param baseNode - beginning of permission node (eg: marklibtest)
     * @param name - name of command (hello)
     * @param noPermMessage - not automatically colourized ~ message to send to user when not granted permission
     */
    public MCommand(String baseNode, String name, String noPermMessage) {
        this(name);
        this.baseNode = baseNode;
        this.noPermMessage = noPermMessage;
        initializePermission();
    }

    public MCommand(String name) {
        this.name = name;
    }

    public abstract void execute(CommandSender sender, String name, String[] args);

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(perm != null) {
            if(sender.hasPermission(perm)) {
                execute(sender, label, args);
            } else {
                sender.sendMessage(noPermMessage);
            }
        } else {
            execute(sender, label, args);
        }
        return true;
    }

    public String getName() {
        return name;
    }

    private void initializePermission() {
        perm = new Permission(baseNode + "." + name + ".base");
    }
}
