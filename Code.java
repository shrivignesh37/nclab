//nc
//EXP-3
Bit Stuffing
import java.util.*;

public class bitstuffing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String a = sc.next();
        String ans = "";
        int count = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == '1')
                count++;
            else
                count = 0;
            ans = ans + a.charAt(i);
            if (count == 5) {
                count = 0;
                ans = ans + '0';
            }
        }
        System.out.println(ans);
    }
}
================================
Character Stuffing

import java.util.*;
class Char
{
public static void main(String r[])
{
Scanner sc=new Scanner(System.in);
System.out.println("Enter number of characters: ");
int n=sc.nextInt();
String in[]=new String[n];
System.out.println("Enter characters: ");
for(int i=0;i<n;i++)
{

in[i]=sc.next();
}
for(int i= 0;i<n;i++)
{
if(in[i].equals("dle"))
{
in[i]="dle dle";
} }
System.out.println("Transmitted message is: ");
System.out.print(" dle stx ");
for(int i=0;i<n;i++)
{
System.out.print(in[i]+" ");
}
System.out.println(" dle etx ");
}
}


============================================================
Sliding Window

import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;

public class Main {
    static final int windowSize = 4;
    static final int totalFrame = 10;
    
    public static void main(String[] args) {
        Queue<Integer> window = new LinkedList();
        Random random = new Random();
        int nextFrameToSend = 0;
        int ackReceived = -1;
        
        while(ackReceived < totalFrame - 1) {
            while(window.size() < windowSize && nextFrameToSend < totalFrame) {
                window.add(nextFrameToSend);
                System.out.println("Frame sent : " + nextFrameToSend);
                nextFrameToSend ++;
            }
            if(!window.isEmpty()) {
                boolean ackSuccess = random.nextBoolean();
                if(ackSuccess) {
                    ackReceived = window.poll();
                    System.out.println("Ack received for : " + ackReceived);
                }
                else {
                    System.out.println("Ack not received, resending frame : " + window.peek());
                }
            }
        }
        System.out.println("All frames sent ans ack received");
    }
}

b.) Stop and wait 

import java.util.Random;

public class Main {
    static final int totalFrame = 10;
    public static void main(String[] args) {
        Random random = new Random();
        int currentFrame = 0;
        while(currentFrame < totalFrame) {
            System.out.println("Frame sent : " + currentFrame);
            boolean ackSuccess = random.nextBoolean();
            if(ackSuccess) {
                System.out.println("Acknowledgement received for frame : " + currentFrame);
                currentFrame ++;
            }
            else {
                System.out.println("Acknowlwdgement not received, resending frame : " + currentFrame);
            }
        }
        System.out.println("Acknowlwdgement received for all frame");
   }
}
=======================================================================================================
//EXP-5


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class Ping {
    public static void main(String[] args) {
        try { 
            // PING
            String host = "www.facebook.com";
            System.out.println("Pinging: " + host);
            pinging(host);

            // TRACEROUTE
            System.out.println("Tracerouting: " + host);
            traceroute(host);
        } catch (IOException e) { 
            e.printStackTrace();
        }
    }

    // PING
    private static void pinging(String host) throws IOException {
        InetAddress inet = InetAddress.getByName(host);
        if (inet.isReachable(5000)) {
            System.out.println(host + " is reachable.");
        } else {
            System.out.println(host + " is not reachable.");
        }
    }

    // TRACEROUTE
    private static void traceroute(String host) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        String command;

        // Determine the appropriate traceroute command based on the operating system
        if (os.contains("win")) {
            command = "tracert " + host;
        } else {
            command = "traceroute " + host;
        }

        // Use ProcessBuilder to create the process for executing the traceroute command
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.redirectErrorStream(true);

        // Start the process
        Process process = processBuilder.start();
        
        // Read and print the output of the traceroute command
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
============================================================================================
//EXP-6

a)File transfer

FileServer:

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class FileServer {
    public static void main(String[] args) {
        int port = 15123;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server waiting for connections...");

            // Wait for a client connection
            try (Socket socket = serverSocket.accept()) {
                System.out.println("Connection established: " + socket);

                // File to be sent
                File transferFile = new File("lexa.l");

                // Ensure the file exists
                if (!transferFile.exists()) {
                    System.out.println("File not found: " + transferFile.getAbsolutePath());
                    return;
                }

                byte[] byteArray = new byte[(int) transferFile.length()];

                try (FileInputStream fin = new FileInputStream(transferFile);
                     BufferedInputStream bin = new BufferedInputStream(fin);
                     OutputStream os = socket.getOutputStream()) {

                    bin.read(byteArray, 0, byteArray.length);
                    System.out.println("Sending file...");

                    os.write(byteArray, 0, byteArray.length);
                    os.flush();
                    System.out.println("File sent successfully");
                } catch (IOException e) {
                    System.err.println("Error reading file or writing to socket: " + e.getMessage());
                }
            } catch (IOException e) {
                System.err.println("Error accepting client connection: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}

FileClient:

import java.io.*;
import java.net.Socket;

public class FileClient {
    public static void main(String[] args) {
        // Define the server's address and port
        String serverAddress = "127.0.0.1";
        int port = 15123;
        
        try (Socket socket = new Socket(serverAddress, port);
             InputStream is = socket.getInputStream();
             FileOutputStream fos = new FileOutputStream("lexa.l");
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {

            System.out.println("Connected to server: " + socket);
            
            // Buffer for reading data
            byte[] byteArray = new byte[1024];
            int bytesRead;
            
            // Read the file data from the server and write it to the local file
            while ((bytesRead = is.read(byteArray, 0, byteArray.length)) != -1) {
                bos.write(byteArray, 0, bytesRead);
            }
            
            bos.flush();
            System.out.println("File received successfully.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
==============================================================================================
b.Remote Command Execution

Server:

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
class RemoteCommandServer {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Port Address: ");
        int port = Integer.parseInt(scanner.nextLine());
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is Ready To Receive a Command.");
            System.out.println("Waiting...");
            // Wait and accept a connection
            try (Socket clientSocket = serverSocket.accept()) {
                if (clientSocket.isConnected()) {
                    System.out.println("Client Socket is Connected Successfully.");
                }
                InputStream in = clientSocket.getInputStream();
                OutputStream out = clientSocket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader( in ));
                PrintWriter writer = new PrintWriter(out, true);
                String cmd = reader.readLine();
                System.out.println("Received command: " + cmd);
                ProcessBuilder processBuilder = new ProcessBuilder(cmd.split("\\s+"));
                Process process = processBuilder.start();
                // Read the command output and send it back to the client
                try (BufferedReader commandOutput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String outputLine;
                    while ((outputLine = commandOutput.readLine()) != null) {
                        writer.println(outputLine);
                    }
                }
            }
        }
    }
}

Client:

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
class RemoteCommandClient {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Port Address: ");
        int port = Integer.parseInt(scanner.nextLine());
        try (Socket socket = new Socket("localhost", port)) {
            if (socket.isConnected()) {
                System.out.println("Server Socket is Connected Successfully.");
            }
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverReader = new BufferedReader(new InputStreamReader( in ));
            PrintWriter writer = new PrintWriter(out, true);
            System.out.print("Enter the Command to be Executed: ");
            String command = userInputReader.readLine();
            writer.println(command);
            // Read and print the command output received from the server
            String serverOutput;
            while ((serverOutput = serverReader.readLine()) != null) {
                System.out.println("Server Output: " + serverOutput);
            }
        }
    }
}
==================================================================================
c.CHAT:

Server:

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
class ChatServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3000);
        System.out.println("Server ready for chatting");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected: " + clientSocket);
        // Reading from keyboard
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
        // Sending to client
        OutputStream ostream = clientSocket.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);
        // Receiving from client
        InputStream istream = clientSocket.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
        String receiveMessage, sendMessage;
        while (true) {
            // Receive message from the client
            if ((receiveMessage = receiveRead.readLine()) != null) {
                System.out.println("Client: " + receiveMessage);
            }
            // Read message from the server's console
            sendMessage = keyRead.readLine();
            // Send the message to the client
            pwrite.println("Server: " + sendMessage);
            pwrite.flush();
        }
    }
}

Client:

import java.io.*;
import java.net.Socket;
class ChatClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 3000);
        System.out.println("Connected to server: " + socket);
        // Reading from keyboard
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
        // Sending to server
        OutputStream ostream = socket.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);
        // Receiving from server
        InputStream istream = socket.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
        String receiveMessage, sendMessage;
        System.out.println("Start the chitchat, type and press Enter key");
        while (true) {
            // Read message from the client's console
            sendMessage = keyRead.readLine();
            // Send the message to the server
            pwrite.println("Client: " + sendMessage);
            pwrite.flush();
            // Receive message from the server
            if ((receiveMessage = receiveRead.readLine()) != null) {
                System.out.println(receiveMessage);
            }
        }
    }
}
===========================================================================

