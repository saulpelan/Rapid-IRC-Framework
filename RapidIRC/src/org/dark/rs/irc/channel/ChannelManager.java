package org.dark.rs.irc.channel;

import java.util.ArrayList;
import org.dark.rs.irc.Session;

public class ChannelManager {

    private ArrayList<Channel> channels = new ArrayList<>();
    private Session session;

    public ChannelManager(Session session) {
        this.session = session;
    }

    public Channel create(String name) {
        Channel c = new Channel(session, name);
        channels.add(c);
        return c;
    }

    public Channel get(String name) {
        Object[] array = channels.toArray();
        for (int i = 0; i < array.length; i++) {
            Channel c = (Channel) array[i];
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

    public void setMode(Channel chan, String mode) {
        String[] sections = mode.split("|");
        if (sections[0].equals("+")) {
            if (sections.length == 3) {
                chan.setMode(sections[1] + " " + sections[2]);
            } else if (sections.length == 2) {
                chan.setMode(sections[1]);
            }
        } else if (sections[0].equals("-")) {
            if (sections.length == 3) {
                chan.removeMode(sections[1] + " " + sections[2]);
            } else if (sections.length == 2) {
                chan.removeMode(sections[1]);
            }
        }
        chan.setMode(mode);
    }
}
