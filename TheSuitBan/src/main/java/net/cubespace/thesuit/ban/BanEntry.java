/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.cubespace.thesuit.ban;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.sql.SQLException;
import java.sql.Timestamp;
import lombok.Data;
import lombok.experimental.Accessors;

import net.cubespace.thesuit.Core.Database.Player;

/**
 *
 * @author Robin
 */
@DatabaseTable(tableName = "ts_ban")
@Accessors(chain = true) @Data
public class BanEntry {
    @DatabaseField(generatedId = true)
    private int id;
    
    /**
     * The player who created this ban.
     * Can be null.
     * @return player who created this ban
     */
    @DatabaseField(foreign = true)
    private Player source;
    
        /**
     * Returns the banned player.
     * @return the target
     */
    @DatabaseField(canBeNull = false, foreign = true)
    private Player target;
    
    @DatabaseField
    private Timestamp until;
    
    /**
     * Returns the server this Ban is effective on.
     * If this is a global ban, this will return (GLOBAL)
     * 
     * @return the server
     */
    @DatabaseField(defaultValue = "(GLOBAL)")
    private String server;
    
    @DatabaseField
    private String reason;
    
    @DatabaseField
    private boolean ipBan;
    
    public BanEntry setGlobal() {
        return this.setServer("(GLOBAL)");
    }
    
    public static class Impl extends BaseDaoImpl {
        public Impl() throws SQLException {
            super(BanEntry.class);
        }
    }
}
