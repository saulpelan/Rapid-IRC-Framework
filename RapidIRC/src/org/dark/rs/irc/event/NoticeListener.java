package org.dark.rs.irc.event;

public interface NoticeListener {

    public abstract void onNotice(Notice e);

    public abstract void onServerNotice(ServerNotice e);

    public abstract void onRawNumeric(RawNumeric e);
}
