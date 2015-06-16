package cluedoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javafx.application.Platform;
import json.CluedoJSON;
import json.CluedoProtokollChecker;

import org.json.JSONObject;

import staticClasses.Config;
import staticClasses.Methods;
import staticClasses.NetworkMessages;
import cluedoNetworkGUI.CluedoServerGUI;
import cluedoNetworkLayer.CluedoGameServer;
import enums.NetworkHandhakeCodes;
import enums.PlayerStates;

/**
 * @author guldener
 * verteilt die nachrichten der des clients
 */
class CommunicationHandler implements Runnable{
	
	ServerSocket serverSocket;
	ClientItem client;
	Connector networkService;
	Socket socket;
	
	//ClientPool clientPool;
	//ArrayList<ClientItem> blackList;
	//GameListServer gameList;

	
	final CluedoServerGUI gui;
	boolean running = true;
	
	
	
	/**
	 * @param ss
	 * @param c
	 * @param g
	 * @param cList
	 * @param bList
	 * 
	 * tcp verbindung steht server wartet auf tcp handshake
	 */
	CommunicationHandler(ServerSocket ss, ClientItem c, CluedoServerGUI g,ClientPool cList,ArrayList<ClientItem> bList,GameListServer gl) {
		gui = g;
		serverSocket = ss;
//		clientPool = cList;
//		blackList = bList;
//		gameList = gl;
		client = c;
	}	
	
	private void awaitingLoginAttempt (){
		boolean readyForCommunication = false;
		System.out.println("awaiting");

		while (!readyForCommunication) {
			try {
				String message = getMessageFromClient(client.getSocket()).trim();
				CluedoProtokollChecker checker = new CluedoProtokollChecker(
						new CluedoJSON(
								new JSONObject(message)));
				NetworkHandhakeCodes errcode = checker.validateExpectedType("login",null);

				if (errcode == NetworkHandhakeCodes.OK) {							
					Platform.runLater(() -> {
						gui.addMessageIn(client.getAdress()+" says :"+message);
						gui.addIp(client.getAdress()+" "+client.getNick());
					});
					
					client.setExpansions(
						Methods.makeConjunction(
							Config.EXPANSIONS, 
							checker.getMessage().getJSONArray("expansions")
						)
					);
					client.setNick(checker.getMessage().getString("nick"));
					client.setGroupName(checker.getMessage().getString("group"));					
					client.sendMsg(NetworkMessages.login_sucMsg(client.getExpansions(), clientPool, gameList));
					
					clientPool.notifyAll(NetworkMessages.user_addedMsg(client.getNick()));
					clientPool.add(client);
					readyForCommunication = true;
				}
				else if (errcode == NetworkHandhakeCodes.TYPEOK_MESERR 
						|| errcode == NetworkHandhakeCodes.TYPERR){
					Platform.runLater(() -> {
						gui.addMessageIn(client.getAdress()+" sends invalid Messages : \n"+checker.getErrString());
					});	
					client.sendMsg(NetworkMessages.error_Msg("you are violating the protokoll due to the following: \n"+checker.getErrString()));
					client.sendMsg(NetworkMessages.disconnectMsg());
					client.closingConnection();
					blackList.add(client);					
					
					readyForCommunication = true; // no further listinenig on this socket
					running = false; // thread will run out without further notice					
				}
				
				else {
					Platform.runLater(() -> {
						gui.addMessageIn("unhandled incoming : \n" + message);
					});
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	@Override
	public void run(){	
		awaitingLoginAttempt();
		while (running){
			try {
	           String message = getMessageFromClient(client.socket).trim();
	           CluedoProtokollChecker checker = new CluedoProtokollChecker(
	        		   								new JSONObject(message));
	           checker.validate();
	           if (!checker.isValid()){
	        	   client.sendMsg(NetworkMessages.error_Msg(checker.getErrString()));
	           }
	           else {
	        	   if (checker.getType().equals("create game")){
	        		   createGame(checker.getMessage().getString("color"),client.getNick());
	        	   }
	        	   else if (checker.getType().equals("join game")){
	        		   int gameID = checker.getMessage().getInt("gameID");
	        		   String color = checker.getMessage().getString("color");
	        		   gameList.joinGame(
	        				   gameID, 
	        				   color, 
	        				   client.getNick());
	        		   Platform.runLater(() -> {
							gui.updateGame(gameID, 
									"(updated) Game "+gameID, gameList.getGameByGameID(gameID).getNicksConnected());
						});	
	        		   clientPool.notifyAll(
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
	           }
	           
	           
	           Platform.runLater(() -> {
	        	   gui.addMessageIn(message);
				});	
	           
			}
			catch (IOException e){
				try {
					closeConnection("closing :"+e.getMessage());
				}
				catch (IOException ex){
					Platform.runLater(() -> {
						gui.addMessageIn(ex.getMessage());
					});	
				}				
			}
		}
		client.sendMsg(NetworkMessages.disconnectedMsg("bye " +client.getNick()));
	}
	
	void createGame(String color,String nick){
		int gameID = gameList.size();
		CluedoGameServer newgame = new CluedoGameServer(gameID);
		newgame.joinGame(color, nick);
		clientPool.notifyAll(
				NetworkMessages.game_createdMsg(
						NetworkMessages.player_info(
								nick, color,PlayerStates.do_nothing.getName()
								), 
						gameID
						)
				);
		gameList.add(newgame);
		Platform.runLater(() -> {
			gui.addGame(gameID, "(created by"+nick+")", newgame.getNicksConnected());
		});
	}
	
	private void closeConnection(String msg) throws IOException{
		serverSocket.close();
		Platform.runLater(() -> {
			gui.addMessageIn(msg);
			
		});
		running = false;
	}
	
	
	String getMessageFromClient(Socket cs) throws IOException{
		StringBuffer message = new StringBuffer();
			try {
				BufferedReader clientInMessage = new BufferedReader(new InputStreamReader(cs.getInputStream(),StandardCharsets.UTF_8));
				char[] buffer = new char[Config.MESSAGE_BUFFER];
			 	int anzahlZeichen = clientInMessage.read(buffer, 0, Config.MESSAGE_BUFFER); // blockiert bis Nachricht empfangen
				message.append(new String(buffer, 0, anzahlZeichen));
			}
			catch(Exception e) {}		
		 	
			return message.toString();
	}
	
	public void kill(){
		running = false;
	}
	
}
