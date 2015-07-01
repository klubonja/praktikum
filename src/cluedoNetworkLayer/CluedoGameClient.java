package cluedoNetworkLayer;

import javafx.application.Platform;
import staticClasses.auxx;
import view.Communicator;
import cluedoClient.ServerItem;
import enums.GameStates;

public class CluedoGameClient extends CluedoGame {
	
	ServerItem server;
	String myNick;
	Communicator communicator;


	public CluedoGameClient(int gameId,ServerItem server) {
		super(gameId);
		this.server = server;
	}
	
	public ServerItem getServer() {
		return server;
	}
	
	public Communicator getCommunicator() {
		return communicator;
	}
		
	@Override
	public boolean start(){
		Platform.runLater(() -> {
			communicator = new Communicator(this);
			communicator.startGame();			
			auxx.loginfo("kommt er hin?");
			
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
}
