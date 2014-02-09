package net.cubespace.thesuit.ban;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.cubespace.lib.Database.Database;
import net.cubespace.lib.Module.Module;
import net.cubespace.thesuit.Core.TheSuitCoreModule;
import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

/**
 *
 * @author Robin
 */
public class BanManager {

    private Module m;
    private Database db;

    public BanManager(Module m) {
        this.m = m;
        this.db = m.getPlugin().getModuleManager().getModule(TheSuitCoreModule.class)
                .getDatabase();
        try {
            m.getModuleLogger().info("hi");
            db.registerDAO(new BanEntry.Impl(), BanEntry.class);
        } catch (SQLException ex) {
            m.getModuleLogger().error("Could not create table", ex);
        }
        db.getDAO(BanEntry.class);
    }

    public Promise<Boolean, Exception, Void> isBanned(final String player) {
        final Deferred<Boolean, Exception, Void> def = new DeferredObject();
        m.getPlugin().getProxy().getScheduler().runAsync(m.getPlugin(), new Runnable() {
            public void run() {
                try {
                    def.resolve(db.getDAO(BanEntry.class).queryForEq("target", player.toLowerCase()).isEmpty());
                } catch (SQLException ex) {
                    def.reject(ex);
                }

            }
        });
        return def.promise();
    }

    public Promise<BanEntry, Exception, Void> getEntry(final String player) {
        final Deferred<BanEntry, Exception, Void> def = new DeferredObject();
        m.getPlugin().getProxy().getScheduler().runAsync(m.getPlugin(), new Runnable() {
            public void run() {
                try {
                    List<BanEntry> entries = db.getDAO(BanEntry.class).queryForEq("target", player.toLowerCase());
                    if (entries.size() < 1) {
                        def.reject(new NoBanFoundException());
                        return;
                    }
                    def.resolve(entries.get(0));
                } catch (SQLException ex) {
                    def.reject(ex);
                }

            }
        });
        return def.promise();
    }

    public void ban(final BanEntry entry) {
        m.getPlugin().getProxy().getScheduler().runAsync(m.getPlugin(), new Runnable() {
            public void run() {
                try {
                    db.getDAO(BanEntry.class).create(entry);
                } catch (SQLException ex) {
                    m.getModuleLogger().error("Could not create ban", ex);
                }
            }
        });
    }

}
