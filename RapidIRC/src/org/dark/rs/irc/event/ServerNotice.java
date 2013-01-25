package org.dark.rs.irc.event;

public class ServerNotice {

    private String sender;
    private String recipient;
    private String message;

    public ServerNotice(String sender, String recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }

    public void dispatch(NoticeListener l) {
        l.onServerNotice(this);
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
