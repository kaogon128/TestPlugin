package info.ahaha.testplugin.test1;

import org.bukkit.plugin.java.JavaPlugin;

public class Test1 extends JavaPlugin {

    public static Test1 plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        getServer().getPluginManager().registerEvents(new ExplodeTriggerListener(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
