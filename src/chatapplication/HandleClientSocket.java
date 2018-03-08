
package chatapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;


/**
 * HandleClientSocket class run a thread for every client and dispacth
 * messagies for all or for specific client
 *
 */
public class HandleClientSocket extends Thread {
//Socket and client`s name
    private Socket socket;
    private String name;
    ServerMsgDispatcher dispacher = new ServerMsgDispatcher();
    

    HandleClientSocket(Socket socket, String idName) {
        this.socket = socket;
        this.name = idName;
    }

    @Override
    public void run() {
       dispacher.start();
         
        while (true) {
            Scanner scanner = new Scanner(System.in);
            InputStreamReader IR;
            try {
                // read client`s messagies
                IR = new InputStreamReader(socket.getInputStream());

                BufferedReader BR = new BufferedReader(IR);
                String msg = BR.readLine();
                System.out.println("Client:" + msg);
                String[] msgSplit = msg.split("\\s+");
                // deliver based on first string
                // send msg to all clients
                if (msgSplit[0].equals("all")) {
                    dispacher.dispatchMsg(msg);
                    
                    
                }
                //send msg to specific client
                if(msgSplit[0].equals("Doe")){
                    ClientToClientDispactcher clientToClientdispacher = new ClientToClientDispactcher("Doe");
                    clientToClientdispacher.dispatchMsg(msg);
                    clientToClientdispacher.start();
                }
           /*     else{
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                printStream.println("wrong");
                }
           */
               
              
                 
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
