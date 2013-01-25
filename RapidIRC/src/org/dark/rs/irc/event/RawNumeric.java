package org.dark.rs.irc.event;

public class RawNumeric {

    private String sender;
    private String id;
    private String subject;
    private String message;

    public RawNumeric(String sender, String id, String subject, String message) {
        this.sender = sender;
        this.id = id;
        this.subject = subject;
        this.message = message;
    }

    public void dispatch(NoticeListener l) {
        l.onRawNumeric(this);
    }

    public String getSender() {
        return sender;
    }

    public String getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
}
