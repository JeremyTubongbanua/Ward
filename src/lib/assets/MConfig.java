package lib.assets;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Abstract me.markiscool.Config class
 * Extend this class if you're making your own methods
 * to interact with a config.yml file.
 */
public abstract class MConfig {

    private File file;
    private FileConfiguration config;

    /**
     * You should
     * @param path
     * @param name
     */
    protected MConfig(File path, String name) {
        createFile(path, name);
        defaultConfig();
        save();
    }

    /**
     * Will push all of the cache and save it into the .yml file
     *
     * Make sure to clear your .yml file
     * (eg: config.set("", null); save();)
     */
    public abstract void push();

    /**
     * Re-set cache in this method
     * Use #get() and #getString() and stuff like that
     *
     * Make sure to clear all of your cache before you pull
     * (eg: list.clear();)
     */
    public abstract void pull();

    /**
     * Code in here should use the #addDefault()
     * method which checks if the value exists, and if it doesn't,
     * then it sets the value.
     */
    protected abstract void defaultConfig();

    /**
     * Saves the file.
     * Method is not called after using set()
     * But should be called after using all of your set()s
     */
    protected void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Re instantiates the config object according
     * to what's on the file.
     */
    protected void reRead() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Sets a value if the path is not found
     * should be called in #defaultConfig()
     */
    protected void addDefault(String path, Object value) {
        if(!config.contains(path)) {
            config.set(path, value);
        }
    }

    /**
     * Returns object (cast if you know what youre doing)
     * @param path path of desired value (eg: players.MarkIsCool.nick)
     * @return Object
     */
    protected Object get(String path) {
        return config.get(path);
    }

    /**
     * Returns a String from config
     * Eg:
     * players:
     *  MarkIsCool:
     *    nick: MarkIsNotCool
     * @param path path of desired value (eg: players.MarkIsCool.nick)
     * @return String (eg: MarkIsNotCool)
     */
    protected String getString(String path) {
        return config.getString(path);
    }

    /**
     * Returns a double from config
     * @param path path of desired value (eg: players.MarkIsCool.nick)
     * @return double
     */
    protected double getDouble(String path) {
        return config.getDouble(path);
    }

    /**
     * Returns a int from config
     * @param path path of desired value (eg: players.MarkIsCool.nick)
     * @return int
     */
    protected int getInt(String path) {
        return config.getInt(path);
    }

    /**
     * Returns a List<?> from config
     * @param path path of desired value (eg: players.MarkIsCool.nick)
     * @return List<?>
     */
    protected List<?> getList(String path) {
        return config.getList(path);
    }

    /**
     * Sets the path a value.
     * @param path (eg: players.MarkIsCool.nick)
     * @param value (eg: "MarkIsNotCool")
     */
    protected void set(String path, Object value) {
        config.set(path, value);
    }

    /**
     * Creates an empty section
     * @param path
     */
    protected void createSection(String path) {
        config.createSection(path);
    }

    protected ConfigurationSection getSection(String path) {
        return config.getConfigurationSection(path);
    }

    /**
     * Initializes the file objects
     * @param path path directory of the plugin
     * @param fileName desired config name (eg: fileName="lemon", will result in "lemon.yml")
     */
    private void createFile(File path, String fileName) {
        if(!path.exists()) {
            path.mkdir();
        }
        file = new File(path, fileName + ".yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

}
