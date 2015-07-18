package cluedoServer;

import java.net.InetAddress;
import java.util.ArrayList;

public class ClientPool extends ArrayList<ClientItem> {	
	
	public ClientPool() {
		super();
	}
	
	public void addClient(ClientItem c){
		this.add(c);
	}
	
	public void notifyAll(String msg){
		for (int i = 0;i < this.size();i++)
			this.get(i).sendMsg(msg);
		
	}
	
	public void notifyAllClientsButSender(String msg,ClientItem c){
		for (int i = 0;i < this.size(); i++)
			if (c.id != this.get(i).id) 
				this.get(i).sendMsg(msg);
		
	}
	
	
	boolean checkForExistingIp(InetAddress adress){
		for (ClientItem c : this)
			if (adress.getHostAddress().equals(c.getAdress().getHostAddress())) return true;
		return false;
	}
	
	
}
