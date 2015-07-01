package cluedoNetworkLayer;

import javafx.application.Platform;
import staticClasses.auxx;
import view.Communicater;
import cluedoClient.ServerItem;
import enums.GameStates;

public class CluedoGameClient extends CluedoGame {
	
	ServerItem server;
	String myNick;
	Communicater communicater;


	public CluedoGameClient(int gameId,ServerItem server) {
		super(gameId);
		this.server = server;
	}
	
	public ServerItem getServer() {
		return server;
	}
		
	@Override
	public boolean start(){
		Platform.runLater(() -> {
			communicater = new Communicater(this);
			communicater.startGame();			
			auxx.loginfo("kommt er hin?");
			
		});
		setGameState(GameStates.started);
		
		return true;
	}
	
	public void setMyNick(String myNick) {
		this.myNick = myNick;
	}
}
