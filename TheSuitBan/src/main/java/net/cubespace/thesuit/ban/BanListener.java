package net.cubespace.thesuit.ban;

import java.sql.SQLException;
import net.cubespace.lib.Module.Module;
import net.cubespace.thesuit.Core.Database.Player;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.jdeferred.AlwaysCallback;
import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.Promise;

/**
 *
 * @author epita
 */
class BanListener implements Listener {
    private final TheSuitBanModule m;
    public BanListener(TheSuitBanModule m) {
        this.m = m;
    }
    
    @EventHandler
    public void onPlayerLogin(final LoginEvent ev) {
        ev.registerIntent(m.getPlugin());
        m.getPlugin().getProxy().getScheduler().runAsync(m.getPlugin(), new Runnable() {

            public void run() {
                m.getBanManager().getEntry(ev.getConnection().getUUID()).done(new DoneCallback<BanEntry>() {
                    public void onDone(BanEntry entry) {
                        ev.setCancelled(true); // cancel if banned
                        ev.setCancelReason(entry.getReason());
                    }
                })
                .fail(new FailCallback<Exception>() {

                    public void onFail(Exception ex) {
                        if (ex instanceof SQLException) {
                            m.getModuleLogger().error("Retrieving ban from database failed !", ex);
                        } else if (ex instanceof NoBanFoundException) {
                            // Do nothing and smile :D
                        }
                    }
                })
                .always(new AlwaysCallback<BanEntry, Exception>() {
                    public void onAlways(Promise.State state, BanEntry d, Exception r) {
                        ev.completeIntent(m.getPlugin());
                    }
                });
            }
        });
    }
}
