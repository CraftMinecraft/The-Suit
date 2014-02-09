/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.cubespace.thesuit.ban;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import net.cubespace.lib.Chat.FontFormat;
import net.cubespace.lib.Chat.MessageBuilder.MessageBuilder;
import net.cubespace.lib.Command.CLICommand;
import net.cubespace.lib.Command.Command;
import net.cubespace.thesuit.Core.Database.Player;
import net.md_5.bungee.api.CommandSender;

/**
 *
 * @author epita
 */
public class BanCommand implements CLICommand {

    private TheSuitBanModule m;

    public BanCommand(TheSuitBanModule m) {
        this.m = m;
    }

    // /gban <player> [ipban] [from <server> | globally] [until <date> | during <time>] [for <reason>]
    @Command(arguments = 1, command = "gban")
    public void onGban(CommandSender sender, String[] args) {
        m.getPlugin().getProxy().getScheduler().runAsync(m.getPlugin(), createRunnable(sender, args));
    }

    private Runnable createRunnable(CommandSender sender, String[] args) {
        return new Runnable() {
            CommandSender sender;
            String[] args;

            public Runnable init(CommandSender sender, String[] args) {
                this.sender = sender;
                this.args = args;
                return this;
            }

            public void run() {
                BanEntry entry = new BanEntry();
                if (!setTarget(entry, args[0])) {
                    new MessageBuilder()
                            .setText(FontFormat.translateString(m.getMessages().onFailedToBan))
                            .setVariable("target", args[0])
                            .send(sender);
                    return;
                }
                for (int i = 1; i < args.length; i++) {
                    i = parseArgs(entry, args, i);
                }
                m.getBanManager().ban(entry);
            }
        }.init(sender, args);
    }

    private int parseArgs(BanEntry entry, String[] args, int i) {
        switch (args[i].toLowerCase()) {
            case "from":
                String from = args[++i];
                entry.setServer(from);
                break;
            case "globally":
                entry.setGlobal();
                break;
            case "ipban":
                entry.setIpBan(true);
                break;
            case "during":
                Timestamp date = Utils.parseUntil(args[++i]);
                entry.setUntil(date);
            case "for":
                StringBuilder builder = new StringBuilder();
                for (i++;i < args.length;i++) {
                    builder.append(args[i]);
                }
                entry.setReason(builder.toString());
                break;
            default:
                throw new IllegalArgumentException(args[i] + " doesn't exist");
        }
        return i;
    }

    private boolean setTarget(BanEntry entry, String player) {
        try {
            Player p = (Player) m.getPlugin().getDatabase().getDAO(Player.class)
                    .queryBuilder().where().eq("name", player)
                    .queryForFirst();
            if (p == null) {
                return false;
            }
            entry.setTarget(p);
            return true;
        } catch (SQLException ex) {
            m.getModuleLogger().error("Couldn't find player's name", ex);
            return false;
        }
    }
}

// /gban roblabla
