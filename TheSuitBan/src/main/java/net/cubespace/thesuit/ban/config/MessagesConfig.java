/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.cubespace.thesuit.ban.config;

import net.cubespace.Yamler.Config.Config;

/**
 *
 * @author epita
 */
public class MessagesConfig extends Config {
    public String onFailedToBan = "[The Suit Ban] Failed to ban %target%";
    public String onBan = "%source% banned %banned% from %server% for %reason%";
    public String onBanIp = "%source% banned %banned% from %server% for %reason%";
    public String onGBan = "%source% global banned %banned% for %reason%";
    public String onGBanIp = "%source% global banned %banned% for %reason%";
    public String onTempBan = "%source% tempbanned %banned% from %server% until %until% for %reason%";
    public String onTempBanIp = "%source% tempbanned %banned% from %server% until %until% for %reason%";
    public String onGTempBan = "%source% global tempbanned %banned% until %until% for %reason%";
    public String onGTempBanIp = "%source% global tempbanned %banned% until %until% for %reason%";
    public String onUnban = "%banned% got unbanned from %server%!";
    public String onUnbanIp = "%banned% got unbanned from %server%!";
    public String onGUnban = "%banned% got unbanned globally!";
    public String onGUnbanIp = "%banned% got unbanned globally!";
}
