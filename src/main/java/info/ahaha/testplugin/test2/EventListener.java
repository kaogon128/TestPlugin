package info.ahaha.testplugin.test2;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onTest(AsyncPlayerChatEvent e){
        e.setCancelled(true);

        e.getPlayer().sendMessage("喋ったらあかんで！！");
    }
}
