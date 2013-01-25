/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dark.rs.irc.event;

import org.dark.rs.irc.User;
import org.dark.rs.irc.channel.Channel;

/**
 *
 * @author Saul
 */
public class ChannelMessage {
    
    private Channel channel;
    private User sender;
    private String message;
    
    public ChannelMessage(Channel channel, User sender, String message) {
        this.channel = channel;
        this.sender = sender;
        this.message = message;
    }

    public void dispatch(ChannelListener l) {
        l.onChannelMessage(this);
    }

    public Channel getChannel() {
        return channel;
    }
    
    public User getSender() {
        return sender;
    }
    
    public String getMessage() {
        return message;
    }
    
    public int getWordCount() {
        return message.split(String.valueOf(' ')).length;
    }
    
    public int getWordCount(String seperator) {
        return message.split(seperator).length;
    }
    
    public String getWord(int index) {
        return getWord(index, String.valueOf(' '));
    }
    
    public String getWord(int index, String seperator) {
        return message.split(seperator)[index - 1];
    }
    
    public String getLineStartingAt(int wordIndex) {
        return message.substring(wordIndex);
    }
}
