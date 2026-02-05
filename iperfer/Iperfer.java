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

    if (args[0].equals("-c")) {
        if (args.length != 5) {
            System.err.println("Incorrect number of arguments for client mode");
            System.exit(1);
        }
        String serverHost = args[1];
        int serverPort = Integer.parseInt(args[2]);
        int duration = Integer.parseInt(args[4]);
        runClient(serverHost, serverPort, duration);
    } else if (args[0].equals("-s")) {
        if (args.length != 3) {
            System.err.println("Incorrect number of arguments for server mode");
            System.exit(1);
        }
        int port = Integer.parseInt(args[1]);
        runServer(port);
    } else {
        System.err.println("Invalid mode. Use -c for client or -s for server.");
        System.exit(1);
    }
}