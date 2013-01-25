/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dark.rs.irc.internal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngineManager;
import org.dark.rs.irc.Session;
import org.dark.rs.irc.event.RawNumeric;
import org.dark.rs.irc.event.ServerNotice;

/**
 *
 * @author Saul
 */
public class IRCManager {

    private final Session session;
    boolean loop = true;
    private int phase = 0;

    public IRCManager(final Session session) {
        this.session = session;
        Timer t = new Timer("Test Connection", true);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (phase > 0) {
                    if (!session.isConnected()) {
                        session.setConnected(true);
                    }
                } else {
                    if (session.isConnected()) {
                        session.setConnected(false);
                    }
                }
            }
        };
        t.scheduleAtFixedRate(task, 0, 1);
    }

    public void start() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(session.getInputStream()));
            String line = "";
            while (loop & phase > -1 & (line = reader.readLine()) != null) {
                System.out.println("<- " + line);
                if (phase == 0) {
                    phase++;
                }
                Scanner s = new Scanner(line);
                if (s.hasNext()) {
                    String word1 = s.next();
                    if (word1.equals("ERROR")) {
                        if (s.hasNext()) {
                            String word2 = s.nextLine();
                            org.dark.rs.irc.event.Error error = new org.dark.rs.irc.event.Error(word2.replaceFirst(":", "").trim());
                            error.dispatch(session);
                        }
                    }
                    if (s.hasNext()) {
                        String word2 = s.next();
                        if (s.hasNext()) {
                            String word3 = s.next();
                            if (s.hasNext()) {
                                String word4 = s.nextLine().trim();
                                if (line.startsWith(":")) {
                                    String sender = word1.replaceFirst(":", "");
                                    String event = word2;
                                    if (!sender.contains("!") && !sender.contains("@")) {
                                        if (word4.startsWith(":")) {
                                            if (event.equals("NOTICE")) {
                                                String recipient = word3;
                                                String message = word4.replaceFirst(":", "");
                                                if (phase != 1) {
                                                    phase = 1;
                                                }
                                                ServerNotice e = new ServerNotice(sender, recipient, message);
                                                e.dispatch(session);
                                            }
                                        }
                                        if (event.trim().matches("\\d\\d\\d")) { //Numeric Event
                                            String raw = event;
                                            String subject = word3.trim();
                                            String message = word4.trim();
                                            if (word4.charAt(0) == ':') {
                                                message = message.replaceFirst(":", "");
                                            }
                                            RawNumeric e = new RawNumeric(sender, raw, subject, message);
                                            e.dispatch(session);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(IRCManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(IRCManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
