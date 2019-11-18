import java.io.*;  
import java.net.*; 
import java.util.Date; 
public class client {

	private static final int TIMEOUT = 1000;
	private static final int NUMTRIES = 10;


    public static void main(String[] args) throws IOException {

        if (args.length != 2){
        	throw new IllegalArgumentException("Parameters: <Server> [<Port>]");
        }
        
        String msg = "PING";

        InetAddress serverAddress = InetAddress.getByName(args[0]);
        int servPort = (args.length == 2) ? Integer.parseInt(args[1]) : 7;

        DatagramSocket socket = new DatagramSocket();
        long[][] results = new long[10][5];
        socket.setSoTimeout(TIMEOUT);
        boolean rec = false;
        java.util.Date track = new java.util.Date();
        long sent = 0000000000;
        long ret;
        long rtt;
        long delayed;
        for(int i =0;i<10;i++){
        	String bytes = (msg + Integer.toString(i));
        	byte[] bytesToSend = (bytes).getBytes();
        	DatagramPacket sendPacket = new DatagramPacket(bytesToSend, bytesToSend.length, serverAddress, servPort);
        	DatagramPacket receivePacket = new DatagramPacket(new byte[bytesToSend.length], bytesToSend.length);
        	
        	socket.send(sendPacket);
        	//start timer
        	sent = track.getTime();
        	results[i][3] = sent;
        	//System.out.println(java.time.LocalTime.now());
        	results[i][0] = i;
        	try{
        		socket.receive(receivePacket);
        		if(!receivePacket.getAddress().equals(serverAddress)){
        			results[i][1] = 0;
        			throw new IOException("Received packet from an unknown source");
        		}
        		//stop timer
        		ret = track.getTime();
        		results[i][4] = ret;
        		rec = true;
        		results[i][1] = 1;
        		rtt = ret - sent;
        		results[i][2] = rtt;
        		System.out.println("The RTT for ping #" + i+ " is " + rtt);
        		System.out.println("sent: "+sent);
        		System.out.println("returned: "+ ret);
        		String returned = "";
        		returned = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
        		System.out.println("Data: "+returned);
        		}catch(InterruptedIOException e){
        			System.out.println("Timed Out");
        			
        		}


        }
        //now new wait for delayed pings.
        int newTimeout = 5000;
        socket.setSoTimeout(newTimeout);
        	try{
        		String retMsg = "PING"+ Integer.toString(1);
        		byte[] toRec = retMsg.getBytes();
        		DatagramPacket receivePacket = new DatagramPacket(new byte[toRec.length], toRec.length); //new byte[bytesToSend.length], bytesToSend.length
        		socket.receive(receivePacket);

        		if(!receivePacket.getAddress().equals(serverAddress)){
        			throw new IOException("Received packet from an unknown source");
        		}
        		delayed = track.getTime();
        		String returned = "";
        		returned = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
        		System.out.print("Delayed packet: "+returned);
        		Character num = returned.charAt(4);
				int fail = Integer.parseInt(String.valueOf(num));
				results[fail][1] = 1;
				results[fail][2] = delayed - sent;
				results[fail][4] = delayed;
				System.out.println(fail);
        		}catch(InterruptedIOException e){
        			System.out.println("Timed Out");
        			
        		}
        		socket.close();

    }  
} 