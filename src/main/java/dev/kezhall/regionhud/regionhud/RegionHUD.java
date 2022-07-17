package dev.kezhall.regionhud.regionhud;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class RegionHUD extends JavaPlugin implements Listener {

    public static HashMap<Player, RegionBar> bars = new HashMap<Player, RegionBar>();

    @Override
    public void onEnable() {
        System.out.println("[RegionHUD] Starting up..");

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

        System.out.println("[RegionHUD] Shutting down..");

        for (Player p : bars.keySet()) {
            RegionBar bar = bars.get(p);
            bar.getBar().removeAll();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        createBar(event.getPlayer());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){

        Player p = e.getPlayer();

        if(bars.containsKey(p)){
            RegionBar bar = bars.get(p);
            bar.checkForRegion(p);
        }else{
            // This could be due to admin doing ./reload
            createBar(p);
        }
    }

    public void createBar(Player player){

        // Create a bar for the player
        RegionBar bar = new RegionBar( player);

        // Add this bar to our hashmap of bars
        bars.put(player, bar);
    }
}
