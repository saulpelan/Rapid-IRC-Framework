/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dark.rs.irc.event;

/**
 *
 * @author Saul
 */
public interface NoticeListener {
    public abstract void onNotice(Notice e);
    public abstract void onServerNotice(ServerNotice e);
    public abstract void onRawNumeric(RawNumeric e);
}
