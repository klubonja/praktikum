package cluedoServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;



/**
 * 
 * @author Huy
 * 
 * @param socket Erstellt ein neues Datagramsocket fuer den Server 
 *
 */
public class UDPServer implements Runnable {
	
	DatagramSocket socket; 
	
	/**
	 * 
	 * @return gibt die Instanz vom UDPServer zurueck
	 */
	public static UDPServer getInstance(){
		return UDPServerHolder.INSTANCE;
				
	}
	
	
	/**
	 * 
	 * @author HUY-BACH
	 * 
	 * @param INSTANCE macht einen neuen UDPServer
	 *
	 */
	public static class UDPServerHolder{
		private static final UDPServer INSTANCE = new UDPServer();
	}
	@Override
	public void run() {
		try{
			socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);
			
			//wird noch gemacht
			
			
		} 
		catch(IOException ex) {
		      Logger.getLogger(UDPServer.class.getName()).log(Level.SEVERE, null, ex);
		    }
	}

}
