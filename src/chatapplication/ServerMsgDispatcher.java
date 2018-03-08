
package chatapplication;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


/** 
 * ServerMsgDispatcher send messagies that 
 * are in queue to all clients
 * 
 */
public class ServerMsgDispatcher extends Thread {

    static HashMap<String, Socket> clientList = new HashMap<>();
    static Vector<String> msgQueue = new Vector();

    @Override
    public void run() {
        while (true) {
            String msg;
            try {
                msg = getMsgfromQueue();
                msgToAll(msg);

            } catch (Exception ex) {
                Logger.getLogger(ServerMsgDispatcher.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public synchronized void dispatchMsg(String msg) {
        msgQueue.add(msg);
        notify();
    }

    private synchronized String getMsgfromQueue() throws InterruptedException {
        while (msgQueue.isEmpty()) {
            wait();
        }

        String firstStringInQueue = msgQueue.get(0);
        msgQueue.remove(0);
        return firstStringInQueue;

    }

    private synchronized void msgToAll(String msg) {
        for (Map.Entry<String,Socket> entry: clientList.entrySet()) {
            PrintStream PS;
            System.out.println(entry.getValue());
            
            try {
                PS = new PrintStream(entry.getValue().getOutputStream());
                PS.println(msg);
                
            } catch (IOException ex) {
                Logger.getLogger(ServerMsgDispatcher.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
