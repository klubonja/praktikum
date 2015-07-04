package cluedoNetworkLayer;

import javafx.application.Platform;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import view.Communicator;
import cluedoClient.ServerItem;
import enums.GameStates;

public class CluedoGameClient extends CluedoGame {
	
	ServerItem server;
	String myNick;


	public CluedoGameClient(int gameId,ServerItem server) {
		super(gameId);
		this.server = server;
		myNick = server.getMyNick();
	}
	
	public ServerItem getServer() {
		return server;
	}
	
	public Communicator getCommunicator() {
		return communicator;
	}
	
	public String getMyNick() {
		return myNick;
	}
		
	@Override
	public boolean start(){
		auxx.loginfo("game "+ getGameId()+ " started");
		Platform.runLater(() -> {
			communicator = new Communicator(this);
			communicator.startGame();		
			communicator.setTitle(myNick +" playing on server "+server.getGroupName()+" Game : "+gameId);			
		});
		setGameState(GameStates.started);
		
		return true;
	}
	
	public void setMyNick(String myNick) {
		this.myNick = myNick;
	}
	
	public void sendMsgToServer(String msg){
		auxx.sendTCPMsg(server.getSocket(), msg);
	}
	
	public void addChatMsg(String msg){
		communicator.addChatMsg(msg);
	}
	
	public void kill() {
  	    auxx.sendTCPMsg(server.getSocket(), NetworkMessages.leave_gameMsg(gameId)); 
  	    killCommunicator();
	}
	
	public void killCommunicator() {
		Platform.runLater(() -> {
			communicator.kill();			
		});  	    
	}
}
