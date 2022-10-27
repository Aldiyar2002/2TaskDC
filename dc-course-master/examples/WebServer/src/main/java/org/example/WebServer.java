package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Simple web server.
 */
public class WebServer {
    public static void main(String[] args) {
        // Port number for http request
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 8080;
        // The maximum queue length for incoming connection
        int queueLength = args.length > 2 ? Integer.parseInt(args[2]) : 50;;

        ThreadSafeQueue<Socket> queue = new ThreadSafeQueue<>();
        int Threads = 21;

        try (ServerSocket serverSocket = new ServerSocket(port, queueLength)) {
            System.out.println("Web Server is starting up, listening at port " + port + ".");
            System.out.println("You can access http://localhost:" + port + " now.");

            for (int i = 0; i < Threads; i++) {
                Consumer<Socket> cons = new Consumer<>(i, queue);
                cons.start();
            }

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Got Connection");
                queue.add(socket);
            }

//            while (true) {
//                // Make the server socket wait for the next client request
//                Socket socket = serverSocket.accept();
//                System.out.println("Got connection!");
//
//                // To read input from the client
//                BufferedReader input = new BufferedReader(
//                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
//
//                // Get request
//                HttpRequest request = HttpRequest.parse(input);
//
//                // Process request
////                Processor proc = new Processor(socket, request);
////                proc.process();
//
//                Thread thrd = new Thread(new Processor (socket, request));
//                thrd.start();
//
//                Thread thrd1 = new Thread(new Processor (socket, request));
//                thrd1.start();
//
//                Thread thrd2 = new Thread(new Processor (socket, request));
//                thrd2.start();
//            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            for (int i = 0; i < Threads; i++){
                queue.add(null);
            }
            System.out.println("Server has been shutdown!");
        }
    }
}
