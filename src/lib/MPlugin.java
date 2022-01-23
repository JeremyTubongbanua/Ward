package lib;

import lib.assets.MConfig;
import lib.assets.MDatabase;
import lib.managers.CommandManager;
import lib.assets.MCommand;
import lib.managers.ConfigManager;
import lib.managers.DatabaseManager;
import lib.managers.ListenerManager;
import lib.assets.MListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Logger;

public abstract class MPlugin extends JavaPlugin {

    private String name;
    private Logger logger;

    private ConfigManager m_config;
    private DatabaseManager m_database;
    private ListenerManager m_lis;
    private CommandManager m_cmd;

    public MPlugin(String name) {
        this.name = name;
        this.logger = getLogger();
    }

    /**
     * You should call super.onEnable()
     * whenever in your @Override onEnable() {} code
     * preferably in the beginning so that all objects
     * can instantiate
     */
    public void onEnable() {
        instantiateAssets();
        initializeManagers();

        registerConfigs();
        pullConfigs();

        registerDatabase();
        defaultDatabases();

        registerListeners();
        registerEvents();

        registerCommands();
        setCommandsExecutors();

        logger.info(name + " has been enabled!");
    }

    /**
     * You should call super.onDisable()
     * whenever in your @Override onDisable() {} code
     * preferably in the beginning so that all objects
     * can instantiate
     */
    public void onDisable() {
        pushCache();
        closeDatabase();
        logger.info(name + " has been disabled!");
    }

    /**
     * Where you create objects such as Database.java
     * or me.markiscool.Config.java. See MarkLibTestPlugin#instantiateAssets()
     * for example.
     */
    public abstract void instantiateAssets();

    /**
     * Add configs to the ConfigManager
     * using super.addConfig()
     */
    public abstract void registerConfigs();

    /**
     * Add databases to the DatabaseManager
     * using super.addDatabase()
     */
    public abstract void registerDatabase();

    /**
     * Add listeners to the ListenerManager
     * using super.addListener()
     */
    public abstract void registerListeners();

    /**
     * Add commands to the CommandManager
     * using super.addCommand()
     */
    public abstract void registerCommands();

    /**
     * Add a MConfig to the ConfigManager
     * for the config to push cache, pull data
     * and instantiate properly
     * @param config
     */
    public void addConfig(MConfig config) {
        m_config.addConfig(config);
    }

    /**
     * Add a MDatabase to the DatabaseManager
     * for the database to close its connection
     * onDisable
     * @param database
     */
    public void addDatabase(MDatabase database) {
        m_database.addDatabase(database);
    }

    /**
     * Add a MListener to the ListenerManager
     * so that it can register the events to
     * the PluginManager (through Bukkit)
     * @param lis
     */
    public void addListener(MListener lis) {
        m_lis.addListener(lis);
    }

    /**
     * Add a <Command to the CommandManager
     * so that it can register
     * so that the command can work overall
     * @param cmd
     */
    public void addCommand(MCommand cmd) {
        m_cmd.addCommand(cmd);
    }

    /**
     * Pushes the cache of configs
     * called in #onDisable()
     */
    private void pushCache() {
        for(MConfig config : m_config.getConfigs()) {
            config.push();
        }
    }

    /**
     * Closes all database connections
     * called in #onDisable()
     */
    private void closeDatabase() {
        try {
            for (MDatabase db : m_database.getDatabases()) {
                if(db.connectionExists()) db.closeConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes all managers.
     * There should only be one instance ever existent of each manager.
     * Called in #onEnable()
     */
    private void initializeManagers() {
        m_cmd = new CommandManager();
        m_lis = new ListenerManager();
        m_config = new ConfigManager();
        m_database = new DatabaseManager();
    }

    /**
     * Registers the commands to the plugin itself
     *
     *
     */
    public void setCommandsExecutors() {
        for(MCommand cmd : m_cmd.getCommands()) {
            getCommand(cmd.getName()).setExecutor(cmd);
        }
    }

    /**
     * Registers the events to the plugin itself
     */
    public void registerEvents() {
        for(MListener lis : m_lis.getListeners()) {
            getServer().getPluginManager().registerEvents(lis, this);
        }
    }

    /**
     * Pulls data into cache
     */
    private void pullConfigs() {
        for(MConfig config : m_config.getConfigs()) {
            config.pull();
        }
    }

    /**
     * Defaults the databases to it's default form
     * in case tables are missing
     * #defaultDatabase() should check for tables if they exist and add them
     */
    private void defaultDatabases() {
        for(MDatabase db : m_database.getDatabases()) {
            if(db.connectionExists()) {
                db.defaultDatabase();
            }
        }
    }

}
