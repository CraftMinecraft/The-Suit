/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.cubespace.thesuit.ban;

/**
 *
 * @author epita
 */
public class NoBanFoundException extends Exception {

    /**
     * Creates a new instance of <code>NoBanFoundException</code> without detail
     * message.
     */
    public NoBanFoundException() {
    }

    /**
     * Constructs an instance of <code>NoBanFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoBanFoundException(String msg) {
        super(msg);
    }
}
