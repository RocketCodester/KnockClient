//---------------------------------------------------------------
//         Project
//         KnockClient.java
//         Listens to endless Knock! Knock! jokes from KnockServer.
//---------------------------------------------------------------

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class KnockClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        //args = new String[]{getIpAddress(), Integer.toString(getPortNumber())};
        args = new String[]{"127.0.0.1", "3309"};
        if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()));) {
            BufferedReader stdIn
                    = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            ClientProtocol cp = new ClientProtocol();
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye.")) {
                    break;
                }
                
                fromUser = cp.processInput(fromServer);//"who's there?";//stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to "
                    + hostName);
            System.exit(1);
        }
    }

    public static int getPortNumber() {
        int number;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("Please enter a port number. ");
            while (!sc.hasNextInt()) {
                System.out.println("That's not a number! ");
                sc.next(); // this is important!
            }
            number = sc.nextInt();
        } while (number <= 0);
        return number;
    }

    public static String getIpAddress() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the server IP address.");
        String ip = sc.next();
        while (!isValidInet4Address(ip)) {
            System.out.println("The IP address \"" + ip + "\" is not valid");
            System.out.println("Please enter the server IP address!");
            ip = sc.next();
        }
        return ip;
    }

    public static boolean isValidInet4Address(String ip) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ip);
            return inetAddress instanceof Inet4Address;
        } catch (UnknownHostException ex) {
            return false;
        }
    }
}
