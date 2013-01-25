/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dark.rs.irc;

import org.dark.rs.irc.channel.ChannelMode;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import org.dark.rs.irc.channel.ChannelManager;
import org.dark.rs.irc.event.ChannelMessage;
import org.dark.rs.irc.event.ChannelModeChange;
import org.dark.rs.irc.event.Connection;
import org.dark.rs.irc.event.Error;
import org.dark.rs.irc.event.EventManager;
import org.dark.rs.irc.event.Notice;
import org.dark.rs.irc.event.RawNumeric;
import org.dark.rs.irc.event.ServerNotice;
import org.dark.rs.irc.internal.IRCManager;

public class Session extends EventManager {

    public static final int DEFAULT_PORT = 6667;
    private ChannelManager chManager = new ChannelManager(this);
    private IRCManager ircManager = new IRCManager(this);
    private String nickname;
    private String realname;
    private String ident;
    private String name;
    private String server;
    private String networkName;
    private int port;
    private Socket socket;
    private OutputStreamWriter writer;
    private InputStreamReader reader;
    private static ArrayList<Session> sessions = new ArrayList<>();
    private HashMap<String, String> attributes = new HashMap();
    private final int id;
    private boolean connected = false;
    private int loginStatus = -1;
    private String channels = "";
    private HashMap<ChannelMode, String> prefixes = new HashMap();
    private String nickservPassword = "";

    private Session(int id, String name, String server, int port) {
        this.id = id;
        this.name = name;
        this.server = server;
        this.port = port;
    }

    public static Session create(String name, String server, int port) {
        Session s = new Session(sessions.size() + 1, name, server, port);
        sessions.add(s);
        return s;
    }

    public static Session get(String name) {
        for (Session s : Arrays.copyOf(sessions.toArray(), sessions.size(), Session[].class)) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    public void connect() throws IOException {
        socket = new Socket(server, port);
        writer = new OutputStreamWriter(socket.getOutputStream());
    }

    public void disconnect() {
    }

    public void login() {
        login(nickname, ident, realname);
    }

    public void login(String nickname, String ident, String realname) {
        login(nickname, ident, realname, null);
    }

    public void login(String nickname, String ident, String realname, String nickPassword) {
        write("NICK " + nickname);
        write("USER " + ident + " * * :" + realname);
        if (nickPassword != null && !nickPassword.isEmpty()) {
            write("PRIVMSG NickServ IDENTIFY " + nickPassword);
        }
        //loggedIn = true;
    }

    public String getSetting(String key) {
        Object a = attributes.get(key);
        if (a instanceof String) {
            return (String) a;
        }
        return null;
    }

    public String getNickservPassword() {
        return nickservPassword;
    }

    public void setNickservPassword(String nickservPassword) {
        this.nickservPassword = nickservPassword;
    }

    
    public boolean containsKey(String key) {
        return attributes.containsKey(key);
    }

    public boolean containsValue(String value) {
        return attributes.containsValue(value);
    }

    public int getLogInStatus() {
        return loginStatus;
    }

    public void setLogInStatus(int phase) {
        this.loginStatus = phase;
    }

    public ChannelManager getChannelManager() {
        return chManager;
    }

    public IRCManager getIRCManager() {
        return ircManager;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getPrefix(ChannelMode m) {
        return prefixes.get(m);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void joinChannel(String channel) {
        write("JOIN " + channel);
    }

    public void leaveChannel(String channelName) {
        leaveChannel(channelName, "");
    }

    public void leaveChannel(String channelName, String reason) {
        write("PART " + channelName + " " + reason);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Socket getSocket() {
        return socket;
    }

    public OutputStreamWriter getWriter() {
        return writer;
    }

    public InputStreamReader getReader() {
        return reader;
    }

    public void write(String str) {
        try {
            if (str.endsWith("\r\n")) {
                writer.write(str);
            } else {
                writer.write(str + "\r\n");
            }
            writer.flush();
            System.out.println("-> " + str);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onChannelMessage(ChannelMessage e) {
    }

    @Override
    public void onChannelModeChange(ChannelModeChange e) {
    }

    @Override
    public void onConnect(Connection e) {
    }

    @Override
    public void onServerNotice(ServerNotice e) {
        if (e.getRecipient().equals("AUTH")) {
            setConnected(true);
            if (getLogInStatus() == -1) {
                setLogInStatus(0);
                login(nickname, ident, realname, nickservPassword);
            }
        }
    }

    @Override
    public void onNotice(Notice e) {
    }

    @Override
    public void onRawNumeric(RawNumeric e) {
        if (e.getId().equals("001")) {
            setLogInStatus(1);
        } else if (e.getId().trim().equals("005")) {
            String[] aa = e.getMessage().substring(0, e.getMessage().lastIndexOf(":")).split(" ");
            for (String a : aa) {
                if (a.contains("=")) {
                    attributes.put(a.split("=")[0], a.split("=")[1]);
                    System.out.println("Stored \"" + a.split("=")[0] + "\" as \"" + a.split("=")[1] + "\"");
                    switch (a.split("=")[0]) {
                        case "CHANMODES":
                            String modeChunk = a.split("=")[1];
                            String[] modeGroups = modeChunk.split(",");
                            for (int x = 0; x < modeGroups.length; x++) {
                                char[] modes = modeGroups[x].toCharArray();
                                if (x == modeGroups.length - 1) {
                                    for (char c : modes) {
                                        ChannelMode.set(c, false);
                                    }
                                } else {
                                    for (char c : modes) {
                                        ChannelMode.set(c, true);
                                    }
                                }
                            }
                            break;
                        case "PREFIX":
                            String chunk = a.split("=")[1];
                            char[] modes = chunk.substring(chunk.indexOf("(") + 1, chunk.indexOf(")")).toCharArray();
                            char[] prefs = chunk.substring(chunk.indexOf(")") + 1).toCharArray();
                            for (int x = 0; x < modes.length; x++) {
                                char m = modes[x];
                                ChannelMode chMode = ChannelMode.set(m, true);
                                prefixes.put(chMode, String.valueOf(prefs[x]));
                            }
                            break;
                    }
                } else {
                    attributes.put(a, a);
                }
            }
        } else if (e.getId().equals("375")) {
            joinChannel(channels);
        } else if (e.getId().equals("432")) {
            if (loginStatus == 0) {
                setNickname("injeX" + new Random().nextInt(99999));
                login(nickname, ident, realname);
            }
        } else if (e.getId().equals("433")) {
            if (loginStatus == 0) {
                setNickname(nickname + "_");
                login(nickname, ident, realname);
            }
        }
    }

    @Override
    public void onError(Error e) {
        System.err.println("ERROR -> " + e.getMessage());
    }
}
