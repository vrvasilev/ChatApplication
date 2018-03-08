/*
 * ChatApllication is a server/cient multithread program.
 * Clients have two options, sending messagies to all clients or  
 * sending to specific client.
 * 
 */
package chatapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**@author Vasil Vasilev 
 * Main method of ChatApplication is a entry point of the program.
 * It is create a server socket and start accept client connections
 * and authorize them. 
 */
public class ChatApplication {

    public static void main(String[] args) {
        int port = 8821;

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                // start listening on server socket
                Socket socket = serverSocket.accept();
                ClientAuthorize(socket);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
/**
 * ClientAuthorize is a simple method to authorize clients.
 * If the authorization is true then the method add new handler 
 * for client requests and start a thread.
 * 
 */
    private static void ClientAuthorize(Socket socket) throws IOException {
        ArrayList<HandleClientSocket> list = new ArrayList<>();
        BufferedReader authoriseReader = null;

        try {

            authoriseReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg = authoriseReader.readLine();
            if (msg.equals("John") || msg.equals("Doe")) {
                ServerMsgDispatcher.clientList.put(msg, socket);
            }
            // Adding and starting handle thread
            list.add(new HandleClientSocket(socket,msg));
            list.get(list.size() - 1).start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
