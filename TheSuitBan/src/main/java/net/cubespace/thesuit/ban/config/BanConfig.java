package net.cubespace.thesuit.ban.config;

import java.io.File;
import net.cubespace.Yamler.Config.Comment;
import net.cubespace.Yamler.Config.Config;
import net.cubespace.lib.Module.Module;

/**
 * @author roblabla (robinlambertz.dev@gmail.com)
 */
public class BanConfig extends Config {
/*    @Comment("Default ban reasons")
    public Void defaults;*/
    @Comment("If true, then %reason% in defaults below will be replaced by given reason.")
    public boolean defaults_reasonWrapper = false;
    public String defaults_banreason = "Banned by an operator.";
    public String defaults_gbanreason = "Banned by an operator.";
    public String defaults_tempbanreason = "Banned by an operator for %until%.";
    public String defaults_gtempbanreason = "Banned by an operator for %until%.";
    public String defaults_tempbantime = "1d";
}
