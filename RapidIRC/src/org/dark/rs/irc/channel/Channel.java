/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dark.rs.irc.channel;

import java.util.ArrayList;
import org.dark.rs.irc.Session;

/**
 *
 * @author Saul
 */
public class Channel {

    private final String name;
    private String topic;
    private String key;
    private Session session;
    private ArrayList<String> modes = new ArrayList<>();

    public Channel(Session session, String name) {
        this.name = name;
        this.session = session;
    }

    public String getName() {
        return name;
    }

    public void sendMessage(String message) {
        sendMessage(message, false);
    }

    public void sendMessage(String message, boolean action) {
        if (action) {
            session.write("PRIVMSG " + name + " :\u0001ACTION " + message + "\u0001");
        } else {
            session.write("PRIVMSG " + name + " " + message);
        }
    }

    public String[] getModes() {
        return (String[]) modes.toArray();
    }

    void setMode(String mode) {
        modes.add(mode);
    }
    
    void removeMode(String mode) {
        modes.remove(mode);
    }
}
