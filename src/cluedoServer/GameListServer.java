package cluedoServer;

import java.util.Vector;

import cluedoNetworkLayer.CluedoGameServer;
import enums.JoinGameStatus;

public class GameListServer extends Vector<CluedoGameServer> {
	
	public GameListServer() {
		super();
	}
	
//	public JoinGameStatus joinGame(int gameID, String color, ClientItem client){
//		for (CluedoGameServer cg : this)
//			if (gameID == cg.getGameId())
//				return	cg.joinGameServer(color, client);
//			
//		return JoinGameStatus.game_not_found;
//	}
	
	public boolean leaveGame(int gameID, String nick){
		for (CluedoGameServer cg : this)
			if (gameID == cg.getGameId()){
				
				return true;
			}
		return false;
	}
	
	public JoinGameStatus joinGameById(int gameID,String color ,ClientItem client){
		return  getGameByID(gameID).joinGameServer(color,client);
	}
	
	public CluedoGameServer getGameByID(int gameID){
		for (CluedoGameServer cg : this)
			if (gameID == cg.getGameId()){				
				return cg;
			}
		return null;
	}
}
