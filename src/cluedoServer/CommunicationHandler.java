package cluedoServer;

import java.io.IOException;

import json.CluedoJSON;
import json.CluedoProtokollChecker;

import org.json.JSONObject;

import staticClasses.Config;
import staticClasses.Methods;
import staticClasses.NetworkMessages;
import cluedoNetworkGUI.DataGuiManagerServer;
import enums.NetworkHandhakeCodes;
import enums.PlayerStates;

/**
 * @author guldener
 * verteilt die nachrichten der des clients
 */
class CommunicationHandler implements Runnable{
	
	ClientItem client;
	Connector networkService;
	
	DataManagerServer dataManager;
	DataGuiManagerServer dataGuiManager;
	
	boolean run = true;	
	
	/**
	 * @param ss
	 * @param c
	 * @param g
	 * @param cList
	 * @param bList
	 * 
	 * tcp verbindung steht server wartet auf tcp handshake
	 */
	CommunicationHandler(ClientItem c, DataManagerServer dm,DataGuiManagerServer dgm) {
		dataManager = dm;
		dataGuiManager = dgm;
		client = c;
	}	
	
	private void awaitingLoginAttempt (){
		boolean readyForCommunication = false;
		System.out.println("awaiting");

		while (!readyForCommunication) {
			try {
				String message = Methods.getTCPMessage(client.getSocket()).trim();
				CluedoProtokollChecker checker = new CluedoProtokollChecker(
						new CluedoJSON(
								new JSONObject(message)));
				NetworkHandhakeCodes errcode = checker.validateExpectedType("login",null);

				if (errcode == NetworkHandhakeCodes.OK) {					
					client.setExpansions(
						Methods.makeConjunction(
							Config.EXPANSIONS, 
							checker.getMessage().getJSONArray("expansions")
						)
					);
					client.setNick(checker.getMessage().getString("nick"));
					client.setGroupName(checker.getMessage().getString("group"));					
					client.sendMsg(NetworkMessages.login_sucMsg(
							client.getExpansions(),
							dataManager.getClientPool(), 
							dataManager.getGameList()));
					System.out.println(NetworkMessages.login_sucMsg(
							client.getExpansions(),
							dataManager.getClientPool(), 
							dataManager.getGameList()));
					
					if (dataGuiManager.addNetworkActor(client,"logged in"))
						dataManager.notifyAll(NetworkMessages.user_addedMsg(client.getNick()));
					else {
						client.sendMsg(NetworkMessages.error_Msg(client.getNick()+" already exists, try again with different nick"));
						readyForCommunication = false;
					}						
					readyForCommunication = true;
				}
				else if (errcode == NetworkHandhakeCodes.TYPEOK_MESERR 
						|| errcode == NetworkHandhakeCodes.TYPERR){
					
					dataGuiManager.addMsgIn(client.getAdress()+" sends invalid Messages : \n"+checker.getErrString());
					client.sendMsg(NetworkMessages.error_Msg("you are violating the protokoll due to the following: \n"+checker.getErrString()));
					client.sendMsg(NetworkMessages.disconnectMsg());
					client.closingConnection(dataManager.getGroupName()+" is closing connection");
					dataManager.blacklist(client);					
					
					readyForCommunication = true; // no further listinenig on this socket
					run = false; // thread will run out without further notice					
				}
				
				else {
					dataGuiManager.addMsgIn("unhandled incoming : \n" + message);
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	@Override
	public void run(){	
		awaitingLoginAttempt();
		while (run){
			try {
	           String message = Methods.getTCPMessage(client.socket).trim();
	           CluedoProtokollChecker checker = new CluedoProtokollChecker(new JSONObject(message));
	           checker.validate();
	           if (!checker.isValid()){
	        	   client.sendMsg(NetworkMessages.error_Msg(checker.getErrString()+ " \n "
	        	   		+ "bye "+client.getNick()+" and "+client.getGroupName()+" is a shitty group"));
	        	   client.sendMsg(NetworkMessages.disconnectMsg());
	        	   dataGuiManager.removeClient(client);
	           }
	           else {
	        	   if (checker.getType().equals("create game")){
	        		   createGame(checker.getMessage().getString("color"),client.getNick());
	        	   }
	        	   else if (checker.getType().equals("join game")){
	        		   int gameID = checker.getMessage().getInt("gameID");
	        		   String color = checker.getMessage().getString("color");
	        		   if (dataGuiManager.joinGame(gameID, color, client)){
	        			   dataManager.notifyAll(
		        				   NetworkMessages.player_addedMsg(
		        						   NetworkMessages.player_info(
		        								   client.getNick(), 
		        								   color , 
		        								   PlayerStates.do_nothing.getName()
		        								   ),
		        						   gameID
		        						   )
		        				   );
	        		   }
	        		   else {
	        			   client.sendMsg(NetworkMessages.error_Msg("color is already chosen by someone else"));
	        		   }	        		  
	        	   }
	           }	
	           //nur damit nichts unter den tisch fällt
		       dataGuiManager.addMsgIn(message);

			}
			catch (Exception e){
				try {
					closeConnection("closing :"+e.getMessage());
				}
				catch (IOException ex){
					dataGuiManager.addMsgIn(ex.getMessage());
				}				
			}
		}
		
	}
	
	void createGame(String color,String nick){
		int gameID = dataGuiManager.createGame(color, nick);
		dataManager.notifyAll(
				NetworkMessages.game_createdMsg(
						NetworkMessages.player_info(
								nick, 
								color,PlayerStates.do_nothing.getName()
								), 
						gameID
						)
				);
	}
	
	private void closeConnection(String msg) throws IOException{
		client.sendMsg(NetworkMessages.disconnectedMsg("bye " +client.getNick()));
		dataManager.notifyAll(NetworkMessages.user_leftMsg(client.getNick()));
		
		dataGuiManager.closeOn(client,msg);
		run = false;
	}
	
	public void kill(){
		run = false;
	}
	
}
