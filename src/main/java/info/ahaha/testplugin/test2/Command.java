package info.ahaha.testplugin.test2;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class Command implements CommandExecutor {

    private final EventListener eventListener = new EventListener();
    private boolean on = true;
    private final Test2 plugin;

    public Command(Test2 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

        // on が trueだったらイベントを登録して
        if (on){
            Bukkit.getServer().getPluginManager().registerEvents(eventListener,plugin);
            on = false;
        }else {
            // falseだったら　イベントを解除
            removeListener();
            on = true;
        }
        return true;
    }

    public void removeListener(){
        HandlerList.unregisterAll(eventListener);
    }
}
