package org.dark.rs.irc.event;

import org.dark.rs.irc.Session;

public class Connection {

    private String server;
    private Session session;

    public Connection(String server, Session session) {
        this.server = server;
        this.session = session;
    }

    public void dispatch(ConnectionListener l) {
        l.onConnect(this);
    }

    public String getServer() {
        return server;
    }

    public Session getSession() {
        return session;
    }
}
