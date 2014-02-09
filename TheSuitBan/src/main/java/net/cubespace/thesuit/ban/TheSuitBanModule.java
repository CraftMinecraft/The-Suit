package net.cubespace.thesuit.ban;

import net.cubespace.thesuit.ban.config.BanConfig;
import net.cubespace.thesuit.ban.config.MessagesConfig;
import net.cubespace.lib.Module.Module;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 * @author roblabla (robinlambertz.dev@gmail.com)
 */
public class TheSuitBanModule extends Module {
    private BanManager banManager;
    
    @Override
    public void onLoad() {
    }

    @Override
    public void onEnable() {
        this.banManager = new BanManager(this);
        this.getConfigManager().registerConfig("config", BanConfig.class);
        this.getConfigManager().registerConfig("messages", MessagesConfig.class);
        this.getPlugin().getProxy().getPluginManager().registerListener(plugin, new BanListener(this));
    }

    @Override
    public void onDisable() {

    }
    
    public BanManager getBanManager() {
        return this.banManager;
    }
    
    public BanConfig getConfig() {
        return this.getConfigManager().getConfig("config");
    }

    public MessagesConfig getMessages() {
        return this.getConfigManager().getConfig("messages");
    }
}