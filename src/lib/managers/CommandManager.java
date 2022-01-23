package lib.managers;

import lib.assets.MCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private List<MCommand> cmds;

    public CommandManager() {
        cmds = new ArrayList<MCommand>();
    }

    public void addCommand(MCommand cmd) {
        cmds.add(cmd);
    }

    public List<MCommand> getCommands() {
        return cmds;
    }

}
