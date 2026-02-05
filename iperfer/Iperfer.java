import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


class Iperfer {

    public static void main(String[] args) {

        String mode = args[0];
        // Check arguments
        if (mode.equals("-c")) {
            //Kushal implementation

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
}