/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dark.rs.irc.event;

public class Error {
    
    private String message;
    
    public Error(String message) {
        this.message = message;
    }
    
    public void dispatch(ConnectionListener l) {
        l.onError(this);
    }

    public String getMessage() {
        return message;
    }
}
