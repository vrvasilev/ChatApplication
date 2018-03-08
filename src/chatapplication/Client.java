
package chatapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client main thread run to write messagies.
 * ReadMsg accept income reply from other clients
 */
public class Client {

    public static void main(String[] args) throws Exception {
        //Connection to server
        Socket clientSocket = new Socket("localhost", 8821);
        
        Scanner scanner = new Scanner(System.in);
        String msg;
        PrintStream clientPrintStream = new PrintStream(clientSocket.getOutputStream());
        ReadMsg reader = new ReadMsg(clientSocket);
        reader.start();

        System.out.println("client");
        while (true) {
            System.out.print("Client:");

            msg = scanner.nextLine();
            clientPrintStream.println(msg);

            /* InputStreamReader IR = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader BR = new BufferedReader(IR);
           
            String MESSAGE = BR.readLine();
            System.out.println(MESSAGE);
             */
        }

    }

}

class ReadMsg extends Thread {

    private Socket clientSocket;

    ReadMsg(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        BufferedReader BR = null;

        try {
            BR = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while (true) {
                String msg = BR.readLine();
                System.out.println(msg);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
