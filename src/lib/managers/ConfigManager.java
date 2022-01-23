package lib.managers;

import lib.assets.MConfig;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private List<MConfig> configs;

    public ConfigManager() {
        configs = new ArrayList<MConfig>();
    }

    public void addConfig(MConfig config) {
        configs.add(config);
    }

    public List<MConfig> getConfigs() {
        return configs;
    }

}
