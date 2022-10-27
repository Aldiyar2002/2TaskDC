package org.example;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Processor of HTTP request.
 */
public class Processor implements Runnable{
    private final Socket socket;
    private final HttpRequest request;

    public Processor(Socket socket, HttpRequest request) {
        this.socket = socket;
        this.request = request;
    }

    public void process() throws IOException {
        System.out.println("Got request:");
        String text = request.getRequestLine();
        System.out.println(request.toString());
        System.out.flush();

        PrintWriter output = new PrintWriter(socket.getOutputStream());
        //"exec/params".equals(requestLine.substring(5, 16))
        //"create/something.txt".equals(requestLine.substring(5, 25))
        //"delete/something.txt".equals(requestLine.substring(5, 25))
        if("create".equals(text.substring(5, 11))){
            File file = new File("D:\\something.txt");
            boolean result;
            try
            {
                result = file.createNewFile();  //creates a new file
                if(result)      // test if successfully created a new file
                {
                    System.out.println("file created "+file.getCanonicalPath()); //returns the path string
                }
                else
                {
                    System.out.println("File already exist at location: "+file.getCanonicalPath());
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();    //prints exception if any
            }
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Task 1</title></head>");
            output.println("<body><p>Congratulations, something.txt file in drive D has been created</p></body>");
            output.println("<body><p>now. write /exec to check execution</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        }
        else if("delete".equals(text.substring(5, 11))){

            try
            {
                File f= new File("D:\\something.txt");
                if(f.delete())                      //returns Boolean value
                {
                    System.out.println(f.getName() + " deleted");   //getting and printing the file name
                }
                else
                {
                    System.out.println("failed");
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Task 1</title></head>");
            output.println("<body><p>Congratulations, something.txt file in drive D has been completely deleted</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        }
        else if("exec".equals(text.substring(5, 9))){

            final long startTime = System.currentTimeMillis();
            int x = 1234567899;
            int sum = 0;
            for(int I = 1; I <= x ; I ++){
                if(x % I ==0){
                    sum += I ;
                }
            }
            PrintWriter out = new PrintWriter("D:\\something.txt");

            out.println(sum);
            // Close the file.
            out.close();
            System.out.println ("The sum of the factors is " + sum);

            final long endTime = System.currentTimeMillis();
            System.out.println("Total execution time: " + (endTime - startTime));


            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Task 1</title></head>");
            output.println("<body><p>you can check something.txt file. In this file you will see the output</p></body>");
            output.println("<body><p>now enter /delete to delete this file</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        }
//        else if("threadh".equals(text.substring(5, 12))) {
//
//            ThreadH thrh = new ThreadH();
//            thrh.ad();
//            output.println("HTTP/1.1 200 OK");
//            output.println("Content-Type: text/html; charset=utf-8");
//            output.println();
//            output.println("<html>");
//            output.println("<head><title>Task 1</title></head>");
//            output.println("<body><p>This is THreadH</p></body>");
//            output.println("<body><p>Let's start</p></body>");
//            output.println("<body><p>write /create to create txt file in your drive D</p></body>");
//            output.println("</html>");
//            output.flush();
//            socket.close();
//        }
        else {
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Task 1</title></head>");
            output.println("<body><p>Here you can create txt file, check execution and delete it</p></body>");
            output.println("<body><p>Let's start</p></body>");
            output.println("<body><p>write /create to create txt file in your drive D</p></body>");
            output.println("</html>");
            output.flush();
            socket.close();
        }



    }
    @Override
    public void run(){
        try {
            process();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}