package org.dark.rs.irc.event;

public interface ChannelListener {

    public abstract void onChannelMessage(ChannelMessage e);

    public abstract void onChannelModeChange(ChannelModeChange e);
}
