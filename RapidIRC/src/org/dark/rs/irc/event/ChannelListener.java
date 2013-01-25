/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dark.rs.irc.event;

/**
 *
 * @author Saul
 */
public interface ChannelListener {
    public abstract void onChannelMessage(ChannelMessage e);
    public abstract void onChannelModeChange(ChannelModeChange e);
}
