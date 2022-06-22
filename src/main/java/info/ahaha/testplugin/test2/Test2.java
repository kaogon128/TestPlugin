package info.ahaha.testplugin.test2;

import org.bukkit.plugin.java.JavaPlugin;

public class Test2 extends JavaPlugin {

    @Override
    public void onEnable(){
        getCommand("game").setExecutor(new Command(this));
    }

    @Override
    public void onDisable(){

    }
}
