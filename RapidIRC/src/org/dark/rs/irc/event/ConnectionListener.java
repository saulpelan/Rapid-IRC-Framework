package org.dark.rs.irc.event;

public interface ConnectionListener {

    public abstract void onConnect(Connection e);

    public abstract void onError(Error e);
}
