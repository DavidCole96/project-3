import java.io.*;  
import java.net.*;

public class server {
    public static void main(String[] args) throws IOException {
  
        //Create the server socket and choose a port to listen on 
	    int ECHOMAX = 255;

	    if (args.length != 1){
	    	throw new IllegalArgumentException("Parameter(s): <Port>");
	    }
	    int servPort = Integer.parseInt(args[0]);
        java.util.Date track = new java.util.Date();
	    DatagramSocket socket = new DatagramSocket(servPort);
	    DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);
        //Create the socket once a connection is established
        while (true){
            socket.receive(packet);
            System.out.println("Receiving packet from client at " + packet.getAddress().getHostAddress()+ " on port:" + packet.getPort());
            System.out.println("Time recevied is: "+ track.getTime());
         //Create the IO objects for reading and writing streams
         	socket.send(packet);
         	packet.setLength(ECHOMAX);
            System.out.println("Time echoed is: "+ track.getTime());


        
        //Create a SHA-256 hash of the received String
        //DO NOT change this section of code
        //throw new NoSuchAlgorithmException();
        

        }
    }
}

