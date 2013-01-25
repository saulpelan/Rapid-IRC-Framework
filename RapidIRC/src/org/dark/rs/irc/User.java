/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dark.rs.irc;

/**
 *
 * @author Saul
 */
public class User {

    private String nickname;
    private String ident;
    private String host;
    private Session session;

    public User(Session session, String nickname) {
        this.nickname = nickname;
        this.session = session;
    }

    public void sendMessage(String location, String message) {
        sendMessage(location, message, false);
    }
    
    public void sendMessage(String location, String message, boolean action) {
        if (action) {
            session.write("PRIVMSG " + nickname + " :\u0001ACTION " + message + "\u0001");
        } else {
            session.write("PRIVMSG " + nickname + " " + message);
        }
    }
}
