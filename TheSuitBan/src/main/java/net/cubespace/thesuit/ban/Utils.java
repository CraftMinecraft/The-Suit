/*
 */
package net.cubespace.thesuit.ban;

import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Robin
 */
class Utils {

    static Timestamp parseUntil(String string) {
        Date now = new Date();
        long seconds = -1, minutes = -1, hours = -1, days = -1, months = -1, years = -1;
        int pow = 0;
        long current = 0;
        for (int i = 0; i < string.length(); i++) {
            if (Character.isLetter(string.charAt(i))) {
                switch (string.charAt(i)) {
                    case 's':
                        if (seconds == -1) {
                            seconds = current;
                        } else {
                            throw new IllegalArgumentException();
                        }
                        break;
                    case 'm':
                        if (minutes == -1) {
                            minutes = current;
                        } else if (months == -1) {
                            months = current;
                        } else {
                            throw new IllegalArgumentException();
                        }
                        break;
                    case 'h':
                        if (hours == -1) {
                            hours = current;
                        } else {
                            throw new IllegalArgumentException();
                        }
                        break;
                    case 'd':
                        if (days == -1) {
                            days = current;
                        } else {
                            throw new IllegalArgumentException();
                        }
                        break;
                    case 'y':
                        if (years == -1) {
                            years = current;
                        } else {
                            throw new IllegalArgumentException();
                        }
                        break;
                }
            } else if (Character.isDigit(string.charAt(i))) {
                current += Character.getNumericValue(string.charAt(i)) * Math.pow(10, pow);
            } else {
                throw new IllegalArgumentException();
            }
        }

        months += years * 12;
        days += months * 30;
        hours += days * 24;
        minutes += hours * 60;
        seconds += minutes * 60;
        return new Timestamp(seconds * 1000);
    }
}
