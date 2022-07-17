package dev.kezhall.regionhud.regionhud;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class RegionBar {

    private Player player;
    private BossBar bar;

    public String serverTitle = "HammerCraft";
    public String wildernessTitle = "Wilderness";
    public String regionName = wildernessTitle;
    public String barTitle = ChatColor.LIGHT_PURPLE + serverTitle + ChatColor.WHITE + " - ";


    public RegionBar(Player player){
        bar = Bukkit.createBossBar(barTitle + regionName, BarColor.PURPLE, BarStyle.SEGMENTED_6);
        bar.setVisible(true);

        addPlayer(player);
    }

    public void addPlayer(Player player){
        this.player = player;
        bar.addPlayer(player);
    }

    public BossBar getBar(){
        return bar;
    }

    private WorldGuardPlugin getWorldGuard(){
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

        if(plugin == null || !(plugin instanceof WorldGuardPlugin)){
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }


    public void checkForRegion(Player player) {
        Location loc = BukkitAdapter.adapt(player.getLocation());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(loc);

        if(set.getRegions().size() == 0){
            regionName = wildernessTitle;
        }

        for(ProtectedRegion region : set.getRegions()) {
            regionName = region.getId();
            regionName = regionName.substring(0, 1).toUpperCase() + regionName.substring(1);
        }

        bar.setTitle(barTitle + regionName);
    }
}
