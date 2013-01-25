/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dark.rs.irc.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Saul
 */
public final class ChannelMode {
    public final char mode;
    public final boolean params;
    public static final ArrayList<ChannelMode> modes = new ArrayList<>();
    
    private ChannelMode(char mode, boolean params) {
        this.mode = mode;
        this.params = params;
    }

    public static ChannelMode set(char mode, boolean params) {
        ChannelMode m = new ChannelMode(mode, params);
        modes.add(m);
        return m;
    }
    
    public static ChannelMode get(char mode) {
        for (int i = 0; i < modes.toArray().length; i++) {
            if (modes.toArray()[i] instanceof ChannelMode) {
                ChannelMode m = (ChannelMode) modes.toArray()[i];
                if (m.getMode() == mode) {
                    return m;
                }
            }
        }
        return null;
    }
    
    public static String[] parse(final String chunk) {
        final String[] sections = chunk.split(" ");
        final String modeChunk = sections[0];
        char[] chars = modeChunk.toCharArray();
        final ArrayList<String> modeList = new ArrayList<>();
        char currentMod = '.';
        int index = 1;
        for (char c : chars) {
            if (c == '+' || c == '-') {
                currentMod = c;
            } else {
                if (get(c).hasParam()) {
                    modeList.add(currentMod+"|"+c+"|"+sections[index]);
                    index++;
                } else {
                    modeList.add(currentMod+"|"+c);
                }
            }
        }
        return (String[]) modeList.toArray();
    }
    public char getMode() {
        return mode;
    }
    
    public boolean hasParam() {
        return params;
    }
    
    
}
