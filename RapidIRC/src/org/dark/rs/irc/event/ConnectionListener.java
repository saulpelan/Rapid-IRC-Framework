/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dark.rs.irc.event;
 //import org.dark.rs.irc.event.Error;
/**
 *
 * @author Saul
 */
public interface ConnectionListener {
    public abstract void onConnect(Connection e);
    public abstract void onError(Error e);
}
