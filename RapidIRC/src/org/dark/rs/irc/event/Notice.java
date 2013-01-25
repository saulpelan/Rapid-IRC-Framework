/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dark.rs.irc.event;

/**
 *
 * @author Saul
 */
public class Notice {
    private String sender;
    private String recipient;
    private String message;

    public Notice(String sender, String recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }

    public void dispatch(NoticeListener l) {
        l.onNotice(this);
    }
    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }
}
