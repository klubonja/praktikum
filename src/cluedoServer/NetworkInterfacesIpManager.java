package cluedoServer;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

import staticClasses.auxx;

	
/**
 * @author guldener
 * eine hilfsklasse zum ausgeben aller verwendbaren, aktiven ips auf allen networkinterfaces
 */
public final class NetworkInterfacesIpManager  {
	private final ArrayList<String> availableNetworkServiceNames = new ArrayList<String>();
	private final ArrayList<String> availableIps = new ArrayList<String>();
	private final ArrayList<String> preferredNetworkServices = new ArrayList<String>();

	private static final String IPADDRESS_PATTERN = 
			"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	/*
	public NetworkInterfacesIpManager(){
		preferredNetworkServices = new ArrayList<String>();
		availableIps = new ArrayList<String>();
		availableNetworkServiceNames = new ArrayList<String>();
		getI();
		
	}
	*/
	
	private void getI(){
		try {
			Enumeration<NetworkInterface> ni = NetworkInterface.getNetworkInterfaces();
			while (ni.hasMoreElements()){
				NetworkInterface n = ni.nextElement();
				Enumeration<InetAddress> nae =n.getInetAddresses();
				while (nae.hasMoreElements()){
					InetAddress na = nae.nextElement();
					String s = na.getHostAddress();
					if (s.matches(IPADDRESS_PATTERN)){
						availableIps.add(s);
						availableNetworkServiceNames.add(n.getName());
					}
				}			
			}
		} 
		catch (SocketException e) {
			auxx.logsevere("SERVER getting interfaces failed", e);
		}
	}
	
	public void addPreferredService(String s){
		preferredNetworkServices.add(s);
	}
	
	public ArrayList<String> getActiveIps(){
		getI();
		return availableIps;
	}
	
	public String getServicesFormated(){
		getI();
		StringBuffer ips = new StringBuffer();
	 	for (int i = 0; i < availableIps.size(); i++) {
	 		ips.append(availableNetworkServiceNames.get(i)+" : "+availableIps.get(i));
	 		if (i != availableIps.size()-1) ips.append(", ");
		}
	 	
	 	return ips.toString();
		
	}

}
