package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String requestLine;
    private final Map<String, String> headers;
    private final String messageBody;

    public HttpRequest(String requestLine, Map<String, String> headers, String messageBody) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.messageBody = messageBody;
    }

    public String getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getMessageBody() {
        return messageBody;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(requestLine).append("\n");
        for (String key: headers.keySet()) {
            builder.append(key).append(": ").append(headers.get(key)).append("\n");
        }
        if (messageBody != null && !messageBody.isEmpty()) {
            builder.append("\r\n").append(messageBody);
        }
        return builder.toString();
    }

    public static HttpRequest parse(BufferedReader reader) throws IOException {
        String requestLine = reader.readLine();
        if (requestLine == null || requestLine.isEmpty()) {
            throw new IOException("Invalid Request-Line: " + requestLine);
        }

        Map<String, String> headers = new HashMap<>();
        while (reader.ready()) {
            String header = reader.readLine();
            if (header.isEmpty()) {
                break;
            }
            int idx = header.indexOf(":");
            if (idx == -1) {
                throw new IOException("Invalid Header Parameter: " + header);
            }
            headers.put(header.substring(0, idx), header.substring(idx + 2, header.length()));
        }

        StringBuilder messageBody = new StringBuilder();
        while (reader.ready()) {
            String bodyLine = reader.readLine();
            messageBody.append(bodyLine).append("\r\n");
        }

        System.out.println("00000000000000000000000000000");
//
//        System.out.println(requestLine);
//        if(requestLine.equals("GET /create/something.txt HTTP/1.1")){
//            File file = new File("D:\\something.txt");
//            boolean result;
//            try
//            {
//                result = file.createNewFile();  //creates a new file
//                if(result)      // test if successfully created a new file
//                {
//                    System.out.println("file created "+file.getCanonicalPath()); //returns the path string
//                }
//                else
//                {
//                    System.out.println("File already exist at location: "+file.getCanonicalPath());
//                }
//
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();    //prints exception if any
//            }
//        }
//        else if(requestLine.equals("GET /delete/something.txt HTTP/1.1")){
//            try
//            {
//                File f= new File("D:\\something.txt");           //file to be delete
//                if(f.delete())                      //returns Boolean value
//                {
//                    System.out.println(f.getName() + " deleted");   //getting and printing the file name
//                }
//                else
//                {
//                    System.out.println("failed");
//                }
//            }
//            catch(Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
//        else if("exec/params".equals(requestLine.substring(5, 16))){ //code to check execution
//            final long startTime = System.currentTimeMillis();
//            int x = 1234567899;
//            int sum = 0;
//            for(int I = 1; I <= x ; I ++){
//                if(x % I ==0){
//                    sum += I ;
//                }
//            }
//            PrintWriter out = new PrintWriter("D:\\something.txt");
//
//            out.println(sum);
//            // Close the file.
//            out.close();
//            System.out.println ("The sum of the factors is " + sum);
//
//            final long endTime = System.currentTimeMillis();
//            System.out.println("Total execution time: " + (endTime - startTime));
//        }

        System.out.println("00000000000000000000000000000");
        return new HttpRequest(requestLine, headers, messageBody.toString());
    }
}