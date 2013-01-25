package org.dark.rs.irc.event;

public class Error {

    private String message;

    public Error(String message) {
        this.message = message;
    }

    public void dispatch(ConnectionListener l) {
        l.onError(this);
    }

    public String getMessage() {
        return message;
    }
}
