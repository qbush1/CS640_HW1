<<<<<<< HEAD
import java.net.Socket;
import java.net.ServerSocket;
import java.net.OutputStream;
import java.net.DataOutputStream;

public class ClientSocket {
    private Socket clientSocket; // client socket
    private int time; // time duration for sending data

    /**
     * Constructor to initialize client socket and time duration
     */
    public ClientSocket(String serverHost, int serverPort, int time) {
        clientSocket = new Socket(serverHost, serverPort);
        this.time = time;
    }

    /**
     * Method to connect to server and send data for the specified time duration
     */
    public void connect(int time) {
        long start = System.currentTimeMillis();
        long end = start + time * 1000;
        int writtenSize = 0;
        while (System.currentTimeMillis() < end) {
            OutputStream out = clientSocket.getOutputStream();
            DataOutputStream dataOut = new DataOutputStream(out);
            dataOut.write(new byte[1000], 0, 1000);
            writtenSize += dataOut.size();
        }
        System.out.println("Sent="writtenSize" KB, Rate="((writtenSize / time)/1000)" Mbps");
    }
}

public static void main(String[] args) {
    if (args.length < 3) {
        System.err.println("Missing arguments");
        System.exit(1);
    }
    
    if(!args[0].equals("-c") || !args[0].equals("-s")) {
        System.err.println("Invalid mode. Use -c for client or -s for server.");
        System.exit(1);
    }

    String mode = args[0];
        // Check arguments
        if (mode.equals("-c")) {
            String serverHost = args[2];
            int serverPort = Integer.parseInt(args[4]);
            int time = Integer.parseInt(args[6]);
            ClientSocket client = new ClientSocket(serverHost, serverPort, time);
            client.connect(time);
        }
        else if (mode.equals("-s")) {
            if (args[2].equals("-p")) {
                //Get port number
                int listenPort = Integer.parseInt(args[3]);
                if (1024 <= listenPort && listenPort <= 65535) {
                    ServerSocket serverSocket = new ServerSocket(listenPort);
                    Socket clientSocket = serverSocket.accept();
                    //Program waits here until client connects
                    InputStream in = clientSocket.getInputStream();


                    byte[] buffer = new byte[1000];
                    int bytesRead;
                    long totalBytesRead = 0;
                    
                    //start timer for bytes per second
                    long startTime = System.currentTimeMillis();
                    while ((bytesRead = in.read(buffer)) != -1) {
                        totalBytesRead += bytesRead;
                    }
                    long endTime = System.currentTimeMillis();
                    long elapsedTime = endTime - startTime;

                    //Close client connection
                    clientSocket.close();
                    serverSocket.close();

                    //Output Results:
                    System.out.print("received=" + (totalBytesRead / 1000) + "KB\t");
                    System.out.print("rate=" + (((totalBytesRead * 8) * 1000) / elapsedTime) + "Mbps\n");



                } else {
                    System.out.println("Error: port number must be in the range 1024 to 65535");
                }
            } else {
                System.out.println("Error: missing or additional arguments");
                System.exit(1);
            }
            

        } 
        else {
            System.out.println("Error: missing or additional arguments");
            System.exit(1);
        }
}
