
package chatapplication;

import static chatapplication.ServerMsgDispatcher.msgQueue;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


/** 
 * ClientToClientDispactcher send messages that
 * are in queue to specific client
 * 
 */
public class ClientToClientDispactcher extends Thread {
    private Vector msgToClientQueue = new Vector();
    private String username;
    
    public ClientToClientDispactcher(String username){
        this.username=username;
    }
     @Override
    public void run() {
        while (true) {
            String msg;
            try {
                msg = getMsgfromQueue();
                msgToClient(msg);

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

    private synchronized void msgToClient(String msg) {
        for (Map.Entry<String,Socket> entry: ServerMsgDispatcher.clientList.entrySet()) {
            PrintStream PS;
           
            
            try {
                if(entry.getKey().equals(username)){
                PS = new PrintStream(entry.getValue().getOutputStream());
                PS.println(msg);
                }
            } catch (IOException ex) {
                Logger.getLogger(ServerMsgDispatcher.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
