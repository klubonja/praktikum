package cluedoClient;

import java.net.InetAddress;


public class ServerItem  {
	String groupName;
	InetAddress ip;
	int port;
	
	public ServerItem(String groupName,InetAddress ip, int port) {
		this.groupName = groupName;
		this.ip = ip;
		this.port = port;
	}
	
	public InetAddress getIp() {
		return ip;
	}
	
	public int getPort() {
		return port;
	}
	
	public String getGroupName() {
		return groupName;
	}
}
