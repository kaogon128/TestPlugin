package info.ahaha.testplugin.test1;

import info.ahaha.testplugin.TestPlugin;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ExplodeTriggerListener implements Listener {

    private boolean s = true;
    private double progress = 1.0;
    private Bar bar;

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack hand = p.getInventory().getItemInMainHand();
        World w = p.getWorld();
        final int count = 10;
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getHand() != EquipmentSlot.HAND) return;
        if (hand.getType() != Material.STICK) return;
        if (!s) return;

        Bukkit.broadcastMessage("発生");
        s = false;

        // 0.1ずつ引いていくと0.7になった時点で表示が0.700000000001..みたいな感じになったので対策です
        // https://www.delftstack.com/ja/howto/java/how-to-round-a-double-to-two-decimal-places-in-java/#math.rounddouble100.0%2f100.0-%25E3%2582%2592%25E7%2594%25A8%25E3%2581%2584%25E3%2581%259F-double-%25E3%2581%25AE%25E5%25B0%258F%25E6%2595%25B0%25E7%2582%25B9%25E4%25BB%25A5%25E4%25B8%258B-2-%25E6%25A1%2581%25E3%2581%25B8%25E3%2581%25AE%25E4%25B8%25B8%25E3%2582%2581
        progress = Math.round((progress - 0.1) * 10.0) / 10.0;

        if (bar == null) {
            bar = new Bar();
            bar.createBar();
            Bukkit.getOnlinePlayers().forEach((player) -> {
                bar.addPlayer(player);
            });
            // progressの監視をしなきゃいけないので0以下になるまでRunnableを走らせます
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (progress <= 0) {
                        Bukkit.getOnlinePlayers().forEach((player) -> {
                            bar.removePlayer(player);
                        });
                        bar = null;
                        progress = 1.0;
                        this.cancel();
                        return;
                    }
                    bar.getBar().setProgress(progress);
                }
            }.runTaskTimer(TestPlugin.plugin, 0, 2);
        }

        new BukkitRunnable() {
            int i = count;

            @Override
            public void run() {
                if (i == 0) {
                    Location l = p.getLocation();
                    w.createExplosion(l, 100, false, false);
                    s = true;
                    Bukkit.broadcastMessage("現在の値 : " + progress);
                    this.cancel();
                    return;
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Location loc = player.getLocation();
                    loc.getWorld().playSound(loc, Sound.BLOCK_NOTE_BLOCK_HARP, 1, 10);
                    player.sendMessage(ChatColor.RED + p.getName() + "の禁止行為を検知しました。 爆発まで : " + i);
                }
                i--;
            }
        }.runTaskTimer(TestPlugin.plugin, 100L, 20L);
    }

}
