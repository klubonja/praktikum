package cluedoServer;

import java.util.ArrayList;

import cluedoNetworkLayer.CluedoGameClient;

public class GameListClient extends ArrayList<CluedoGameClient>{
	public GameListClient() {
		super();
	}
	
	public boolean joinGame(int gameID, String color, String nick){
		for (CluedoGameClient cg : this)
			if (gameID == cg.getGameId()){
				cg.joinGame(color, nick);
				return true;
			}
		return false;
	}
	
	public boolean leaveGame(int gameID, String nick){
		for (CluedoGameClient cg : this)
			if (gameID == cg.getGameId()){
				
				return true;
			}
		return false;
	}
	
	public boolean leaveAllGames(String nick){
		boolean removed = false;
		for (CluedoGameClient cg : this){
			 removed = cg.removePlayer(nick);				
		}
		
		return removed;
	}
	
	public CluedoGameClient getGameByGameID(int gameID){
		for (CluedoGameClient cg : this)
			if (gameID == cg.getGameId()){
				
				return cg;
			}
		return null;
	}
}
